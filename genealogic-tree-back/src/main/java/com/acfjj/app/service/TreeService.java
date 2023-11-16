package com.acfjj.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.repository.NodeRepository;
import com.acfjj.app.repository.TreeNodesRepository;
import com.acfjj.app.repository.TreeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TreeService {

    @Autowired
    TreeRepository treeRepository;

    @Autowired
    NodeRepository nodeRepository;
    
    @Autowired
    TreeNodesRepository treeNodesRepository;

    public List<Tree> getAllTrees() {
        List<Tree> trees = new ArrayList<>();
        treeRepository.findAll().forEach(trees::add);
        return trees;
    }

    public Tree getTree(long id) {
        return treeRepository.findById(id).orElse(null);
    }

     public void addTree(Tree tree) {
        if(isNameTaken(tree.getName())){
        	tree.setName(tree.getName()+"2");
    	}
        for (TreeNodes treeNode : tree.getNodes()) {
            if(treeNode != null)
        	treeNodesRepository.save(treeNode);
        }    
        treeRepository.save(tree);
    }

    public void deleteTree(long id) {
    	Tree tree = getTree(id);
        Set<TreeNodes> treeNodes = tree.getTreeNodes(); 
        for (TreeNodes treeNode : treeNodes) {
        	  removeNodeFromTree(tree, treeNode.getNode());
//            nodeService.deleteNode(treeNode.getNode().getId());
        }
        treeRepository.deleteById(id);
        return;
    }

    public void updateTree(long id, Tree tree) {
        Tree existingTree = getTree(id);
        if (existingTree != null) {
            Set<TreeNodes> treeNodes = tree.getTreeNodes();
            for (TreeNodes treeNode : treeNodes) {
            	treeNodesRepository.save(treeNode);
            }
            treeRepository.save(tree);
        }
        return;
    }

    public boolean isNameTaken(String name) { 
    	return (getTreeByName(name) == null) ? false : true;
    }
    
    public Tree getTreeByName(String name) {
        return treeRepository.findByName(name);
    }

    public List<Tree> getPublicTrees() {
        return treeRepository.findByPrivacy(1);
    }
    
    public void deleteNodeFromTree(Long nodeId, Long treeId) {
        Tree tree = getTree(treeId);

    	Tree tree = getTree(treeId);
        if (tree != null) {
        	 Set<TreeNodes> treeNodes = treeNodesRepository.findAll();

        	 for (TreeNodes treeNode : treeNodes) {
        		 if(treeNode.getNode().getId().equals(nodeId)) {
        			 if(treeNode.getTree() != null && treeNode.getTree().getId().equals(treeId)) {
        				 removeNodeFromTree(tree, treeNode.getNode());
        				 tree = getTree(treeId);
        			 }
        			 treeNodesRepository.delete(treeNode);

        		 }
        	 }
             nodeRepository.deleteById(nodeId);

             treeRepository.save(tree);
        }
    }


    public void addNodeToTree(Tree tree, Node node, int privacy, int depth) {
        if (tree != null && node != null) {

            Set<TreeNodes> treeNodes = tree.getNodes();
            if (treeNodes.contains(null) || treeNodes == null ) {
                treeNodes = new HashSet<>();
            }
            boolean associationExists = treeNodes.stream().anyMatch(treeNode -> treeNode.getNode().equals(node));
            if (!associationExists) {
                TreeNodes treeNode = new TreeNodes(tree, node, privacy, depth);
                treeNodesRepository.save(treeNode);
                treeNodes.add(treeNode);
                node.setTreeNodes(treeNodes);
                treeRepository.save(tree);
            }
        }
    }

    public void removeNodeFromTree(Tree tree, Node node) {
    	Long treeId = tree.getId();
        if (tree != null && node != null) {
            Set<TreeNodes> treeNodes = tree.getTreeNodes();
            if (treeNodes != null) {
                TreeNodes nodeToRemove = treeNodes.stream()
                        .filter(treeNode -> treeNode.getNode().getId().equals(node.getId()))
                        .findFirst()
                        .orElse(null);

                if (nodeToRemove != null) {
                	treeNodes.remove(nodeToRemove);
                    node.setTreeNodes(treeNodes);
                    tree.setTreeNodes(treeNodes);

                    nodeRepository.save(node);
                    nodeToRemove.setTree(null);
                    treeNodesRepository.save(nodeToRemove);
                    treeRepository.save(tree);
                }
            }
        }
    }
}
