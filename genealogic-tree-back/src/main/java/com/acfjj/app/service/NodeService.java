package com.acfjj.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acfjj.app.model.Node;
import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.model.User;
import com.acfjj.app.repository.PersonInfoRepository;
import com.acfjj.app.repository.TreeNodesRepository;
import com.acfjj.app.repository.TreeRepository;
import com.acfjj.app.repository.NodeRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class NodeService {

    @Autowired
    NodeRepository nodeRepository;
    @Autowired
    PersonInfoRepository personInfoRepository;
    @Autowired
    TreeRepository treeRepository;
    @Autowired
    TreeNodesRepository treeNodesRepository;

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
    	for (TreeNodes treeNode : node.getTreeNodes()) {
    		if(treeNode != null)
            treeNodesRepository.save(treeNode);
        }
        nodeRepository.save(node);
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
    	//regarder si le créateur est celui qui veux delete
        Node node = getNode(id);
        PersonInfo person = node.getPersonInfo();
//        User user = userService.getUserByNameAndBirthInfo(person.getLastName(), person.getFirstName(), person.getDateOfBirth(), person.getCountryOfBirth(), person.getCityOfBirth());
        if(node.getPersonInfo() != null)
        if (node != null) {
        	List<Node> nodes = getAllNodes();
        	for(Node parcoursNode : nodes) {
        		if(parcoursNode.getId() != id) {
        			if(parcoursNode.getParent1() != null && parcoursNode.getParent1().getId() == id) {
            			parcoursNode.setParent1(null);
            		}
    	        	if(parcoursNode.getParent2() != null && parcoursNode.getParent2().getId() == id) {
    	    			parcoursNode.setParent2(null);
    	    		}
    	        	if(parcoursNode.getPartner() != null && parcoursNode.getPartner().getId() == id) {
            			parcoursNode.setPartner(null);
            		}
    	        	for(Node exPartners : parcoursNode.getExPartners()) {
    	        		if(exPartners.getId() == id) {
    	        			parcoursNode.getExPartners().remove(exPartners);
    	        		}
    	        	}
    	        	for(Node Siblings : parcoursNode.getSiblings()) {
    	        		if(Siblings.getId() == id) {
    	        			parcoursNode.getSiblings().remove(Siblings);
    	        		}
    	        	}
            		updateNode(parcoursNode.getId(), parcoursNode);
		}
	}
            if (node.isOrphan()) {
                PersonInfo personInfo = node.getPersonInfo();
                if (personInfo != null) {
                    personInfoRepository.deleteById(personInfo.getId());
                }
            }
            nodeRepository.deleteById(id);
        }
        return;
    }

    public void updateNode(Long id, Node node) {
        if (getNode(id) != null && node.getId() == id) {
            nodeRepository.save(node);
            personInfoRepository.save(node.getPersonInfo());
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
        Tree tree = treeRepository.findById(treeId).orElse(null);

        if (node == null || tree == null) {
            return false;
        }

        Set<TreeNodes> treeNodes = node.getTreeNodes();
        for (TreeNodes treeNode : treeNodes) {
            if (treeNode.getTree().equals(tree)) {
                return true;
            }
        }
        return false;
    }
    
    public Node getNodeByNameAndBirthInfo(String lastName, String firstName, LocalDate dateOfBirth, String countryOfBirth, String cityofBirth) {
		Node nodeFound = null;
		PersonInfo personInfoFound = personInfoRepository.findByLastNameAndFirstNameAndDateOfBirthAndCountryOfBirthAndCityOfBirth(lastName, firstName, dateOfBirth, countryOfBirth, cityofBirth); 
		if(!Objects.isNull(personInfoFound)) {
			nodeFound = nodeRepository.findByPersonInfo(personInfoFound);
		}
		return nodeFound;
	}
}
