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
		return new Response(user.getId(), "Login Success", true);
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
		String treeName = firstName + " " + lastName + "'s Tree";
		treeName = treeService.getUniqueName(new Tree(treeName, 0));
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