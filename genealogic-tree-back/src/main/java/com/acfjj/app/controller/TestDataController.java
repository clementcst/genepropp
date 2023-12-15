package com.acfjj.app.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acfjj.app.model.Node;
import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.User;
import com.acfjj.app.utils.Response;

@RestController
@Scope("singleton")
@RequestMapping("/test")
public class TestDataController extends AbstractController {
	@Autowired
	AccountController accountController;
	@Autowired
	TreeController treeController;

	@PostMapping("/users")
	public Response addTestUsers(@RequestParam(required = false, defaultValue = "1") Boolean validated) {
		List<User> users = new ArrayList<User>(Arrays.asList(new User[] {
				new User("Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "France", "Cergy", "adam@mail", "password1",
						"Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureUrl"),
				new User("Cassiet", "Clement", 1, LocalDate.of(1899, 07, 9), "Péîs", "Tournant-En-Brie", "clement@mail",
						"password2", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234,
						"profilPictureUrl"),
				new User("Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Nouvelle-Zélande", "Paris Hilton",
						"jordan@mail", "password3", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234,
						"profilPictureUrl"),
				new User("Cerf", "Fabien", 1, LocalDate.of(2002, 03, 9), "France", "Paris", "fabien@mail", "password4",
						"Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureUrl"),
				new User("Legrand", "Joan", 1, LocalDate.of(2002, 10, 26), "France", "Paris", "joan@mail", "password5",
						"Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureUrl") }));
		List<Response> responses = new ArrayList<>();
		for (User user : users) {
			responses.add(accountController.registerUser(user, null));
			user = userService.getUserByEmail(user.getEmail());
			user.setValidated(validated);
			userService.updateUser(user.getId(), user);
		}
		for (Response response : responses) {
			if (!response.getSuccess()) {
				return new Response(responses, "One or more failure occured", false);
			}
		}
		return new Response("All test users have successfully been created", true);
	}

	@PostMapping("/tree")
	public Response addTestTree() {
		Tree tree = treeService.getTree(1);
		Node node = nodeService.getNode((long) 1);
		Node parent1 = nodeService.getNode((long) 2);
		Node partner = nodeService.getNode((long) 3);
		Node siblings = nodeService.getNode((long) 4);
		Node exPartner = nodeService.getNode((long) 5);

		List<Response> responses = new ArrayList<>();
		responses.add(treeController.addLinkedNode(tree.getId(), node.getId(), parent1, 1, "Parent", true));
		responses.add(treeController.addLinkedNode(tree.getId(), node.getId(), partner, 0, "Partner", true));
		responses.add(treeController.addLinkedNode(tree.getId(), node.getId(), siblings, 1, "Siblings", true));
		responses.add(treeController.addLinkedNode(tree.getId(), siblings.getId(), parent1, 1, "Parent", false));
		responses.add(treeController.addLinkedNode(tree.getId(), node.getId(), exPartner, 0, "ExPartner", true));

		PersonInfo personInfo1 = new PersonInfo("Dupont", "Camille", 0, LocalDate.of(2002, 04, 2), "Pays", "Ville",
				true, "Nationalité", "Adresse", 12345, "Base64Image");
		Node newNode1 = new Node(personInfo1, userService.getUser(1), 1);
		responses.add(treeController.addLinkedNode(tree.getId(), node.getId(), newNode1, 1, "Parent", true));
		responses.add(treeController.addLinkedNode(tree.getId(), newNode1.getId(), parent1, 1, "Partner", false));
		responses.add(treeController.addLinkedNode(tree.getId(), siblings.getId(), newNode1, 1, "Parent", false));

		Node newNode2 = new Node("Silva", "Rafael", 1, LocalDate.of(2002, 04, 2), "Pays", "Ville",
				userService.getUser(1), 1, "Nationalité", "Adresse", 12345, "Base64Image");
		responses.add(treeController.addLinkedNode(tree.getId(), newNode1.getId(), newNode2, 1, "Parent", true));

		Node newNode3 = new Node("Petrov", "Yuri", 1, LocalDate.of(2002, 04, 2), "Pays", "Ville",
				userService.getUser(1), 1, "Nationalité", "Adresse", 12345, "Base64Image");
		responses.add(treeController.addLinkedNode(tree.getId(), newNode1.getId(), newNode3, 1, "Siblings", true));
		responses.add(treeController.addLinkedNode(tree.getId(), newNode3.getId(), newNode2, 0, "Parent", true));

		for (Response response : responses) {
			if (!response.getSuccess()) {
				return new Response(responses, "One or more failure occured", false);
			}
		}
		return new Response("All test trees have successfully been created", true);
	}
	
	@PostMapping("/tree2")
	public Response addTestTree2() {
		List<Response> responses = new ArrayList<>();

		User userTest = new User("Martin", "Martin", 1, LocalDate.of(2002, 04, 2), "France", "Cergy", "test@mail", "password1",
				"Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "https://99designs-blog.imgix.net/blog/wp-content/uploads/2016/03/web-images.jpg?auto=format&q=60&w=1600&h=824&fit=crop&crop=faces");
		responses.add(accountController.registerUser(userTest, null));

		Tree tree = userTest.getMyTree();
		Node node = nodeService.getPublicNodeByNameAndBirthInfo("Martin", "Martin", LocalDate.of(2002, 04, 2), "France", "Cergy");
		
		
		Node newNode1 = new Node("Dubois", "Sophie", 1, LocalDate.of(1990, 12, 15), "France", "Paris",
				userService.getUser(5), 0, "Française", "123 Rue de la Paix", 75001, "https://img.freepik.com/photos-premium/panda-deguise-statue-liberte-guerre-chine-etats-unis_238683-236.jpg");
		responses.add(treeController.addLinkedNode(tree.getId(), node.getId(), newNode1, 1, "partner", false));
		
		Node newNode2 = new Node("Garcia", "Carlos", 1, LocalDate.of(1985, 7, 8), "Espagne", "Barcelone",
				userService.getUser(5), 1, "Espagnol", "456 Carrer de Gràcia", 75001, "https://img.freepik.com/photos-premium/panda-noir-blanc-geant-mange-du-bambou-gros-plan-animalier_304356-320.jpg");
		responses.add(treeController.addLinkedNode(tree.getId(), newNode1.getId(), newNode2, 1, "Parent", false));

		Node newNode3 = new Node("Schneider", "Elena", 2, LocalDate.of(1995, 3, 21), "Allemagne", "Berlin",
				userService.getUser(5), 1, "Allemande", "789 Unter den Linden", 10117, "https://img.freepik.com/photos-premium/portrait-homme-affaires-costume-tete-requin-gros-plan-extreme-ia-generative_476612-24667.jpg");
		responses.add(treeController.addLinkedNode(tree.getId(), newNode1.getId(), newNode3, 1, "Parent", false));
		responses.add(treeController.addLinkedNode(tree.getId(), newNode3.getId(), newNode2, 1, "exPartner", true));

		Node newNode4 = new Node("Rossi", "Giuseppe", 1, LocalDate.of(1988, 9, 10), "Italie", "Rome",
				userService.getUser(5), 1, "Italien", "Via del Corso", 75001, "https://img.freepik.com/premium-photo/3d-cartoon-fish-shark-portrait-wearing-clothes-glasses-hat-jacket-standing-front_741910-1595.jpg");
		responses.add(treeController.addLinkedNode(tree.getId(), newNode3.getId(), newNode4, 1, "Partner", false));
		
		Node newNode5 = new Node("Smith", "Emily", 2, LocalDate.of(1980, 5, 3), "Royaume-Uni", "Londres",
				userService.getUser(5), 0, "Anglaise", "1 Buckingham Palace Road", 75001, "https://img.freepik.com/photos-premium/requin-queue-arc-ciel-corne-tete_593294-8770.jpg?w=360");
		responses.add(treeController.addLinkedNode(tree.getId(), newNode4.getId(), newNode5, 1, "Child", false));
		responses.add(treeController.addLinkedNode(tree.getId(), newNode3.getId(), newNode5, 1, "Child", true));

		
		Node newNode6 = new Node("Kim", "Jin", 1, LocalDate.of(1993, 11, 27), "Corée du Sud", "Séoul",
				userService.getUser(5), 0, "Coréen", "123 Gangnam-daero", 75001, "https://img.freepik.com/photos-premium/licorne-bande-dessinee-criniere-bleue-cheveux-roses-se-tient-fond-blanc_593294-18360.jpg");
		responses.add(treeController.addLinkedNode(tree.getId(), newNode4.getId(), newNode6, 1, "Child", false));
		responses.add(treeController.addLinkedNode(tree.getId(), newNode3.getId(), newNode6, 1, "Child", true));
		responses.add(treeController.addLinkedNode(tree.getId(), newNode6.getId(), newNode5, 1, "siblings", true));
		
		Node newNode7 = new Node("Fernandez", "Isabella", 2, LocalDate.of(1982, 6, 14), "Argentine", "Buenos Aires",
				userService.getUser(5), 1, "Argentine", "456 Avenida 9 de Julio", 75001, "https://img.freepik.com/photos-premium/licorne-bande-dessinee-corne-criniere_81048-17810.jpg");
		responses.add(treeController.addLinkedNode(tree.getId(), newNode4.getId(), newNode7, 1, "siblings", false));
		
		Node newNode8 = new Node("Ahmed", "Youssef", 1, LocalDate.of(1996, 2, 18), "Égypte", "Le Caire",
				userService.getUser(5), 0, "Égyptien", "789 Sharia El Tahrir", 75001, "https://img.freepik.com/photos-premium/licorne-bande-dessinee-corne-rose-est-fond-violet_593294-15126.jpg");
		responses.add(treeController.addLinkedNode(tree.getId(), newNode6.getId(), newNode8, 1, "parent", false));
		responses.add(treeController.addLinkedNode(tree.getId(), newNode7.getId(), newNode8, 1, "parent", true));

		Node newNode9 = new Node("Watanabe", "Aya", 2, LocalDate.of(1987, 4, 5), "Japon", "Tokyo",
				userService.getUser(5), 1, "Japonaise", "1-1 Marunouchi", 75001, "https://gweb-research-imagen.web.app/compositional/An%20oil%20painting%20of%20a%20British%20Shorthair%20cat%20wearing%20a%20cowboy%20hat%20and%20red%20shirt%20skateboarding%20on%20a%20beach./1_.jpeg");
		responses.add(treeController.addLinkedNode(tree.getId(), newNode6.getId(), newNode9, 1, "parent", false));
		responses.add(treeController.addLinkedNode(tree.getId(), newNode7.getId(), newNode9, 1, "parent", true));
		responses.add(treeController.addLinkedNode(tree.getId(), newNode9.getId(), newNode8, 1, "partner", true));

		for (Response response : responses) {
			if (!response.getSuccess()) {
				return new Response(responses, "One or more failure occured", false);
			}
		}
		return new Response(responses);
	}

	@PostMapping("/nodes")
	public void addTestNodes() {
		List<Node> nodes = new ArrayList<Node>(Arrays.asList(new Node[] {
				new Node("Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "France", "Cergy", userService.getUser(1), 1,
						"French", "Some Address", 12345, "Base64Image"),
				new Node("Cassiet", "Clement", 1, LocalDate.of(1899, 07, 9), "Péîs", "Tournant-En-Brie",
						userService.getUser(2), 2, "Peïsian", "Another Address", 54321, "Base64Image2"),
				new Node("Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Nouvel-Zélande", "Paris Hilton",
						userService.getUser(3), 1, "Kiwi", "Yet Another Address", 67890, "Base64Image3"),
				new Node("Cerf", "Fabien", 1, LocalDate.of(2002, 03, 9), "France", "Paris", userService.getUser(4), 0,
						"French", "Some Address", 98765, "Base64Image4"),
				new Node("Legrand", "Joan", 1, LocalDate.of(2002, 10, 26), "France", "Paris", userService.getUser(5), 2,
						"French", "Some Address", 54321, "Base64Image5") }));
		for (Node node : nodes) {
			nodeService.addNode(node);
		}
	}

}