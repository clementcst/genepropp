package com.acfjj.app.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.acfjj.app.model.Node;
import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.model.User;
import com.acfjj.app.utils.Misc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Scope("singleton")
public class NodeService extends AbstractService {

	public List<Node> getAllNodes() {
        List<Node> nodes = new ArrayList<>();
        nodeRepository.findAll().forEach(nodes::add);
        return nodes;
    }

    public Node getNode(Long id) {
        return nodeRepository.findById(id).orElse(null);
    }

    public void addNode(Node node) {
    	personInfoRepository.save(node.getPersonInfo());
        nodeRepository.save(node);
        for (TreeNodes treeNode : node.getTreeNodes()) {
    		if(treeNode != null)
            treeNodesRepository.save(treeNode);
        }
        return;
    }
    
    public void addParent(Node node, Node parentNode) {
    	 if (node != null && parentNode != null) {
    	        if (node.getParent1() == null) {
    	            node.setParent1(parentNode);
    	        } else if (node.getParent2() == null) {
    	            node.setParent2(parentNode);
    	        }
    	        updateNode(node.getId(), node);
    	    }
    	 return;
    }
    
    public void updateParent(Long nodeId, Long parentNodeId, int whichParent) {
    	if(nodeId == parentNodeId) 
    		return;
        Node node = getNode(nodeId);
        Node newParent = getNode(parentNodeId);
        if (node != null && whichParent == 1) {
            node.setParent1(newParent);
            updateNode(nodeId, node);
        } else if (node != null && whichParent == 2) {
            node.setParent2(newParent);
            updateNode(nodeId, node);
        }
        return;
    }


   public void deleteNode(Long id) {
        Node node = getNode(id);
        if (node != null) {        	
            if (node.isOrphan()) {
                PersonInfo personInfo = node.getPersonInfo();
                if (personInfo != null) {
                	nodeRepository.deleteById(id);
                    personInfoRepository.deleteById(personInfo.getId());
                    return;
                }
            }
            nodeRepository.deleteById(id);
        }
        return;
    }
   
   public void removeLinks(Long nodeId, Long treeId) {
       Node node = getNode(nodeId);
       if (node != null) {        	
           if (node.getParent1() != null) {
        	   node.setParent1(null);
        	   updateNode(node.getId(),node);
           }
           if (node.getParent2() != null) {
        	   node.setParent2(null);
        	   updateNode(node.getId(),node);        	   
           }
           if (node.getPartner() != null) {
        	   Node partner = node.getPartner();
        	   node.setPartner(null);
        	   updateNode(node.getId(),node);  
        	   partner.setPartner(null);
        	   updateNode(partner.getId(),partner); 
           }
           if (node.getSiblings() != null) {
        	   for(Node siblings : node.getSiblings()) {
        		   siblings.removeSiblings(node);
        		   updateNode(siblings.getId(),siblings); 
        	   }
        	   node.setSiblings(null);
        	   updateNode(node.getId(),node);  
           }
           if (node.getExPartners() != null) {
        	   for(Node exPartners : node.getExPartners()) {
        		   exPartners.removeExPartners(node);
        		   updateNode(exPartners.getId(),exPartners); 
        	   }
        	   node.setExPartners(null);
        	   updateNode(node.getId(),node);  
           }
       }
       if(node.getHasChild()) {
    	   Set<TreeNodes> treeNodes = treeNodesRepository.findAll();
           for(TreeNodes treeNode : treeNodes) {
        	   if(treeNode.getTree().getId() == treeId) {
        		   if(treeNode.getNode().getParent1Id() == node.getId()) {
        			   treeNode.getNode().setParent1(null);
        			   updateNode(treeNode.getNode().getId(), treeNode.getNode());
        		   }
        		   if(treeNode.getNode().getParent2Id() == node.getId()) {
        			   treeNode.getNode().setParent2(null);
        			   updateNode(treeNode.getNode().getId(), treeNode.getNode());
        		   }
        	   }
           }
       }  
       node.setHasChild(false);
       updateNode(node.getId(),node);  
   }

   public void removeLinksInTree(Long nodeId, Long treeId) {
	    Tree tree = treeRepository.findById(treeId).orElse(null);

	    if (!Objects.isNull(tree)) {
	        Node node = getNode(nodeId);

	        if (!Objects.isNull(node)) {
	            Set<TreeNodes> treeNodes = tree.getTreeNodes();

	            removeParentFromNode(node, node.getParent1(), treeNodes);
	            removeParentFromNode(node, node.getParent2(), treeNodes);

	            if (!Objects.isNull(node.getPartner())) {
	                Node partner = node.getPartner();
	                node.setPartner(null);
	                updateNode(node.getId(), node);
	                partner.setPartner(null);
	                updateNode(partner.getId(), partner);
	            }

	            if (node.getHasChild()) {
	                for (TreeNodes treeNode : treeNodes) {
	                    if (treeNode.getNode().getParent1Id() == node.getId()) {
	                        treeNode.getNode().setParent1(null);
	                        updateNode(treeNode.getNode().getId(), treeNode.getNode());
	                    }
	                    if (treeNode.getNode().getParent2Id() == node.getId()) {
	                        treeNode.getNode().setParent2(null);
	                        updateNode(treeNode.getNode().getId(), treeNode.getNode());
	                    }
	                }
	                node.setHasChild(false);
	            }

	            updateNode(node.getId(), node);
	        }
	    }
	}

	private void removeParentFromNode(Node node, Node parent, Set<TreeNodes> treeNodes) {
	    if (!Objects.isNull(parent) && parent.getTrees().contains(node.getTree())) {
	        Boolean hasChild = false;
	        for (TreeNodes treeNode : treeNodes) {
	            if (treeNode.getNode().getParent1Id() == parent.getId() ||
	                treeNode.getNode().getParent2Id() == parent.getId()) {
	                hasChild = true;
	            }
	        }
	        parent.setHasChild(hasChild);
	        updateNode(parent.getId(), parent);
	        if (parent.equals(node.getParent1())) {
	            node.setParent1(null);
	        } else if (parent.equals(node.getParent2())) {
	            node.setParent2(null);
	        }
	        updateNode(node.getId(), node);
	    }
	}

   
    public void updateNode(Long id, Node node) {
        if (getNode(id) != null && node.getId() == id) {
            nodeRepository.save(node);
            personInfoRepository.save(node.getPersonInfo());
        }
    }
    
    public void updateWithoutRelation(Long id, Node node) {
    	Node existingNode = getNode(id);
        if (getNode(id) != null && node.getId() == id) {
        	PersonInfo person = existingNode.getPersonInfo();
           	node.getPersonInfo().setId(person.getId());
        	existingNode.setPersonInfo(node.getPersonInfo());
        	existingNode.setPrivacy(node.getPrivacy());
        	existingNode.setDateOfDeath(node.getDateOfDeath()); 
            personInfoRepository.save(existingNode.getPersonInfo());
            nodeRepository.save(existingNode);
        }
    }

    public List<Node> getParentsOfNode(Long id) {
        Node node = getNode(id);
        List<Node> parents = new ArrayList<>();
        parents.add(node.getParent1());
        parents.add(node.getParent2());
        return parents;
    }
    
    
    public boolean doesNodeBelongToTree(Long nodeId, Long treeId) {
        Node node = getNode(nodeId);
        if (node == null || treeId == null) {
            return false;
        }

        Set<TreeNodes> treeNodes = node.getTreeNodes();
        for (TreeNodes treeNode : treeNodes) {
            if (treeNode.getTree().getId().equals(treeId)) {
                return true;
            }
        }
        return false;
    }
    
    public Integer getGenerationGap(Node node1, Node node2) {
    	List<Tree> trees1 = node1.getTrees();
    	List<Tree> trees2 = node2.getTrees();
    	trees1.retainAll(trees2);
    	List<Integer> diffList = new ArrayList<>();
    	for(Tree tree : trees1) {
    		diffList.add(tree.getTreeNodesByNode(node1).getDepth() - tree.getTreeNodesByNode(node2).getDepth());
    	}
    	return Misc.findMaxFrequency(diffList);
    }
    
    public Node getNodeByNameAndBirthInfo(String lastName, String firstName, LocalDate dateOfBirth, String countryOfBirth, String cityofBirth) {    	
    	Node nodeFound = null;
		List<PersonInfo> personInfoFounds = personInfoRepository
				.findByLastNameAndFirstNameAndDateOfBirthAndCountryOfBirthAndCityOfBirth(lastName, firstName,
						dateOfBirth, countryOfBirth, cityofBirth);
		if (!personInfoFounds.isEmpty()) {
			PersonInfo personInfoFound = personInfoFounds.get(0);
			nodeFound = nodeRepository.findByPersonInfo(personInfoFound);
		}
		return nodeFound;
	}

    public Node getPublicNodeByNameAndBirthInfo(String lastName, String firstName, LocalDate dateOfBirth, String countryOfBirth, String cityofBirth) {
		Node nodeFound = getNodeByNameAndBirthInfo(lastName, firstName, dateOfBirth, countryOfBirth, cityofBirth);
		if(Objects.isNull(nodeFound)) {
			return null;
		}
		return nodeFound.isPublic() ? nodeFound : null;
	}
}
