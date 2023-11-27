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
import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.User;
import com.acfjj.app.repository.TreeRepository;
import com.acfjj.app.repository.PersonInfoRepository;
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
	@Autowired
	PersonInfoRepository personInfoRepository;
	
		
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
			responses.add(userController.addUser(user, null));
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
		responses.add(treeController.addLinkedNode(tree.getId(), node.getId(),parent1, 1, "Parent", true));
		responses.add(treeController.addLinkedNode(tree.getId(), node.getId(),partner, 0, "Partner", true));
		responses.add(treeController.addLinkedNode(tree.getId(), node.getId(),siblings, 1, "Siblings", true));
		responses.add(treeController.addLinkedNode(tree.getId(), node.getId(),exPartner, 0, "ExPartner",true));
		
		PersonInfo personInfo1 = new PersonInfo("Dupont", "Camille", 0, LocalDate.of(2002, 04, 2), "Pays", "Ville", true,  "Nationalité", "Adresse", 12345, "Base64Image");
        Node newNode1 = new Node(personInfo1, userService.getUser(1), 1);
        
	    responses.add(treeController.addLinkedNode(tree.getId(), node.getId(),newNode1, 1, "Parent", true));
	    responses.add(treeController.addLinkedNode(tree.getId(), newNode1.getId(),parent1, 1, "Partner", false));
	    Node newNode2 = new Node("Silva", "Rafael" , 1, LocalDate.of(2002, 04, 2), "Pays", "Ville", userService.getUser(1), 1, "Nationalité", "Adresse", 12345, "Base64Image");
	    responses.add(treeController.addLinkedNode(tree.getId(), newNode1.getId(),newNode2, 1, "Parent", true));

	    Node newNode3 = new Node("Petrov", "Yuri" , 1, LocalDate.of(2002, 04, 2), "Pays", "Ville", userService.getUser(1), 1, "Nationalité", "Adresse", 12345, "Base64Image");
	    responses.add(treeController.addLinkedNode(tree.getId(), newNode1.getId(),newNode3, 1, "Siblings", true));
	    responses.add(treeController.addLinkedNode(tree.getId(), newNode3.getId(),newNode2, 0, "Parent", true));



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
