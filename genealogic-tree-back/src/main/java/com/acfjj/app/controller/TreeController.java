package com.acfjj.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.acfjj.app.model.Node;
import com.acfjj.app.model.Tree;
import com.acfjj.app.service.NodeService;
import com.acfjj.app.service.TreeService;
import com.acfjj.app.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
public class TreeController{	
	@Autowired
	TreeService TreeService;
	@Autowired
    NodeService nodeService;
    @Autowired
    UserService userService;

		
    @GetMapping("/trees")
    public List<Tree> getTrees() {
        return TreeService.getAllTrees();
    }

    @GetMapping("/tree/{id}")
    public Tree getTree(@PathVariable Long id) {
        return TreeService.getTree(id);
    }

    @DeleteMapping("/tree/{id}")
    public void deleteTree(@PathVariable Long id) {
    	TreeService.deleteTree(id);
    }
	
    @GetMapping("/Nodes")
    public List<Node> getNodes() {
        return nodeService.getAllNodes();
    }

    @GetMapping("/Node/{id}")
    public Node getNode(@PathVariable Long id) {
        return nodeService.getNode(id);
    }
    
    @PutMapping("/Node/{id}/updateParent")
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