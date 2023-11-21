package com.acfjj.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.acfjj.app.model.Node;
import com.acfjj.app.model.Tree;
import com.acfjj.app.service.NodeService;
import com.acfjj.app.service.TreeService;
import com.acfjj.app.service.UserService;
import com.acfjj.app.utils.Response;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
public class TreeController{	
	@Autowired
	TreeService treeService;
	@Autowired
    NodeService nodeService;
    @Autowired
    UserService userService;

		
    @GetMapping("/trees")
    public Response getTrees() {
        return new Response(treeService.getAllTrees());
    }

    @GetMapping("/tree/{id}")
    public Response getTree(@PathVariable Long id) {
        return new Response(treeService.getTree(id));
    }

    @DeleteMapping("/tree/{id}")
    public void deleteTree(@PathVariable Long id) {
    	treeService.deleteTree(id);
    }
	
    @GetMapping("/Nodes")
    public Response getNodes() {
        return new Response(nodeService.getAllNodes());
    }

    @GetMapping("/Node/{id}")
    public Response getNode(@PathVariable Long id) {
        return new Response(nodeService.getNode(id));
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
    
    @PutMapping("/addNode")
    public Response addNode(@RequestParam Node node) {
        nodeService.addNode(node);
        node = nodeService.getNode(node.getId());
		if(Objects.isNull(node)) {
			return new Response("Fail to create user's Tree: step 2 failed", false);
		}
		return new Response("Success", true);
    }
    
    @PutMapping("/addTree")
    public Response addTree(@RequestParam Tree tree) {
        treeService.addTree(tree);
        tree = treeService.getTreeByName(tree.getName());
		if(Objects.isNull(tree)) {
			return new Response("Fail to create user's Tree: step 2 failed", false);
		}
		return new Response("Success", true);
    }
    @PutMapping("/Node/addParent")
    public Response addParent(@RequestParam Long treeId, @RequestParam Node node, @RequestParam Node parent, @RequestParam int privacy, @RequestParam int wichParent) {
        treeService.addParentToNodeInTree(treeId, node, parent, privacy, wichParent);
        node = nodeService.getNode(node.getId());
		if(Objects.isNull(node)) {
			return new Response("Fail to create user's Tree: step 2 failed", false);
		}
		return new Response("Success", true);
    }
    @PutMapping("/Node/addPartner")
    public Response addPartner(@RequestParam Long treeId, @RequestParam Node node, @RequestParam Node Partner, @RequestParam int privacy) {
    	treeService.addPartnerToNodeInTree(treeId, node, Partner, privacy);
        node = nodeService.getNode(node.getId());
		if(Objects.isNull(node)) {
			return new Response("Fail to create user's Tree: step 2 failed", false);
		}
		return new Response("Success", true);
    }
    @PutMapping("/Node/addSiblings")
    public Response addSiblings(@RequestParam Long treeId, @RequestParam Node node, @RequestParam Node sibling, @RequestParam int privacy) {
    	treeService.addSiblingsToTree(treeId, node, sibling, privacy);
        node = nodeService.getNode(node.getId());
		if(Objects.isNull(node)) {
			return new Response("Fail to create user's Tree: step 2 failed", false);
		}
		return new Response("Success", true);
    }
    @PutMapping("/Node/addExPartner")
    public Response addExPartner(@RequestParam Long treeId, @RequestParam Node node, @RequestParam Node exPartner, @RequestParam int privacy) {
    	treeService.addExPartnerToNodeInTree(treeId, node, exPartner, privacy);
        node = nodeService.getNode(node.getId());
		if(Objects.isNull(node)) {
			return new Response("Fail to create user's Tree: step 2 failed", false);
		}
		return new Response("Success", true);
    }
}