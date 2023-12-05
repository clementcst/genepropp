package com.acfjj.app.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acfjj.app.model.Node;
import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.model.User;
import com.acfjj.app.service.NodeService;
import com.acfjj.app.service.TreeService;
import com.acfjj.app.service.UserService;
import com.acfjj.app.utils.Response;
import com.acfjj.app.controller.TreeController;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
public class UserController{	
	@Autowired
	UserService userService;
	@Autowired
	TreeService treeService;
	@Autowired 
	NodeService nodeService;
			
	@GetMapping("/users") 
	public Response getUsers() {
		return new Response(userService.getAllUsers());
	}
	
	@GetMapping("/user") 
	public Response getUser(@RequestParam Long userId) {
		return new Response(userService.getUser(userId));
	}
	
	@GetMapping("/login")
	public Response login(@RequestParam String privateCode, @RequestParam String password) {
		User user = userService.getUserByPrivateCode(privateCode);
		if(Objects.isNull(user)) {
			return new Response("Incorrect private code", false);
		}
		if(!user.getPassword().equals(password)) {
			return new Response("Incorrect password", false);
		}
		if(!user.isValidated()) {
			return new Response("User node validated by Admin. Please retry later or contact support", false);
		}
		return new Response(user.getId(), "Login Success", true);
	}
	
	@PostMapping("/registration")
	public Response registration(@RequestParam int step, @RequestBody LinkedHashMap<String, String> data, @RequestParam(required = false, defaultValue = "0") Boolean userResponse) {
		User userToRegister = User.castAsUser(data);
		if(Objects.isNull(userToRegister)) {
			return new Response("Request Body format is invalid.",	false);
		}
		switch (step) {
		case 1: {
			return registrationStep1(userToRegister, data);
		}
		case 2: {
			return registrationStep2(userToRegister, data, userResponse);
		}
		default:
			return new Response("Unexpected parameter: " + step, false);
		}

	}

	public Response addUser(User user, Node existingNode) {
		//security
		if(!Objects.isNull(userService.getUserByNameAndBirthInfo(user.getLastName(), user.getFirstName(), user.getDateOfBirth(), user.getCountryOfBirth(), user.getCityOfBirth()))) {
			return new Response("User with birth info (" + user.getFullNameAndBirthInfo() + ") already exist", false);
		}
		if(!Objects.isNull(userService.getUserByEmail(user.getEmail()))) {
			return new Response("User with email (" + user.getEmail() + ") already exist", false);
		}
		//User
		userService.addUser(user);
		String lastName = user.getLastName();
		String firstName = user.getFirstName();
		user = userService.getUserByNameAndBirthInfo(lastName, firstName, user.getDateOfBirth(), user.getCountryOfBirth(), user.getCityOfBirth());
		if(Objects.isNull(user)) {
			return new Response("Fail to create user in DB", false);
		}
		//User's Tree
		String treeName = firstName + " " + lastName + "'s Tree";
		treeName = treeService.getUniqueName(new Tree(treeName, 0));
		treeService.addTree(new Tree(treeName, 0));
		Tree tree = treeService.getTreeByName(treeName);
		if(Objects.isNull(tree)) {
			return new Response("Fail to create user's Tree in DB", false);
		}
		//User's Node
		if(!Objects.isNull(existingNode)) {
			nodeService.updateNode(existingNode.getId(), existingNode);
		} else {
			nodeService.addNode(new Node(user.getPersonInfo(), user, 1));
		}
		Node node = nodeService.getPublicNodeByNameAndBirthInfo(lastName, firstName, user.getDateOfBirth(), user.getCountryOfBirth(), user.getCityOfBirth());
		if(Objects.isNull(node)) {
			return new Response("Fail to create user's Node in DB", false);
		}
		user.getPersonInfo().setRelatedUser(user);
		user.getPersonInfo().setRelatedNode(node);
		treeService.addNodeToTree(tree, node, 1, 0);
		tree = treeService.getTree(tree.getId());
		user.setMyTree(tree);
		userService.updateUser(user.getId(), user);
		LinkedHashMap<String, String> responseValue = new LinkedHashMap<String, String>();
		responseValue.put("userId", user.getId().toString());
		responseValue.put("privateCode", user.getPrivateCode());
		return new Response(responseValue, "Success", true);
	}

	private Response registrationStep1(User userToRegister, LinkedHashMap<String, String> data) {
		User userFound = userService.getUserByNameAndBirthInfo(userToRegister.getLastName(), userToRegister.getFirstName(), userToRegister.getDateOfBirth(), userToRegister.getCountryOfBirth(), userToRegister.getCityOfBirth());
		Node nodeFound = nodeService.getPublicNodeByNameAndBirthInfo(userToRegister.getLastName(), userToRegister.getFirstName(), userToRegister.getDateOfBirth(), userToRegister.getCountryOfBirth(), userToRegister.getCityOfBirth());
		if(!Objects.isNull(userFound)) {
			Map<String, Object> responseValue = new LinkedHashMap<String, Object>();
			responseValue.put("nextStep", 1);
			responseValue.put("frontMessage", "A user with the same names and birth informations or with same email as been found. Double account are not allowed. Please double check register information.");
			responseValue.put("data", data);
			return new Response(responseValue ,"User with same birth info (" + userFound.getFullNameAndBirthInfo() + ") or with same email (" + userFound.getEmail() + ") already exist", false);
		} else if(!Objects.isNull(nodeFound)) {
			Map<String, Object> responseValue = new LinkedHashMap<String, Object>();
			responseValue.put("nextStep", 2);
			responseValue.put("frontMessage", "A node with the same names and birth informations as been found. Please double check register information and confirm that it's you. Yes/No");
			responseValue.put("toDisplay", nodeFound.getPersonInfo());
			responseValue.put("data", data);
			return new Response(responseValue ,"Node with same birth info (" + nodeFound.getFullNameAndBirthInfo() + ")", true);
		} else {
			return addUser(userToRegister, null);
		}
	}

	private Response registrationStep2(User userToRegister, LinkedHashMap<String, String> data, Boolean userResponse) {
		if(userResponse) {
			Node nodeFound = nodeService.getPublicNodeByNameAndBirthInfo(userToRegister.getLastName(), userToRegister.getFirstName(), userToRegister.getDateOfBirth(), userToRegister.getCountryOfBirth(), userToRegister.getCityOfBirth());
			if(Objects.isNull(nodeFound)) {
				return new Response("Error while retrieving node that was found before. Go back to step 1 of registration", false);
			}
			userToRegister.setPersonInfo(mergeNodeToUserPI(nodeFound, userToRegister));
			return addUser(userToRegister, nodeFound);
		} else {
			return registrationStep1(userToRegister, data);
		}
	}

	private PersonInfo mergeNodeToUserPI(Node node, User user) {
		PersonInfo nodePI = node.getPersonInfo();
		PersonInfo userPI = user.getPersonInfo();
		PersonInfo finalPI = new PersonInfo();
		finalPI.setId(nodePI.getId());
		finalPI.setLastName(userPI.getLastName());
		finalPI.setFirstName(userPI.getFirstName());
		finalPI.setDateOfBirth(userPI.getDateOfBirth());
		finalPI.setCountryOfBirth(userPI.getCountryOfBirth());
		finalPI.setCityOfBirth(userPI.getCityOfBirth());
		finalPI.setGender(userPI.getGender());
		finalPI.setIsDead(userPI.isDead());
		finalPI.setAdress(Objects.isNull(userPI.getAdress()) ? nodePI.getAdress() : userPI.getAdress());
		finalPI.setNationality(Objects.isNull(userPI.getNationality()) ? nodePI.getNationality() : userPI.getNationality());
		finalPI.setPostalCode(Objects.isNull(userPI.getPostalCode()) ? nodePI.getPostalCode() : userPI.getPostalCode());
		finalPI.setProfilPictureData64(Objects.isNull(userPI.getProfilPictureData64()) ? nodePI.getProfilPictureData64() : userPI.getProfilPictureData64());
		return finalPI;
	}
	
	@DeleteMapping("/user")
	public void deleteUser(@RequestParam Long userId) {
		userService.deleteUser(userId);
	}
}
