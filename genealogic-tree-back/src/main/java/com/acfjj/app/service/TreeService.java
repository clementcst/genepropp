package com.acfjj.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.repository.NodeRepository;
import com.acfjj.app.repository.TreeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TreeService {

    @Autowired
    TreeRepository treeRepository;

    @Autowired
    NodeService nodeService;
    @Autowired
    NodeRepository nodeRepository;

    public List<Tree> getAllTrees() {
        List<Tree> trees = new ArrayList<>();
        treeRepository.findAll().forEach(trees::add);
        return trees;
    }

    public Tree getTree(long id) {
        return treeRepository.findById(id).orElse(null);
    }

    public void addTree(Tree tree) {
        treeRepository.save(tree);
        return;
    }

    public void deleteTree(long id) {
        List<TreeNodes> treeNodes = nodeService.getTreeNodesByTreeId(id); //supprimer service (autorwired aussi) + appel juste de la list de node du tree + test si juste remove node de la list = remove de treenode
        for (TreeNodes treeNode : treeNodes) {
            nodeService.deleteNode(treeNode.getNode().getId());
        }
        treeRepository.deleteById(id);
        return;
    }

    public void updateTree(long id, Tree tree) {
        Tree existingTree = getTree(id);
        if (existingTree != null && tree.getId() == id) {
            treeRepository.save(tree);
            //update nodes aussi 
        }
        return;
    }

    public List<Tree> getTreesByName(String name) {
        return treeRepository.findByName(name);
    }

    public List<Tree> getPublicTrees() {
        return treeRepository.findByPrivacy(1);
    }

    //à deplacer dans NodeService Selon moi
    //à finir 
    public boolean doesNodeBelongToTree(Long nodeId, Long treeId) { 
        Tree tree = getTree(treeId);
        if (tree == null) {
            return false;
        }

        Set<TreeNodes> treeNodes = tree.getNodes();
        for (TreeNodes treeNode : treeNodes) {
            if (treeNode.getNode().getId().equals(nodeId)) {
                return true;
            }
        }
        return false;
    }
    
    //à revoir
    public void deleteNodeFromTree(Long nodeId, Long treeId) {
    	Tree tree = treeRepository.findById(treeId).orElse(null);
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
