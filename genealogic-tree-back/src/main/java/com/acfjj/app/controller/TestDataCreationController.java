package com.acfjj.app.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acfjj.app.model.Node;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.User;
import com.acfjj.app.repository.TreeRepository;
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
	@Autowired
	TreeController treeController;
	
		
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
			//System.out.println(userService.getUserByNameAndBirthInfo(user.getLastName(), user.getFirstName(), user.getDateOfBirth(), user.getCountryOfBirth(), user.getCityOfBirth()));
			if(!Objects.isNull(userService.getUserByNameAndBirthInfo(user.getLastName(), user.getFirstName(), user.getDateOfBirth(), user.getCountryOfBirth(), user.getCityOfBirth()))) {
				responses.add(new Response("user " + user.getFullName() + " already exist", false));
			} else {
				responses.add(userController.addUser(user));
			}
		}
		for (Response response : responses) {
			if(!response.getSuccess()) {
				return new Response(responses, "One or more failure occured", false);
			}
		}
		return new Response(responses);
	}
	
	@PostMapping("/test/trees")
    public Response addTree() {
            Tree tree = treeService.getTree(1);
            Node node = nodeService.getNode((long) 1);
            Node parent1 = nodeService.getNode((long) 2);
            Node partner = nodeService.getNode((long) 3);
            Node siblings = nodeService.getNode((long) 4);
            Node exPartner = nodeService.getNode((long) 5);
            
    	List<Response> responses = new ArrayList<>();
		responses.add(treeController.addParent(tree.getId(), node,parent1, 1, 1));
		responses.add(treeController.addPartner(tree.getId(), node,partner, 0));
		responses.add(treeController.addSiblings(tree.getId(), node,siblings, 1));
		responses.add(treeController.addExPartner(tree.getId(), node,exPartner, 0));
		
		
        Node newNode1 = new Node("Dupont", "Camille" , 0, LocalDate.of(2002, 04, 2), "Pays", "Ville", userService.getUser(1), 1, "Nationalité", "Adresse", 12345, "Base64Image");
        nodeService.addNode(newNode1);
	    newNode1 = nodeService.getNode((long) 6);
	    responses.add(treeController.addParent(tree.getId(), node,newNode1, 1, 2));
	    responses.add(treeController.addPartner(tree.getId(), newNode1,parent1, 0));
	    Node newNode2 = new Node("Silva", "Rafael" , 1, LocalDate.of(2002, 04, 2), "Pays", "Ville", userService.getUser(1), 1, "Nationalité", "Adresse", 12345, "Base64Image");
        nodeService.addNode(newNode2);
        newNode2 = nodeService.getNode((long) 7);
	    responses.add(treeController.addParent(tree.getId(), newNode1,newNode2, 1, 2));

	    Node newNode3 = new Node("Petrov", "Yuri" , 1, LocalDate.of(2002, 04, 2), "Pays", "Ville", userService.getUser(1), 1, "Nationalité", "Adresse", 12345, "Base64Image");
        nodeService.addNode(newNode3);
        newNode3 = nodeService.getNode((long) 8);
	    responses.add(treeController.addSiblings(tree.getId(), newNode1,newNode3, 1));


		for (Response response : responses) {
			if(!response.getSuccess()) {
				return new Response(responses, "One or more failure occured", false);
			}
		}
		return new Response(responses);
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
