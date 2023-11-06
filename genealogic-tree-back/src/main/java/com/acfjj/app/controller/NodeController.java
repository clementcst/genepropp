package com.acfjj.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.acfjj.app.model.Node;
import com.acfjj.app.service.NodeService;
import com.acfjj.app.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
public class NodeController {

    @Autowired
    NodeService nodeService;
    @Autowired
    UserService userService;


    @PostMapping("/Nodes")
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

    //faire des set parents
    @GetMapping("/Nodes")
    public List<Node> getNodes() {
        return nodeService.getAllNodes();
    }

    @GetMapping("/Node/{id}")
    public Node getNode(@PathVariable Long id) {
        return nodeService.getNode(id);
    }
    
    @PutMapping("/Node/{id}/updateParent") // exemple : localhost:8080/Node/2/updateParent?parentId=3&parentNumber=1
    public void updateParent(@PathVariable Long id, @RequestParam Long parentId, @RequestParam int parentNumber) {
        nodeService.updateParent(id, parentId, parentNumber);
    }


    @DeleteMapping("/Node/{id}")
    public void deleteNode(@PathVariable Long id) {
        nodeService.deleteNode(id);
    }

    @PutMapping("/Node/{id}")
    public void updateNode(@PathVariable Long id, @RequestBody Node node) {
        nodeService.updateNode(id, node);
    }
}
