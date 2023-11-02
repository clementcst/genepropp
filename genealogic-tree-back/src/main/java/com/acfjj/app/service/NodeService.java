package com.acfjj.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acfjj.app.model.Node;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.repository.PersonInfoRepository;
import com.acfjj.app.repository.NodeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class NodeService {

    @Autowired
    NodeRepository nodeRepository;
    @Autowired
	PersonInfoRepository personInfoRepository;

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
            nodeRepository.deleteById(id);
            if (node.isOrphan()) {
            	//personInfoRepository.save(Node.getPersonInfo());
                // Supprimer la PersonInfo associée s'il est orphelin
            }
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

    public List<TreeNodes> getTreeNodesByTreeId(Long treeId) { //à revoir 
        List<TreeNodes> treeNodes = new ArrayList<>();
        for (Node node : nodeRepository.findAll()) {
            Set<TreeNodes> nodes = node.getTrees();
            for (TreeNodes treeNode : nodes) {
                if (treeNode.getTree().getId().equals(treeId)) {
                    treeNodes.add(treeNode);
                }
            }
        }
        return treeNodes;
    }
}
