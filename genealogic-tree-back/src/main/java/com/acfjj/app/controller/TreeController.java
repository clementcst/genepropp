package com.acfjj.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.acfjj.app.model.Node;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.User;
import com.acfjj.app.service.NodeService;
import com.acfjj.app.service.TreeService;
import com.acfjj.app.service.UserService;
import com.acfjj.app.utils.Response;

import java.time.LocalDate;
import java.util.List;
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

    @GetMapping("/tree")
    public Response getTree(@RequestParam Long id) {
        return new Response(treeService.getTree(id));
    }

    @DeleteMapping("/tree")
    public Response deleteTree(@RequestParam Long treeId) {
    	Tree tree = treeService.getTree(treeId);
    	for(Node node : tree.getNodes()) {
    		if(userService.getUserByNameAndBirthInfo(node.getLastName(), node.getFirstName(), node.getDateOfBirth(), node.getCountryOfBirth(), node.getCityOfBirth()) != null && node.getTrees().size() > 1) {
    			treeService.removeNodeFromTree(tree, node);
    		}
    		else if(node.getTrees().size() == 1){
    			return new Response("Nodes cannot be without tree, so you can't delete this tree", false);
    		}
    		else {
    			deleteNodeInTree(node, treeId);
    		}
    	}
    	treeService.deleteTree(treeId);
    	return new Response("Success", true);
    }
	
    @GetMapping("/nodes")
    public Response getNodes() {
        return new Response(nodeService.getAllNodes());
    }

    @GetMapping("/node")
    public Response getNode(@RequestParam Long id) {
        return new Response(nodeService.getNode(id));
    }


    @DeleteMapping("/node")
    public Response deleteNodeInTree(@RequestParam Node node, @RequestParam Long treeId) {
    	Tree tree = treeService.getTree(treeId);
    	if(nodeService.doesNodeBelongToTree(node, treeId) == true) {
    		List<User> users = userService.getAllUsers();
    		for (User user : users) {
    			if(node.getCreatedBy() == user) {
    				nodeService.removeLinks(node.getId());
    				nodeService.deleteNode(node.getId());
    				for(Node nodes : tree.getNodes()) {
    					if(nodes.isOrphan()) {
    						nodeService.deleteNode(nodes.getId());
    					}
    				}
    			}
    			
    		}
    		return new Response("You cannot delete this node", false);
    	}
    	return new Response("This node is not in your tree", false);
    }

    @PutMapping("/updateNode")
    public void updateNode(@RequestParam Long id, @RequestParam Node node) {
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
    
    @PutMapping("/node/addNodeWithLink")
    public Response addLinkedNode(@RequestParam Long treeId, @RequestParam long linkedNodeId, @RequestParam Node nodeToAdd, @RequestParam int privacy, @RequestParam String type, @RequestParam boolean alreadyInTree) {
    	Node linkedNode = nodeService.getNode(linkedNodeId);
    	if(!alreadyInTree) {
    		if(!canCreateNodeInTree(nodeToAdd, treeId)){
    			return new Response("Node cannot be added", false);
    		}
    	}
    	if(autorizedLink(treeId, linkedNode, nodeToAdd)) {
    		switch(type.toUpperCase()) {
	    		case "PARENT" :
	    			treeService.addParentToNodeInTree(treeId, linkedNode, nodeToAdd, privacy);
	        		break;
				case "PARTNER" :
					treeService.addPartnerToNodeInTree(treeId, linkedNode, nodeToAdd, privacy);
				    break;
				case "SIBLINGS" :
					treeService.addSiblingsToTree(treeId, linkedNode, nodeToAdd, privacy);
	        		break;
				case "EXPARTNER" :
					treeService.addExPartnerToNodeInTree(treeId, linkedNode, nodeToAdd, privacy);
	        		break;
				default :
					return new Response("No link founded", false);
	        }    
			nodeToAdd = nodeService.getNodeByNameAndBirthInfo(nodeToAdd.getLastName(), nodeToAdd.getFirstName(), nodeToAdd.getDateOfBirth(), nodeToAdd.getCountryOfBirth(), nodeToAdd.getCityOfBirth());
			if(Objects.isNull(nodeToAdd)) {
				return new Response("Fail to create node in Tree", false);
			}
			return new Response("Success", true);
    	}   
    	return new Response("Link not possible", false);
    }
    
    // faire les remove links
    
    public boolean canCreateNodeInTree(Node node, Long treeId) {
    	if(nodeService.getNode(node.getId()) != null) {
    		if(userService.getUserByNameAndBirthInfo(node.getLastName(), node.getFirstName(), node.getDateOfBirth(), node.getCountryOfBirth(), node.getCityOfBirth()) != null) {
//    			if(C est bien lui ?){
//	    		    if(V1)
//	    			message à l'autre gars pour qu'il accepte de rejoindre ton arbre
//	    				return false;
//	    		    else if V2{
//	    				if(le user accepte d'aller dans ton arbre){
//	    					return true;
//	    			    }
//	    				else { // il accepte pas
//	    					if(veux créer en PV){
//	    						créer lien en PV
//	    					} else {
//	    							abandon, on va rien faire
//	    					  }	
//	    			
//	    		        }
//	    		    }
//    		    } else if(bonnes infos){
//    					
//    				} else {
//    						restart le process
//    					}
    		}    		
    		if(nodeService.doesNodeBelongToTree(node, treeId) == true) {
//    			message user already in tree
    			return false;
    		} else {
//    			Montrer arbre de la node que tu veux ajouter, bien lui ?{
//    				if(lier vos arbres ?) {
//    					V1
//    						Message au créateur pour savoir s'il est ok
//    					V2
//    						S'il est ok {
//    							Lier les arbres
//    						} else{
//    							Créer en PV, oui non ?
//    						}
//    				}
//    					Créer en pv ? oui, non
//    			}
//    				if(Bonnes infos){
//    					Créer en pv ? oui, non
//    				} else {
//    					abandon
//    				}    			
    		}
    	}
    	return true;    	
    }
   
    
    
//    faire les sécurités des links
    public boolean autorizedLink(Long treeId,  Node node, Node nodeToLink) {
//    	Tree tree = treeService.getTree(treeId);
    	if (node != null) {        	
            if (node.getParent1() == nodeToLink) {
         	   return false;
            }
            if (node.getParent2() == nodeToLink) {
         	   return false;        	   
            }
            if (node.getPartner() == nodeToLink) {
            	return false;  
            }
            if (node.getSiblings() != null) {
         	   for(Node siblings : node.getSiblings()) {
         		  if (siblings == nodeToLink) {
                	   return false;        	   
                   }
         	   } 
            }
            if (node.getExPartners() != null) {
         	   for(Node exPartners : node.getExPartners()) {
         		  if (exPartners == nodeToLink) {
               	   return false;        	   
                  }
         	   }
            }
    	}
    	return true;
    }
}