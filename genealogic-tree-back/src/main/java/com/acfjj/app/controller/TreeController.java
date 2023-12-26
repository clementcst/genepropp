package com.acfjj.app.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import com.acfjj.app.model.Node;
import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.User;
import com.acfjj.app.utils.Misc;
import com.acfjj.app.utils.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
@Scope("singleton")
public class TreeController extends AbstractController {
	@GetMapping("/trees")
	public Response getTrees() {
		return new Response(treeService.getAllTrees());
	}

	@GetMapping("/tree")
	public Response getTree(@RequestParam Long treeId) {
		return new Response(treeService.getTree(treeId));
	}

	@DeleteMapping("/tree")
	public Response deleteTree(@RequestParam Long treeId) {
		Tree tree = treeService.getTree(treeId);
		for (Node node : tree.getNodes()) {
			if (userService.getUserByNameAndBirthInfo(node.getLastName(), node.getFirstName(), node.getDateOfBirth(),
					node.getCountryOfBirth(), node.getCityOfBirth()) != null && node.getTrees().size() > 1) {
				treeService.removeNodeFromTree(tree, node);
			} else if (node.getTrees().size() == 1) {
				return new Response("Nodes cannot be without tree, so you can't delete this tree", false);
			} else {
				deleteNodeFromTree(node, treeId);
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
	public Response getNode(@RequestParam Long nodeId) {
		return new Response(nodeService.getNode(nodeId));
	}

	@DeleteMapping("/deletNode")
	public Response deleteNode(@RequestParam Node node) {
//		Tree tree = treeService.getTree(treeId);
//		if (nodeService.doesNodeBelongToTree(node.getId(), treeId) == true) {
		List<User> users = userService.getAllUsers();
		for (User user : users) {
			if (node.getCreatedBy() == user) {
				nodeService.removeLinks(node.getId());
				nodeService.deleteNode(node.getId());
				for (Node nodes : nodeService.getAllNodes()) {
					if (nodes.isOrphan()) {
						nodeService.deleteNode(nodes.getId());
					}
				}
			}

//			}
			return new Response("You cannot delete this node", false);
		}
		return new Response("This node is not in your tree", false);
	}

	@DeleteMapping("/deleteNode")
	public Response deleteNodeFromTree(@RequestParam Node node, @RequestParam long treeId) {
		Tree tree = treeService.getTree(treeId);
		treeService.removeNodeFromTree(tree, node);
		node = nodeService.getNode(node.getId());
		if (node.isOrphan()) {
			return deleteNode(node);
		}
		return new Response("The node has been removed", true);
	}

	@PutMapping("/updateNode")
	public void updateNode(@RequestParam Long nodeId, @RequestParam Node node) {
		nodeService.updateNode(nodeId, node);
	}

	@PutMapping("/addNode")
	public Response addNode(@RequestParam Node node) {
		nodeService.addNode(node);
		node = nodeService.getNode(node.getId());
		if (Objects.isNull(node)) {
			return new Response("Fail to create node", false);
		}
		return new Response("Success", true);
	}

	@PutMapping("/addTree")
	public Response addTree(@RequestParam Tree tree) {
		treeService.addTree(tree);
		tree = treeService.getTreeByName(tree.getName());
		if (Objects.isNull(tree)) {
			return new Response("Fail to create user's Tree: step 2 failed", false);
		}
		return new Response("Success", true);
	}

	@PostMapping("/updateTree")
	public Response updateTree(@RequestBody Map<String, Object> requestData, @RequestParam int userId,
			@RequestParam int treeId) {
		Long UserId = (long) userId;
		Long TreeId = (long) treeId;

		@SuppressWarnings("unchecked")
		List<LinkedHashMap<String, String>> tree = (List<LinkedHashMap<String, String>>) requestData.get("data1"); // Object
																													// data2
																													// =
																													// requestData.get("data2");
		@SuppressWarnings("unchecked")
		LinkedHashMap<Long, String> updates = (LinkedHashMap<Long, String>) requestData.get("data2");
		if (updates.size() <= 0) {
			return new Response("no change detected", false);
		}
		Response response = firstVerifications(tree, UserId);
		if (!response.getSuccess()) {
			return response;
		}
		LinkedHashMap<Long, Node> existingNodes = new LinkedHashMap<Long, Node>();
		LinkedHashMap<Long, Node> nodeToAdd = new LinkedHashMap<Long, Node>();
		LinkedHashMap<Long, String> unknownRelation = new LinkedHashMap<Long, String>();

		for (LinkedHashMap<String, String> node : tree) {
			// ajout vérif de Joan
			Long id = Misc.convertObjectToLong(node.get("id"));
			User creator = userService.getUser(id >= 0 ? Misc.convertObjectToLong(node.get("createdById")) : UserId);

			Node parent1 = getNodeIfNonNegative(Misc.convertObjectToLong(node.get("parent1Id")));
			Node parent2 = getNodeIfNonNegative(Misc.convertObjectToLong(node.get("parent2Id")));
			Node partner = getNodeIfNonNegative(Misc.convertObjectToLong(node.get("partnerId")));

			if (id >= 0) {
				if (parent1 == null || parent2 == null || partner == null) {
					String allRelations = "Parent1." + Misc.convertObjectToLong(node.get("parent1Id")) + ".Parent2."
							+ Misc.convertObjectToLong(node.get("parent2Id")) + ".Partner."
							+ Misc.convertObjectToLong(node.get("partnerId"));
					unknownRelation.put(id, allRelations);
				}
				existingNodes.put(id, Node.CastAsNode(node, creator, parent1, parent2, partner));
			} else {
				nodeToAdd.put(id, Node.CastAsNode(node, creator, null, null, null));
				String allRelations = "Parent1." + Misc.convertObjectToLong(node.get("parent1Id")) + ".Parent2."
						+ Misc.convertObjectToLong(node.get("parent2Id")) + ".Partner."
						+ Misc.convertObjectToLong(node.get("partnerId"));
				unknownRelation.put(id, allRelations);
			}
		}

		// test si droit d'update les nodes
		for (Map.Entry<Long, String> nodeUpdate : updates.entrySet()) {
			Long id = Misc.convertObjectToLong(nodeUpdate.getKey());
			if ("UPDATE".equals(nodeUpdate.getValue().toUpperCase())
					&& nodeService.getNode(id).getCreatedById() != UserId) {
				return new Response("the node of " + nodeService.getNode(id).getFirstName()
						+ " does not belong to you, you cannot update it", false);
			}
		}

		for (Map.Entry<Long, String> newOrUpdatedNode : updates.entrySet()) {
			Long id = Misc.convertObjectToLong(newOrUpdatedNode.getKey());
			String key = newOrUpdatedNode.getValue();
			String[] child = null;

			if (!Objects.isNull(unknownRelation.get(id))) {
				child = unknownRelation.get(id).split("\\.");
			}

			switch (key.toUpperCase()) {
			case "PARENT":
				addParentRelations(id, TreeId, child, nodeToAdd, existingNodes, unknownRelation, updates);
				break;
			case "PARTNER":
				addPartnerRelations(id, TreeId, child, nodeToAdd, existingNodes, unknownRelation, updates);
				break;
			case "SIBLINGS":
				// Pas fait pour l'instant
				break;
//					case "EXPARTNER":
//						if(!existingNodes.get(id).getExPartnersId().isEmpty() && existingNodes.get(id).getPartnerId() < 0) {
//							addLinkedNode(treeId, id, nodeToAdd.get(existingNodes.get(id).getPartnerId()), nodeToAdd.get(existingNodes.get(id).getPartnerId()).getPrivacy() ,"Partner",false);
//							nodeToAdd.remove(nodeService.getNode(id).getPartnerId());
//							existingNodes.put(nodeService.getNode(id).getPartnerId(), nodeService.getNode(id).getPartner());	
//						}
//						break;
			case "CHILD":
				addChildRelations(id, TreeId, child, nodeToAdd, existingNodes, unknownRelation, updates);
				break;
			case "UPDATE":
				nodeService.updateWithoutRelation(id, existingNodes.get(id));
				break;
			default:
				return new Response("there is a problem with " + nodeToAdd.get(id).getFirstName(), false);
			}
		}

		if (!nodeToAdd.isEmpty()) {
			return new Response("Something went wrong", false);
		}

		return new Response("Success", true);
	}

	@PutMapping("/node/addNodeWithLink")
	public Response addLinkedNode(@RequestParam Long treeId, @RequestParam long linkedNodeId,
			@RequestParam Node nodeToAdd, @RequestParam int privacy, @RequestParam String type,
			@RequestParam boolean alreadyInTree) {
		if (!Objects.isNull(nodeToAdd.getPrivacy())) {
			privacy = nodeToAdd.getPrivacy();
		}
		Node linkedNode = nodeService.getNode(linkedNodeId);
//    	if(!alreadyInTree) {
//    		if(!canCreateNodeInTree(nodeToAdd, treeId)){
//    			return new Response("Node cannot be added", false);
//    		}
//    	}
//    	if(autorizedLink(treeId, linkedNode, nodeToAdd)) {
		switch (type.toUpperCase()) {
		case "PARENT":
			if (linkedNode.getParent1() == nodeToAdd || linkedNode.getParent2() == nodeToAdd) {
				return new Response("Link already set", false);
			}
			treeService.addParentToNodeInTree(treeId, linkedNode, nodeToAdd, privacy);
			break;
		case "PARTNER":
			if (nodeToAdd.getPartner() == linkedNode || linkedNode.getPartner() == nodeToAdd) {
				return new Response("Link already set", false);
			}
			treeService.addPartnerToNodeInTree(treeId, linkedNode, nodeToAdd, privacy);
			break;
		case "SIBLINGS":
			for (Node sibilng : linkedNode.getSiblings()) {
				if (sibilng == nodeToAdd) {
					return new Response("Link already set", false);
				}
			}
			treeService.addSiblingsToTree(treeId, linkedNode, nodeToAdd, privacy);
			break;
		case "EXPARTNER":
			for (Node exPartnerNode : linkedNode.getExPartners()) {
				if (exPartnerNode == nodeToAdd) {
					return new Response("Link already set", false);
				}
			}
			treeService.addExPartnerToNodeInTree(treeId, linkedNode, nodeToAdd, privacy);
			break;
		case "CHILD":
			if (nodeToAdd.getParent1() == linkedNode || nodeToAdd.getParent2() == linkedNode) {
				return new Response("Link already set", false);
			}
			treeService.addChildToNodeInTree(treeId, linkedNode, nodeToAdd, privacy);
			break;
		default:
			return new Response("No link founded", false);
		}
		nodeToAdd = nodeService.getNodeByNameAndBirthInfo(nodeToAdd.getLastName(), nodeToAdd.getFirstName(),
				nodeToAdd.getDateOfBirth(), nodeToAdd.getCountryOfBirth(), nodeToAdd.getCityOfBirth());
		if (Objects.isNull(nodeToAdd)) {
			return new Response("Fail to create node in Tree", false);
		}
		return new Response("Success", true);
//    	}   
//    	return new Response("Link not possible", false);
	}

	// faire les remove links

	public boolean canCreateNodeInTree(Node node, Long treeId,
			@RequestParam(required = false, defaultValue = "0") Boolean userResponse) {
		if (nodeService.getNode(node.getId()) != null) {
			if (userService.getUserByNameAndBirthInfo(node.getLastName(), node.getFirstName(), node.getDateOfBirth(),
					node.getCountryOfBirth(), node.getCityOfBirth()) != null) {
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
			if (nodeService.doesNodeBelongToTree(node.getId(), treeId) == true) {
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
	public boolean autorizedLink(Long treeId, Node node, Node nodeToLink) {
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
				for (Node siblings : node.getSiblings()) {
					if (siblings == nodeToLink) {
						return false;
					}
				}
			}
			if (node.getExPartners() != null) {
				for (Node exPartners : node.getExPartners()) {
					if (exPartners == nodeToLink) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public Response firstVerifications(List<LinkedHashMap<String, String>> tree, Long userConnectedId) {
		for (LinkedHashMap<String, String> node : tree) {
//			if(!Misc.birthIsPossible((String) node.get("dateOfBirth"), (String) node.get("dateOfDeath"))) {
//				return new Response(node.get("fisrtName") + "died before he lived", false);
//			}			
			if (node != null) {
				LinkedHashMap<String, String> parent1;
				LinkedHashMap<String, String> parent2;
				if (node.containsKey("parent1Id") && !Objects.isNull(node.get("parent1Id"))) {
//					Object parent = (Object) ;
					Long parent1Id = Misc.convertObjectToLong(node.get("parent1Id"));
					parent1 = findNodeWithId(tree, parent1Id);
					if (!Misc.parentIsOlder((String) parent1.get("dateOfBirth"), (String) node.get("dateOfBirth"))) {
						return new Response(parent1.get("firstName") + "is younger than " + node.get("firstName"),
								false);
					}
					if (!Misc.parentWasAlive((String) parent1.get("dateOfDeath"), (String) node.get("dateOfBirth"))) {
						return new Response(
								parent1.get("fisrtName") + "was dead for the birth of " + node.get("firstName"), false);
					}

				}

				if (node.containsKey("parent2Id") && !Objects.isNull(node.get("parent2Id"))) {
					Long parent2Id = Misc.convertObjectToLong(node.get("parent2Id"));
					parent2 = findNodeWithId(tree, parent2Id);

					if (!Misc.parentIsOlder((String) parent2.get("dateOfBirth"), (String) node.get("dateOfBirth"))) {
						return new Response(parent2.get("fisrtName") + "is younger than " + node.get("firstName"),
								false);
					}
					if (!Misc.parentWasAlive((String) parent2.get("dateOfDeath"), (String) node.get("dateOfBirth"))) {
						return new Response(
								parent2.get("fisrtName") + "was dead for the birth of " + node.get("firstName"), false);
					}
				}
			}

		}
		return new Response("Success", true);
	}

	private static LinkedHashMap<String, String> findNodeWithId(List<LinkedHashMap<String, String>> tree, Long id) {
		for (LinkedHashMap<String, String> node : tree) {
			if (node != null && node.containsKey("id") && Misc.convertObjectToLong(node.get("id")) == id) {
				return node;
			}
		}
		return null;
	}

	private Node getNodeIfNonNegative(Long nodeId) {
		return (nodeId > 0) ? nodeService.getNode(nodeId) : null;
	}

	public static LinkedHashMap<Long, String> replaceId(LinkedHashMap<Long, String> relationsMap, Long oldId,
			Long idInDb) {
		for (Map.Entry<Long, String> entry : relationsMap.entrySet()) {
			Long key = entry.getKey();
			String relationsString = entry.getValue();

			String[] parts = relationsString.split("\\.");

			for (int i = 1; i < parts.length; i += 2) {
				if (Long.parseLong(parts[i]) == oldId) {
					parts[i] = String.valueOf(idInDb);
				}
			}
			StringBuilder updatedRelations = new StringBuilder();
			for (String part : parts) {
				updatedRelations.append(part).append(".");
			}
			relationsMap.put(key, updatedRelations.toString());
		}
		return relationsMap;
	}

	public static LinkedHashMap<Long, String> replaceIdInUpdates(LinkedHashMap<Long, String> updates, Long oldId,
			Long idInDb) {
		LinkedHashMap<Long, String> newUpdates = new LinkedHashMap<>();
		for (Map.Entry<Long, String> entry : updates.entrySet()) {
			Long updatedKey = entry.getKey() == oldId ? Misc.convertObjectToLong(idInDb)
					: Misc.convertObjectToLong(entry.getKey());
			newUpdates.put(updatedKey, entry.getValue());
		}
		return newUpdates;
	}

	private void addParentRelations(Long id, Long TreeId, String[] child, LinkedHashMap<Long, Node> nodeToAdd,
			LinkedHashMap<Long, Node> existingNodes, LinkedHashMap<Long, String> unknownRelation,
			LinkedHashMap<Long, String> updates) {
		Long newIdInDb = null;

		if (Misc.convertObjectToLong(child[1]) < 0) {
			addLinkedNode(TreeId, id, nodeToAdd.get(Misc.convertObjectToLong(child[1])),
					nodeToAdd.get(Misc.convertObjectToLong(child[1])).getPrivacy(), "PARENT", false);
			nodeToAdd.remove(Misc.convertObjectToLong(child[1]));
			existingNodes.put(nodeService.getNode(id).getParent1Id(), nodeService.getNode(id).getParent1());
			unknownRelation = replaceId(unknownRelation, Misc.convertObjectToLong(child[1]),
					nodeService.getNode(id).getParent1Id());
			updates = replaceIdInUpdates(updates, Misc.convertObjectToLong(child[1]),
					nodeService.getNode(id).getParent1Id());
			newIdInDb = nodeService.getNode(id).getParent1Id();
		}

		if (Misc.convertObjectToLong(child[3]) < 0) {
			if (Objects.isNull(nodeToAdd.get(Misc.convertObjectToLong(child[3]))) && !Objects.isNull(newIdInDb)) {
				addLinkedNode(TreeId, id, existingNodes.get(newIdInDb), existingNodes.get(newIdInDb).getPrivacy(),
						"Parent", true);
			} else {
				addLinkedNode(TreeId, id, nodeToAdd.get(Misc.convertObjectToLong(child[3])),
						nodeToAdd.get(Misc.convertObjectToLong(child[3])).getPrivacy(), "PARENT", false);
				nodeToAdd.remove(Misc.convertObjectToLong(child[3]));
				existingNodes.put(nodeService.getNode(id).getParent2Id(), nodeService.getNode(id).getParent2());
				unknownRelation = replaceId(unknownRelation, Misc.convertObjectToLong(child[3]),
						nodeService.getNode(id).getParent2Id());
				updates = replaceIdInUpdates(updates, Misc.convertObjectToLong(child[3]),
						nodeService.getNode(id).getParent2Id());
			}
		}
	}

	private void addPartnerRelations(Long id, Long TreeId, String[] child, LinkedHashMap<Long, Node> nodeToAdd,
			LinkedHashMap<Long, Node> existingNodes, LinkedHashMap<Long, String> unknownRelation,
			LinkedHashMap<Long, String> updates) {
		if (Misc.convertObjectToLong(child[5]) != 0) {
			if (Objects.isNull(nodeToAdd.get(id))) {
				return;
			} else {
				addLinkedNode(TreeId, Misc.convertObjectToLong(child[5]), nodeToAdd.get(id),
						nodeToAdd.get(id).getPrivacy(), "Partner", false);
				nodeToAdd.remove(id);
				existingNodes.put(nodeService.getNode(Misc.convertObjectToLong(child[5])).getPartnerId(),
						nodeService.getNode(Misc.convertObjectToLong(child[5])).getPartner());
				unknownRelation = replaceId(unknownRelation, Misc.convertObjectToLong(child[5]),
						nodeService.getNode(Misc.convertObjectToLong(child[5])).getPartnerId());
				updates = replaceIdInUpdates(updates, Misc.convertObjectToLong(child[5]),
						nodeService.getNode(Misc.convertObjectToLong(child[5])).getPartnerId());
			}
		}
	}

	private void addChildRelations(Long id, Long TreeId, String[] child, LinkedHashMap<Long, Node> nodeToAdd,
			LinkedHashMap<Long, Node> existingNodes, LinkedHashMap<Long, String> unknownRelation,
			LinkedHashMap<Long, String> updates) {
		Long idInDb = null;
		int privacy = nodeToAdd.get(id).getPrivacy();

		if (Misc.convertObjectToLong(child[1]) > 0) {
			addLinkedNode(TreeId, Misc.convertObjectToLong(child[1]), nodeToAdd.get(id), privacy, "CHILD", false);
			Node nodeInDb = nodeService.getNodeByNameAndBirthInfo(nodeToAdd.get(id).getLastName(),
					nodeToAdd.get(id).getFirstName(), nodeToAdd.get(id).getDateOfBirth(),
					nodeToAdd.get(id).getCountryOfBirth(), nodeToAdd.get(id).getCityOfBirth());
			existingNodes.put(nodeInDb.getId(), nodeInDb);
			nodeToAdd.remove(id);
			unknownRelation = replaceId(unknownRelation, id, nodeInDb.getId());
			idInDb = nodeInDb.getId();
		}

		if (Misc.convertObjectToLong(child[3]) > 0) {
			if (Objects.isNull(nodeToAdd.get(Misc.convertObjectToLong(id))) && !Objects.isNull(idInDb)) {
				addLinkedNode(TreeId, Misc.convertObjectToLong(child[3]), existingNodes.get(idInDb),
						existingNodes.get(idInDb).getPrivacy(), "Child", true);
			} else {
				addLinkedNode(TreeId, Misc.convertObjectToLong(child[3]), nodeToAdd.get(id), privacy, "CHILD", false);
				Node nodeInDb = nodeService.getNodeByNameAndBirthInfo(nodeToAdd.get(id).getLastName(),
						nodeToAdd.get(id).getFirstName(), nodeToAdd.get(id).getDateOfBirth(),
						nodeToAdd.get(id).getCountryOfBirth(), nodeToAdd.get(id).getCityOfBirth());
				existingNodes.put(nodeInDb.getId(), nodeInDb);
				nodeToAdd.remove(id);
				unknownRelation = replaceId(unknownRelation, id, nodeInDb.getId());
			}
		}
	}

}