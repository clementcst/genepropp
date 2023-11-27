package com.acfjj.app.controller;

import java.util.LinkedHashMap;
import java.util.List;
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
	public Response login(@RequestParam String privatecode, @RequestParam String password) {
		User user = userService.getUserByPrivateCode(privatecode);
		if(Objects.isNull(user)) {
			return new Response("Incorrect private code", false);
		}
		if(!user.getPassword().equals(password)) {
			return new Response("Incorrect password", false);
		}
		return new Response(user.getId(), "Login Success", true);
	}
	
	@PostMapping("/registration")
	public Response registration(@RequestParam int step, @RequestBody LinkedHashMap<String, String> data) {
		User userToRegister = User.castAsUser(data);
		if(Objects.isNull(userToRegister)) {
			return new Response("Request Body format is invalid.",	false);
		}
		return addUser(userToRegister);
	}
	
	public Response addUser(User user) {
		if(!Objects.isNull(userService.getUserByNameAndBirthInfo(user.getLastName(), user.getFirstName(), user.getDateOfBirth(), user.getCountryOfBirth(), user.getCityOfBirth()))) {
			return new Response("user " + user.getFullName() + " already exist", false);
		}
		userService.addUser(user);
		String lastName = user.getLastName();
		String firstName = user.getFirstName();
		user = userService.getUserByNameAndBirthInfo(lastName, firstName, user.getDateOfBirth(), user.getCountryOfBirth(), user.getCityOfBirth());
		if(Objects.isNull(user)) {
			return new Response("Fail to create user", false);
		}
		String treeName = firstName + " " + lastName + "'s Tree";
		treeName = treeService.addTree(new Tree(treeName, 0));
		Tree tree = treeService.getTreeByName(treeName);
		if(Objects.isNull(tree)) {
			return new Response("Fail to create user's Tree", false);
		}
		nodeService.addNode(new Node(user.getPersonInfo(), user, 0));
		Node node = nodeService.getNodeByNameAndBirthInfo(lastName, firstName, user.getDateOfBirth(), user.getCountryOfBirth(), user.getCityOfBirth());
		if(Objects.isNull(node)) {
			return new Response("Fail to create user's node", false);
		}
		treeService.addNodeToTree(tree, node, 1, 0);
		tree = treeService.getTree(tree.getId());
		user.setMyTree(tree);
		userService.updateUser(user.getId(), user);
		return new Response("Success", true);
	}
	
	@DeleteMapping("user/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}
}