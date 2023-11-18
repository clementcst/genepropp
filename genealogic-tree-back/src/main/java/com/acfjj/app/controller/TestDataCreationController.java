package com.acfjj.app.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acfjj.app.model.Node;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.User;
import com.acfjj.app.service.*;
import com.acfjj.app.utils.Response;


@RestController
public class TestDataCreationController {
	@Autowired
	UserController userController;
	@Autowired
	UserService userService;
	@Autowired
	TreeService treeService;
	@Autowired
	NodeService nodeService;
	@Autowired
	ConversationService conversationService;
	
		
	@PostMapping("/test/users") 
	public Response addTestUsers() {
		List<User> users = new ArrayList<User>(Arrays.asList(new User[] {
			new User("Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "France", "Cergy", "adam@mail", "password1","Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureData64"),
	        new User("Cassiet", "Clement", 1, LocalDate.of(1899, 07, 9), "Péîs", "Tournant-En-Brie", "clement@mail", "password2", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureData64"),
	        new User("Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Nouvelle-Zélande", "Paris Hilton", "jordan@mail", "password3", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureData64"),
	        new User("Cerf", "Fabien", 1, LocalDate.of(2002, 03, 9), "France", "Paris", "fabien@mail", "password4", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureData64"),
	        new User("Legrand", "Joan", 1, LocalDate.of(2002, 10, 26), "France", "Paris", "joan@mail", "password5", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureData64")
	    }));
		List<Response> responses = new ArrayList<>();
		for (User user : users) {
			responses.add(userController.addUser(user));
		}
		for (Response response : responses) {
			if(!response.getSuccess()) {
				return new Response(responses, "On or more failure occured", false);
			}
		}
		return new Response(responses);
	}
	
	@PostMapping("/test/trees")
    public void addTree() {
		List<Tree> Trees = new ArrayList<Tree>(Arrays.asList(new Tree[] {
            new Tree("Tree Bourhara", 1),
            new Tree("Tree Cassiet", 0),
            new Tree("Tree Gautier", 0),
            new Tree("Tree Cerf", 1),
            new Tree("Tree Legrand", 2)
        }));
		for (Tree tree : Trees) {
			treeService.addTree(tree);
		}
    }
	

    @PostMapping("/test/nodes")
    public void addNodes() {
    	 List<Node> nodes = new ArrayList<Node>(Arrays.asList(new Node[] {
            new Node("Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "France", "Cergy", userService.getUser(1), 1, "French", "Some Address", 12345, "Base64Image"),
            new Node("Cassiet", "Clement", 1, LocalDate.of(1899, 07, 9), "Péîs", "Tournant-En-Brie", userService.getUser(2), 2, "Peïsian", "Another Address", 54321, "Base64Image2"),
            new Node("Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Nouvel-Zélande", "Paris Hilton", userService.getUser(3), 1, "Kiwi", "Yet Another Address", 67890, "Base64Image3"),
            new Node("Cerf", "Fabien", 1, LocalDate.of(2002, 03, 9), "France", "Paris", userService.getUser(4), 0, "French", "Some Address", 98765, "Base64Image4"),
            new Node("Legrand", "Joan", 1, LocalDate.of(2002, 10, 26), "France", "Paris", userService.getUser(5), 2, "French", "Some Address", 54321, "Base64Image5")
        }));
		for (Node node : nodes) {
			nodeService.addNode(node);
		}
	}
	
}
