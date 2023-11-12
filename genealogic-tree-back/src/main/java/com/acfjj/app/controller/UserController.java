package com.acfjj.app.controller;

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
	public List<User> getUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/user/{id}") 
	public User getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}
	
	@PostMapping("/user") 
	public Response addUser(@RequestParam User user) {
		userService.addUser(user);
		String lastName = user.getLastName();
		String firstName = user.getFirstName();
		user = userService.getUserByNameAndBirthInfo(lastName, firstName, user.getDateOfBirth(), user.getCountryOfBirth(), user.getCityOfBirth());
		if(Objects.isNull(user)) {
			return new Response("Fail to create user: step 1 failed", false);
		}
		String defaultTreeName = firstName + " " + lastName + "'s Tree";
		Integer nameInt = 1;
		String treeName = defaultTreeName;
//		while() { //mettre le check de verification  si le nom du tree exite
//			nameInt++;
//			treeName = defaultTreeName + nameInt.toString(); 
//		}
		treeService.addTree(new Tree(treeName, 0));
		Tree tree = treeService.getTreeByName(treeName);
		if(Objects.isNull(tree)) {
			return new Response("Fail to create user's Tree: step 2 failed", false);
		}
		nodeService.addNode(new Node(user.getPersonInfo(), user, 0));
		Node node = nodeService.getNodeByNameAndBirthInfo(lastName, firstName, user.getDateOfBirth(), user.getCountryOfBirth(), user.getCityOfBirth());
		if(Objects.isNull(node)) {
			return new Response("Fail to create user's node: step 3 failed", false);
		}
		//tree.addNode(node, 1, 0);
		System.out.println(tree);
		treeService.updateTree(tree.getId(), tree);
		return new Response("Success", true);
	}
	
	@DeleteMapping("user/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}
}