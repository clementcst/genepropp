package com.acfjj.app.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import com.acfjj.app.model.Node;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.model.User;
import com.acfjj.app.utils.Constants;
import com.acfjj.app.utils.Misc;
import com.acfjj.app.utils.Response;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
@Scope("singleton")
public class TreeController extends AbstractController {
	@GetMapping("/tree")
	public Response getTree(@RequestParam Long treeId) {
		Tree tree = treeService.getTree(treeId);
		if (Objects.isNull(tree)) {
			return new Response("Tree not found", false);
		}
		return new Response(tree);
	}

	@PostMapping("/tree/addView")
	public Response addView(@RequestParam Long treeId) {
		Tree tree = treeService.getTree(treeId);
		if (Objects.isNull(tree)) {
			return new Response("Tree not found", false);
		}
		tree.addAView();
		treeService.updateTree(tree.getId(), tree);
		return new Response("Success", true);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/updateTree")
	public Response updateTree(@RequestBody Map<String, Object> requestData, @RequestParam int userId,
			@RequestParam int treeId) {
		// Init
		Long UserId = (long) userId;
		Long TreeId = (long) treeId;

		User user = userService.getUser(UserId);
		if (Objects.isNull(user)) {
			return new Response("User not found", false);
		}
		if (conversationService.userHasTreeMergeValidationsOnGoing(user)) {
			return new Response(
					"There is a Tree Merge validation process that concerns you on going. Saving your tree is impossible while",
					false);
		}
		
		List<LinkedHashMap<String, String>> tree;
		LinkedHashMap<Long, String> updates;
		try {
			tree = (List<LinkedHashMap<String, String>>) requestData.get("data1");
			updates = (LinkedHashMap<Long, String>) requestData.get("data2");
		} catch (Exception e) {
			return new Response("Invalid format", false);
		}

		if (updates.size() <= 0) {
			return new Response("No change detected", false);
		}
		LinkedHashMap<Long, Node> existingNodes = new LinkedHashMap<Long, Node>();
		LinkedHashMap<Long, Node> nodesToAdd = new LinkedHashMap<Long, Node>();
		LinkedHashMap<Long, String> unknownRelation = new LinkedHashMap<Long, String>();

		// LHM Check
		String responseStr = "";
		boolean responseSuccess = true;
		for (LinkedHashMap<String, String> node : tree) {
			Response LHMres = Misc.LHMCheck(node, "NODE");
			if (!LHMres.getSuccess()) {
				responseStr += node.containsKey("lastName") && node.containsKey("firstName")
						? "\n\nFormat problem found in node " + node.get("lastName") + " " + node.get("firstName")
								+ LHMres.getMessage() + "\n"
						: "\n\nFormat problem found in node " + node.get("id") + LHMres.getMessage() + "\n";
				responseSuccess = false;
			}
		}

		// First Verifications
		String firstVerifStr = firstVerifications(tree, UserId);
		responseStr += firstVerifStr != "OK" ? firstVerifStr : "";
		responseSuccess = firstVerifStr != "OK" ? false : responseSuccess;

		// Separation between existing nodes and node to add
		// Init unknownRelation => all links that will be used to add nodes
		for (LinkedHashMap<String, String> node : tree) {
			Long id = Misc.convertObjectToLong(node.get("id"));
			User creator = userService.getUser(id >= 0 ? Misc.convertObjectToLong(node.get("createdById")) : UserId);
			// security creator not found

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
				nodesToAdd.put(id, Node.CastAsNode(node, creator, null, null, null));
				String allRelations = "Parent1." + Misc.convertObjectToLong(node.get("parent1Id")) + ".Parent2."
						+ Misc.convertObjectToLong(node.get("parent2Id")) + ".Partner."
						+ Misc.convertObjectToLong(node.get("partnerId"));
				unknownRelation.put(id, allRelations);
			}
		}

		// Verify Permissions (Case DELETE and UPDATE)
		for (Map.Entry<Long, String> nodeUpdate : updates.entrySet()) {
			Long id = Misc.convertObjectToLong(nodeUpdate.getKey());
			Node node = nodeService.getNode(id);
			Node currentNode = ("UPDATE".equals(nodeUpdate.getValue().toUpperCase())
					|| "DELETE".equals(nodeUpdate.getValue().toUpperCase())) ? existingNodes.get(id) : null;
			if(nodeUpdate.getValue().toUpperCase().equals("UPDATE") || nodeUpdate.getValue().toUpperCase().equals("DELETE")) {
				if (!Objects.isNull(node)) {
					if ("UPDATE".equals(nodeUpdate.getValue().toUpperCase())
							&& nodeService.getNode(id).getCreatedById() != UserId) {
						responseStr += "the node of " + nodeService.getNode(id).getFullName()
								+ " does not belong to you, you cannot update it\n";
						responseSuccess = false;
					}
					Node nodeInDb = null;
					if(!Objects.isNull(currentNode)) {
						nodeInDb = nodeService.getNodeByNameAndBirthInfo(currentNode.getLastName(),
								currentNode.getFirstName(), currentNode.getDateOfBirth(),
								currentNode.getCountryOfBirth(), currentNode.getCityOfBirth());
					}					
					if ("UPDATE".equals(nodeUpdate.getValue().toUpperCase())
							&& !Objects.isNull(nodeInDb) && nodeInDb.getId() != currentNode.getId()) {
						responseStr += "Cannot update the node with these info : "
								+ nodeService.getNode(id).getFullNameAndBirthInfo()
								+ " because it already exist in the database. Try to create it from start to go further in a merge procedure\n";
						responseSuccess = false;
					}
					if ("DELETE".equals(nodeUpdate.getValue().toUpperCase())
							&& nodeService.getNode(id).getCreatedById() != UserId) {
						responseStr += "the node of " + nodeService.getNode(id).getFullName()
								+ " does not belong to you, you cannot delete it\n";
						responseSuccess = false;
					}
					if ("DELETE".equals(nodeUpdate.getValue().toUpperCase()) && nodeService.getNode(id).getPersonInfo()
							.getId() == userService.getUser(userId).getPersonInfo().getId()) {
						responseStr += "This is your node, you cannot delete the main node. You cannot delete your tree either if that's your goal.\n";
						responseSuccess = false;
					}
				} else {
					if (nodeUpdate.getValue().equals("UPDATE") || nodeUpdate.getValue().equals("DELETE")) {
						responseStr += "Cannot update or delete node with id " + id
								+ " because it has not been found in databased\n";
						responseSuccess = false;
					}
				}
			}
			
		}
		// Post Check return if problem
		if (!responseSuccess) {
			return new Response(responseStr, responseSuccess);
		}

		// Check all Actions Type are allowed
		for (Map.Entry<Long, String> newOrUpdatedNode : updates.entrySet()) {
			String key = newOrUpdatedNode.getValue();
			if (!Constants.POSSIBLE_NODE_ACTIONS.contains(key)) {
				return new Response("No Problem was found in the tree, but an action type has an incorrect value : "
						+ key + " for entry " + newOrUpdatedNode.getKey()
						+ " \nThat is not a normal error, pls contact support.", false);
			}
		}
		// Reorder updates to align Parent and Partner elements based on relationships
		// in unknownRelation		
		updates = reorderUpdates(updates, unknownRelation);
		// True Process
		for (Map.Entry<Long, String> newOrUpdatedNode : updates.entrySet()) {
			Long id = Misc.convertObjectToLong(newOrUpdatedNode.getKey());
			String key = newOrUpdatedNode.getValue();
			if (Constants.POSSIBLE_NODE_ACTIONS.contains(key)) {
				String[] relatedToNodes = Objects.isNull(unknownRelation.get(id)) ? null
						: unknownRelation.get(id).split("\\.");
				if(Constants.POSSIBLE_NODE_ACTIONS_CREATION.contains(key)) {
					Response BaFRes = sendPreProcessNodeInTreeCreation(key, id, TreeId, user, relatedToNodes, nodesToAdd,
							existingNodes, unknownRelation);
					if (!Objects.isNull(BaFRes) && !BaFRes.getSuccess() || !Objects.isNull(BaFRes)  && (BaFRes.getSuccess() && !Objects.isNull(BaFRes.getValue()))) {
						return BaFRes;
					}
				}	
				switch (key.toUpperCase()) {
				case "PARENT":
					addParentRelations(id, TreeId, relatedToNodes, nodesToAdd, existingNodes, unknownRelation, updates);
					break;
				case "PARTNER":
					addPartnerRelations(id, TreeId, relatedToNodes, nodesToAdd, existingNodes, unknownRelation,
							updates);
					break;
				case "SIBLINGS":
					// Useless ATM, so not implemented
					break;
				case "EXPARTNER":
					// Useless ATM, so not implemented
					break;
				case "CHILD":
					addChildRelations(id, TreeId, relatedToNodes, nodesToAdd, existingNodes, unknownRelation, updates);
					break;
				case "UPDATE":
					nodeService.updateWithoutRelation(id, existingNodes.get(id));
					break;
				case "DELETE":
					deleteNodeFromTree(nodeService.getNode(id), TreeId);
					break;
				default:
					return new Response("Incorrect Parameter update " + key, false);
				}
			}
		}

		if (!nodesToAdd.isEmpty()) {
			return new Response("Something went wrong", false);
		}
		
		return new Response("Tree Updated successfully", true);
	}
	
	 private static LinkedHashMap<Long, String> reorderUpdates(LinkedHashMap<Long, String> updates, LinkedHashMap<Long, String> unknownRelation) {
	        LinkedHashMap<Long, String> tmpUpdates = new LinkedHashMap<>(updates);

	        for (Map.Entry<Long, String> entry : tmpUpdates.entrySet()) {
	            Long id = Misc.convertObjectToLong(entry.getKey());
	            String value = entry.getValue();
	            // Check if value is Parent
	            if ("CHILD".equals(value.toUpperCase())) {
	                // Check next element
	                Map.Entry<Long, String> nextEntry = getNextEntry(tmpUpdates, id);
	                if (nextEntry != null && "PARTNER".equals(nextEntry.getValue().toUpperCase())) {
	                    // Check if next element is also a parent
	                	String[] relatedToNodes = Objects.isNull(unknownRelation.get(id)) ? null
	    						: unknownRelation.get(id).split("\\.");
	                    if (relatedToNodes != null && Misc.convertObjectToLong(relatedToNodes[1]) == Misc.convertObjectToLong(nextEntry.getKey()) || relatedToNodes != null && Misc.convertObjectToLong(relatedToNodes[3]) == Misc.convertObjectToLong(nextEntry.getKey()) ) {
	                        // swap elements
	                    	updates = swapElements(updates, id, Misc.convertObjectToLong(nextEntry.getKey()));
	                    }
	                }
	            }
	        }
	        return updates;
	    }
	 
	 @SuppressWarnings("unlikely-arg-type")
	private static LinkedHashMap<Long, String> swapElements(LinkedHashMap<Long, String> map, Long key1, Long key2) {
		    LinkedHashMap<Long, String> updatedMap = new LinkedHashMap<>();

		    for (Map.Entry<Long, String> entry : map.entrySet()) {
		        if (Misc.convertObjectToLong(entry.getKey()) == key1) {
		            break;
		        }
		        updatedMap.put(Misc.convertObjectToLong(entry.getKey()), entry.getValue());
		    }
		    updatedMap.put(key2, map.get(key2.toString()));
		    updatedMap.put(key1, map.get(key1.toString()));
		    boolean afterkey2 = false;
		    for (Map.Entry<Long, String> entry : map.entrySet()) {
		        if (Misc.convertObjectToLong(entry.getKey()) == key2) {
		            afterkey2 = true;
		            continue;
		        }
		        if (afterkey2) {
		            updatedMap.put(Misc.convertObjectToLong(entry.getKey()), entry.getValue());
		        }
		    }
		    return updatedMap;
		}



	 private static Map.Entry<Long, String> getNextEntry(LinkedHashMap<Long, String> updates, Long id) {
	        boolean found = false;
	        for (Map.Entry<Long, String> entry : updates.entrySet()) {
	            if (found) {
	                return entry;
	            }
	            if (Misc.convertObjectToLong(entry.getKey())==(id)) {
	                found = true;
	            }
	        }
	        return null;
	    }

	public Response deleteTree(@RequestParam Long treeId) {
		Tree tree = treeService.getTree(treeId);
		for (Node node : tree.getNodes()) {
			if (userService.getUserByNameAndBirthInfo(node.getLastName(), node.getFirstName(), node.getDateOfBirth(),
					node.getCountryOfBirth(), node.getCityOfBirth()) != null && node.getTrees().size() > 1) {
				treeService.removeNodeFromTree(tree, node);
			} else if (node.getTrees().size() == 1) {
				return new Response(
						"Nodes cannot be without tree, so you can't delete this tree, delete all node in the tree is required before",
						false);
			} else {
				deleteNodeFromTree(node, treeId);
			}
		}
		treeService.deleteTree(treeId);
		return new Response("Success", true);
	}

	public Response getNodes() {
		return new Response(nodeService.getAllNodes());
	}

	public Response getNode(@RequestParam Long nodeId) {
		return new Response(nodeService.getNode(nodeId));
	}

	public Response deleteNode(@RequestParam Node node) {
		Set<TreeNodes> treeNodes = node.getTreeNodes();
		for (TreeNodes treeNode : treeNodes) {
			nodeService.removeLinks(node.getId(), treeNode.getTree().getId());
			nodeService.deleteNode(node.getId());
			for (Node nodes : nodeService.getAllNodes()) {
				if (nodes.isOrphan()) {
					nodeService.deleteNode(nodes.getId());
				}
			}
		}
		if (!Objects.isNull(nodeService.getNode(node.getId()))) {
			return new Response("The node has not been deleted, there is a problem", false);
		}
		return new Response("The node has been deleted", true);
	}

	public Response deleteNodeFromTree(@RequestParam Node node, @RequestParam long treeId) {
		Long nodeId = node.getId();
		Tree tree = treeService.getTree(treeId);
		nodeService.removeLinksInTree(node.getId(), tree.getId());
		treeService.removeNodeFromTree(tree, node);
		node = nodeService.getNode(nodeId);
		if (!Objects.isNull(node) && node.isOrphan()) {
			return deleteNode(node);
		}
		return new Response("The node has been removed", true);
	}

	public void updateNode(@RequestParam Long nodeId, @RequestParam Node node) {
		nodeService.updateNode(nodeId, node);
	}

	public Response addNode(@RequestParam Node node) {
		nodeService.addNode(node);
		node = nodeService.getNode(node.getId());
		if (Objects.isNull(node)) {
			return new Response("Fail to create node", false);
		}
		return new Response("Success", true);
	}

	public Response addTree(@RequestParam Tree tree) {
		treeService.addTree(tree);
		tree = treeService.getTreeByName(tree.getName());
		if (Objects.isNull(tree)) {
			return new Response("Fail to create user's Tree: step 2 failed", false);
		}
		return new Response("Success", true);
	}

	public Response addLinkedNode(@RequestParam Long treeId, @RequestParam long linkedNodeId,
			@RequestParam Node nodeToAdd, @RequestParam int privacy, @RequestParam String type,
			@RequestParam boolean alreadyInTree) {
		if (!Objects.isNull(nodeToAdd.getPrivacy())) {
			privacy = nodeToAdd.getPrivacy();
		}
		Node linkedNode = nodeService.getNode(linkedNodeId);
		if(Objects.isNull(linkedNode)){
			return new Response("The linked node doesn't exist", false);
		}
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

	public Response preProcessNodeInTreeCreation(Node node, long treeId, User userWhoWantsToCreate, Node relatedToNode,
			String relationType) {
		if (Objects.isNull(node) || !node.isPublic()) { // If the node want to be created in private ok
			return new Response(null, true);
		}
		Node existingNode = nodeService.getNodeByNameAndBirthInfo(node.getLastName(), node.getFirstName(),
				node.getDateOfBirth(), node.getCountryOfBirth(), node.getCityOfBirth());
		// If an existing Node has been found
		if (!Objects.isNull(existingNode) && existingNode.isPublic()) {
			// check if the node is already in the tree
			if (nodeService.doesNodeBelongToTree(existingNode.getId(), treeId)) {
				return new Response("Cannot add a node in your tree that is already in your tree", false);
			}
			// If you have not created this node (always at this point, could be an else)
			else if (!existingNode.getCreatedById().equals(userWhoWantsToCreate.getId())) {
				User creator = existingNode.getCreatedBy();
				// make the node temporary (invisible to user) and store it in db
				String resStr = "A node similar as the one that you want to create (" + node.getFullNameAndBirthInfo()
						+ ") as been found in the tree of another user. \nHere is the node. Is that the node you wanna create ?\n If so, click yes and a request to merge the other user tree will be sent. \nOtherwise click no, and change data in the Node or create it in private.";
				String temporaryCountryOfBirth = node.getCountryOfBirth() + Constants.TEMPORARY_STR_MAKER;
				node.getPersonInfo().setCountryOfBirth(temporaryCountryOfBirth);
				Response tmpNodeCreatRes = addNode(node);
				if (!tmpNodeCreatRes.getSuccess()) {
					return tmpNodeCreatRes;
				}
				node = nodeService.getNodeByNameAndBirthInfo(node.getLastName(), node.getFirstName(),
						node.getDateOfBirth(), temporaryCountryOfBirth, node.getCityOfBirth());
				// response Preparation
				LinkedHashMap<String, Object> resValue = new LinkedHashMap<String, Object>();
				resValue.put("specialSuccess", true);
				resValue.put("nodeToShow", existingNode);
				String requestYes = "/conversation/validation/newTreeMergeValidation?senderId="
						+ userWhoWantsToCreate.getId() + "&receiverId=" + creator.getId() + "&baseNodeId="
						+ existingNode.getId() + "&additionNodeId=" + node.getId() + "&relatedToNodeId="
						+ relatedToNode.getId() + "&relationType=" + relationType;
				resValue.put("requestYes", requestYes);
				return new Response(resValue, resStr, true);
			}
		}
		return new Response(null, true);
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

	private String firstVerifications(List<LinkedHashMap<String, String>> tree, Long userConnectedId) {
	    String returnStr = "One or more logical problem has been found in your tree, so it has not been saved :\n";
	    boolean thereIsAProb = false;
	    
	    for (LinkedHashMap<String, String> node : tree) {
	        String firstName = node.get("firstName");
	        String lastName = node.get("lastName");
	        String dateOfBirth = node.get("dateOfBirth");
	        Long nodeId = Misc.convertObjectToLong(node.get("id"));

	        if (!Misc.birthIsPossible(dateOfBirth, node.get("dateOfDeath"))) {
	            returnStr += "\t- The node of " + firstName + " " + lastName + "'s date of death is before his/her date of birth.\n";
	            thereIsAProb = true;
	        }
	        
	        if (nodeId > 0) {
	            int nodePrivacy = nodeService.getNode(nodeId).getPrivacy();
	            int elementPrivacy = (int) Misc.convertObjectToLong(node.get("privacy"));

	            if (elementPrivacy < nodePrivacy) {
	                returnStr += "\t- It is not allowed to make the node " + firstName + " " + lastName +
	                        " more private than it already is.\n";
	                thereIsAProb = true;
	            }
	        }

	        if (node != null) {
	            checkParentValidity(tree, returnStr, thereIsAProb, node, "parent1Id", "firstName", "lastName", "dateOfBirth", "dateOfDeath");
	            checkParentValidity(tree, returnStr, thereIsAProb, node, "parent2Id", "firstName", "lastName", "dateOfBirth", "dateOfDeath");
	            if(!Objects.isNull(node.get("partnerId")) && !node.get("partnerId").equals("null")) {
		            checkPartnerValidity(tree, returnStr, thereIsAProb, node, "partnerId", "firstName", "lastName", "dateOfBirth", "dateOfDeath");
	            }
	        }
	    }

	    return !thereIsAProb ? "OK" : returnStr;
	}
	
	@SuppressWarnings("null")
	private void checkParentValidity(List<LinkedHashMap<String, String>> tree, String returnStr, boolean thereIsAProb,
	                                  LinkedHashMap<String, String> node, String parentIdKey, String firstNameKey,
	                                  String lastNameKey, String dateOfBirthKey, String dateOfDeathKey) {
	    if (node.containsKey(parentIdKey) && !Objects.isNull(node.get(parentIdKey))) {
	        Long parentId = Misc.convertObjectToLong(node.get(parentIdKey));
	        LinkedHashMap<String, String> parent = findNodeWithId(tree, parentId);

	        if (Objects.isNull(parent)) {
	            parent.put("dateOfBirth", nodeService.getNode(parentId).getDateOfBirth().toString());
	            parent.put("dateOfDeath", null);
	        }

	        String parentFirstName = parent.get(firstNameKey);
	        String parentLastName = parent.get(lastNameKey);
	        String parentDateOfBirth = parent.get(dateOfBirthKey);

	        if (!Misc.parentIsOlder(parentDateOfBirth, node.get(dateOfBirthKey))) {
	            returnStr += "\t- The node " + parentFirstName + " " + parentLastName +
	                    " is too young to be " + node.get(firstNameKey) + " " + node.get(lastNameKey) + "'s parent.\n";
	            thereIsAProb = true;
	        }

	        if (!Misc.parentWasAlive(parent.get(dateOfDeathKey), node.get(dateOfBirthKey))) {
	            returnStr += "\t- The node " + parentFirstName + " " + parentLastName +
	                    " was dead at the date of the birth indicated in the node of " + node.get(firstNameKey) +
	                    " " + node.get(lastNameKey) + "\n";
	            thereIsAProb = true;
	        }
	    }
	}
	
	@SuppressWarnings("null")
	private void checkPartnerValidity(List<LinkedHashMap<String, String>> tree, String returnStr, boolean thereIsAProb,
            LinkedHashMap<String, String> node, String partnerIdKey, String firstNameKey,
            String lastNameKey, String dateOfBirthKey, String dateOfDeathKey) {
			if (node.containsKey(partnerIdKey) && !Objects.isNull(node.get(partnerIdKey))) {
				Long partnerId = Misc.convertObjectToLong(node.get(partnerIdKey));
				LinkedHashMap<String, String> partner = findNodeWithId(tree, partnerId);
		
				if (Objects.isNull(partner)) {
					partner.put("dateOfBirth", nodeService.getNode(partnerId).getDateOfBirth().toString());
					partner.put("dateOfDeath", null);
				}				
				String partnerFirstName = partner.get(firstNameKey);
				String partnerLastName = partner.get(lastNameKey);
				String partnerDateOfBirth = partner.get(dateOfBirthKey);
				String partnerDateOfDeath = partner.get(dateOfDeathKey);
				
				if (Misc.birthIsPossible(partnerDateOfBirth, partnerDateOfDeath) &&
					Misc.birthIsPossible(node.get(dateOfBirthKey), node.get(dateOfDeathKey)) &&
					!Misc.parentWasAlive(partnerDateOfDeath, node.get(dateOfBirthKey)) &&
					!Misc.parentWasAlive(node.get(dateOfDeathKey), partnerDateOfBirth)) {
					returnStr += "\t- The nodes " + firstNameKey + " " + lastNameKey + " and " +
							partnerFirstName + " " + partnerLastName + " have inconsistent birth and death dates.\n";
					thereIsAProb = true;
				}
			}
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
				if (Misc.convertObjectToLong(parts[i]) == oldId) {
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

	private void addParentRelations(Long id, Long TreeId, String[] relatedToNodes, LinkedHashMap<Long, Node> nodeToAdd,
		LinkedHashMap<Long, Node> existingNodes, LinkedHashMap<Long, String> unknownRelation,
		LinkedHashMap<Long, String> updates) {
		Long newIdInDb = null;

		if (Misc.convertObjectToLong(relatedToNodes[1]) < 0) {
			addLinkedNode(TreeId, id, nodeToAdd.get(Misc.convertObjectToLong(relatedToNodes[1])),
					nodeToAdd.get(Misc.convertObjectToLong(relatedToNodes[1])).getPrivacy(), "PARENT", false);
			nodeToAdd.remove(Misc.convertObjectToLong(relatedToNodes[1]));
			existingNodes.put(nodeService.getNode(id).getParent1Id(), nodeService.getNode(id).getParent1());
			unknownRelation = replaceId(unknownRelation, Misc.convertObjectToLong(relatedToNodes[1]),
					nodeService.getNode(id).getParent1Id());
			updates = replaceIdInUpdates(updates, Misc.convertObjectToLong(relatedToNodes[1]),
					nodeService.getNode(id).getParent1Id());
			newIdInDb = nodeService.getNode(id).getParent1Id();
		}

		if (Misc.convertObjectToLong(relatedToNodes[3]) < 0) {
			if (Objects.isNull(nodeToAdd.get(Misc.convertObjectToLong(relatedToNodes[3])))
					&& !Objects.isNull(newIdInDb)) {
				addLinkedNode(TreeId, id, existingNodes.get(newIdInDb), existingNodes.get(newIdInDb).getPrivacy(),
						"Parent", true);
			} else {
				addLinkedNode(TreeId, id, nodeToAdd.get(Misc.convertObjectToLong(relatedToNodes[3])),
						nodeToAdd.get(Misc.convertObjectToLong(relatedToNodes[3])).getPrivacy(), "PARENT", false);
				nodeToAdd.remove(Misc.convertObjectToLong(relatedToNodes[3]));
				existingNodes.put(nodeService.getNode(id).getParent2Id(), nodeService.getNode(id).getParent2());
				unknownRelation = replaceId(unknownRelation, Misc.convertObjectToLong(relatedToNodes[3]),
						nodeService.getNode(id).getParent2Id());
				updates = replaceIdInUpdates(updates, Misc.convertObjectToLong(relatedToNodes[3]),
						nodeService.getNode(id).getParent2Id());
			}
		}
	}

	private void addPartnerRelations(Long id, Long TreeId, String[] relatedToNodes, LinkedHashMap<Long, Node> nodeToAdd,
		LinkedHashMap<Long, Node> existingNodes, LinkedHashMap<Long, String> unknownRelation,
		LinkedHashMap<Long, String> updates) {
		if (Misc.convertObjectToLong(relatedToNodes[5]) != 0) {
			if (Objects.isNull(nodeToAdd.get(id))) {
				return;
			} else {
				addLinkedNode(TreeId, Misc.convertObjectToLong(relatedToNodes[5]), nodeToAdd.get(id),
						nodeToAdd.get(id).getPrivacy(), "Partner", false);
				nodeToAdd.remove(id);
				existingNodes.put(nodeService.getNode(Misc.convertObjectToLong(relatedToNodes[5])).getPartnerId(),
						nodeService.getNode(Misc.convertObjectToLong(relatedToNodes[5])).getPartner());
				unknownRelation = replaceId(unknownRelation, id,
						nodeService.getNode(Misc.convertObjectToLong(relatedToNodes[5])).getPartnerId());
				updates = replaceIdInUpdates(updates, id,
						nodeService.getNode(Misc.convertObjectToLong(relatedToNodes[5])).getPartnerId());
			}
		}
	}

	private void addChildRelations(Long id, Long TreeId, String[] relatedToNodes, LinkedHashMap<Long, Node> nodeToAdd,
		LinkedHashMap<Long, Node> existingNodes, LinkedHashMap<Long, String> unknownRelation,
		LinkedHashMap<Long, String> updates) {
		Long idInDb = null;
		int privacy = nodeToAdd.get(id).getPrivacy();

		if (Misc.convertObjectToLong(relatedToNodes[1]) > 0) {
			addLinkedNode(TreeId, Misc.convertObjectToLong(relatedToNodes[1]), nodeToAdd.get(id), privacy, "CHILD",
					false);
			Node nodeInDb = nodeService.getNodeByNameAndBirthInfo(nodeToAdd.get(id).getLastName(),
					nodeToAdd.get(id).getFirstName(), nodeToAdd.get(id).getDateOfBirth(),
					nodeToAdd.get(id).getCountryOfBirth(), nodeToAdd.get(id).getCityOfBirth());
			existingNodes.put(nodeInDb.getId(), nodeInDb);
			nodeToAdd.remove(id);
			unknownRelation = replaceId(unknownRelation, id, nodeInDb.getId());
			idInDb = nodeInDb.getId();
		}

		if (Misc.convertObjectToLong(relatedToNodes[3]) > 0) {
			if (Objects.isNull(nodeToAdd.get(Misc.convertObjectToLong(id))) && !Objects.isNull(idInDb)) {
				addLinkedNode(TreeId, Misc.convertObjectToLong(relatedToNodes[3]), existingNodes.get(idInDb),
						existingNodes.get(idInDb).getPrivacy(), "Child", true);
			} else {
				addLinkedNode(TreeId, Misc.convertObjectToLong(relatedToNodes[3]), nodeToAdd.get(id), privacy, "CHILD",
						false);
				Node nodeInDb = nodeService.getNodeByNameAndBirthInfo(nodeToAdd.get(id).getLastName(),
						nodeToAdd.get(id).getFirstName(), nodeToAdd.get(id).getDateOfBirth(),
						nodeToAdd.get(id).getCountryOfBirth(), nodeToAdd.get(id).getCityOfBirth());
				existingNodes.put(nodeInDb.getId(), nodeInDb);
				nodeToAdd.remove(id);
				unknownRelation = replaceId(unknownRelation, id, nodeInDb.getId());
			}
		}
	}

	private Response sendPreProcessNodeInTreeCreation(String key, Long id, Long treeId, User user,
			String[] relatedToNodes, Map<Long, Node> nodesToAdd, Map<Long, Node> existingNodes,
			Map<Long, String> unknownRelation) {
		Response BaFRes = null;
		switch (key.toUpperCase()) {
		case "PARENT":
			if (Misc.convertObjectToLong(relatedToNodes[1]) < 0) {
				BaFRes = preProcessNodeInTreeCreation(nodesToAdd.get(Misc.convertObjectToLong(relatedToNodes[1])),
						treeId, user, nodeService.getNode(id), key);
			} else if (Misc.convertObjectToLong(relatedToNodes[3]) < 0) {
				BaFRes = preProcessNodeInTreeCreation(nodesToAdd.get(Misc.convertObjectToLong(relatedToNodes[3])),
						treeId, user, nodeService.getNode(id), key);
			}
			break;
		case "PARTNER":
			BaFRes = preProcessNodeInTreeCreation(nodesToAdd.get(id), treeId, user,
					nodeService.getNode(Misc.convertObjectToLong(relatedToNodes[5])), key);
			break;
		case "CHILD":
			if (Misc.convertObjectToLong(relatedToNodes[1]) < 0) {
				BaFRes = preProcessNodeInTreeCreation(nodesToAdd.get(id), treeId, user,
						nodeService.getNode(Misc.convertObjectToLong(relatedToNodes[1])), key);
			} else if (Misc.convertObjectToLong(relatedToNodes[3]) < 0) {
				BaFRes = preProcessNodeInTreeCreation(nodesToAdd.get(id), treeId, user,
						nodeService.getNode(Misc.convertObjectToLong(relatedToNodes[3])), key);
			}
			break;
		default:
			BaFRes = new Response("Incorrect Parameter update " + key, false);
		}
		return BaFRes;
	}
}
