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
    		//changer le nom
    	}
        treeRepository.save(tree);
        return;
    }

    public void deleteTree(long id) {
    	Tree tree = getTree(id);
        Set<TreeNodes> treeNodes = tree.getNodes(); // test si juste remove node de la list = remove de treenode
        for (TreeNodes treeNode : treeNodes) {
        	tree.removeNode(treeNode.getNode());
//            nodeService.deleteNode(treeNode.getNode().getId());
        }
        treeRepository.deleteById(id);
        return;
    }

    public void updateTree(long id, Tree tree) {
        Tree existingTree = getTree(id);
        if (existingTree != null && tree.getId() == id) {
            Set<TreeNodes> treeNodes = tree.getNodes();
            for (TreeNodes treeNode : treeNodes) {
            	treeNodesRepository.save(treeNode);
            }
            treeRepository.save(tree);
        }
        return;
    }

    public Tree getTreeByName(String name) {
        return treeRepository.findByName(name);
    }

    public boolean isNameTaken(String name) { 
    	return (getTreeByName(name) == null) ? false : true;
    }

    public List<Tree> getPublicTrees() {
        return treeRepository.findByPrivacy(1);
    }
    
    public void deleteNodeFromTree(Long nodeId, Long treeId) {
    	Tree tree = getTree(treeId);
        if (tree != null) {
        	Set<TreeNodes> treeNodes = tree.getNodes();
            for (TreeNodes treeNode : treeNodes) {
                if (treeNode.getNode().getId().equals(nodeId)) {
                    treeNodes.remove(treeNode);
                    treeRepository.save(tree);
                    nodeRepository.deleteById(nodeId);
                    return;
                }
            }
        }
        return;
    }

}
