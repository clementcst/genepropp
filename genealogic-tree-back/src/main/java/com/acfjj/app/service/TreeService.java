package com.acfjj.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acfjj.app.model.Node;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.repository.NodeRepository;
import com.acfjj.app.repository.PersonInfoRepository;
import com.acfjj.app.repository.TreeNodesRepository;
import com.acfjj.app.repository.TreeRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class TreeService {

	@Autowired
	TreeRepository treeRepository;

	@Autowired
	NodeRepository nodeRepository;

	@Autowired
	TreeNodesRepository treeNodesRepository;

	@Autowired
	PersonInfoRepository personInfoRepository;

	public List<Tree> getAllTrees() {
		List<Tree> trees = new ArrayList<>();
		treeRepository.findAll().forEach(trees::add);
		return trees;
	}

	public Tree getTree(long id) {
		return treeRepository.findById(id).orElse(null);
	}

	public void addTree(Tree tree) {
		for (TreeNodes treeNode : tree.getTreeNodes()) {
			if (treeNode != null)
				treeNodesRepository.save(treeNode);
		}
		treeRepository.save(tree);
	}

	public String getUniqueName(Tree tree) {
		String treeName = tree.getName();
		Integer nameInt = 1;
		while (isNameTaken(tree.getName())) {
			nameInt++;
			tree.setName(treeName + nameInt.toString());
		}
		return tree.getName();
	}

	public void deleteTree(long id) {
		Tree tree = getTree(id);
		Set<TreeNodes> treeNodes = tree.getTreeNodes();
		for (TreeNodes treeNode : treeNodes) {
			removeNodeFromTree(tree, treeNode.getNode());
//            nodeService.deleteNode(treeNode.getNode().getId());
			// supprimer toutes les nodes qui n'ont pas de user ?
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

//    public void deleteNodeFromTree(Long nodeId, Long treeId) {
//    les vérifs sont à faire ici
//    	//sécurité de user
//        Tree tree = getTree(treeId);
//        if (tree != null) {
//        	 Set<TreeNodes> treeNodes = tree.getTreeNodes();
//
//        	 for (TreeNodes treeNode : treeNodes) {
//        		 if(treeNode.getNode().getId().equals(nodeId)) {
//        			 if(treeNode.getTree() != null && treeNode.getTree().getId().equals(treeId)) {
//        				 removeNodeFromTree(tree, treeNode.getNode());
//        				 tree = getTree(treeId);
//        			 }
//        			 treeNodesRepository.delete(treeNode);
//
//        		 }
//        	 }
//             nodeRepository.deleteById(nodeId);
//
//             treeRepository.save(tree);
//        } 
//    }

	public void addNodeToTree(Tree tree, Node node, int privacy, int depth) {
		if (tree != null && node != null) {
			Set<TreeNodes> treeNodes = tree.getTreeNodes();
			if (treeNodes == null || treeNodes.size()== 1) {
				treeNodes = new HashSet<>();
			}
			boolean associationExists = treeNodes.stream().anyMatch(treeNode -> !Objects.isNull(treeNode) && treeNode.getNode().equals(node));
			if (!associationExists) {
				personInfoRepository.save(node.getPersonInfo());
				nodeRepository.save(node);
				TreeNodes treeNode = new TreeNodes(tree, node, privacy, depth);
				treeNodesRepository.save(treeNode);
				tree.addTreeNodes(treeNode);
				node.addTreeNodes(treeNode);
				nodeRepository.save(node);
				treeRepository.save(tree);
			}
			return;
		}
	}

	public void addParentToNodeInTree(Long treeId, Node node, Node parent, int privacy) {
		Tree tree = getTree(treeId);
		int depth = 0;
		for (TreeNodes treeNode : node.getTreeNodes()) {
			if (treeNode.getTree().getId() == treeId)
				depth = treeNode.getDepth();
		}
		System.out.println();
		addNodeToTree(tree, parent, privacy, depth + 1);
		if (node.getParent1() == null || node.getParent1Id().equals(parent.getId())) {
			node.setParent1(parent);
		} else if (node.getParent2() == null || node.getParent2Id().equals(parent.getId())) {
			node.setParent2(parent);
		} else {
			return;
		}
		nodeRepository.save(node);
	}
	
	public void addChildToNodeInTree(Long treeId, Node parent, Node child, int privacy) {
		Tree tree = getTree(treeId);
		int depth = 0;
		for (TreeNodes treeNode : parent.getTreeNodes()) {
			if (treeNode.getTree().getId() == treeId)
				depth = treeNode.getDepth();
		}
		addNodeToTree(tree, child, privacy, depth - 1);
		if (Objects.isNull(child.getParent1()) || child.getParent1Id().equals(parent.getId())) {
			child.setParent1(parent);
		} else if (Objects.isNull(child.getParent2()) || child.getParent2Id().equals(parent.getId())) {
			child.setParent2(parent);
		} else {
			return;
		}
		nodeRepository.save(child);
	}

	public void addPartnerToNodeInTree(Long treeId, Node node, Node partner, int privacy) {
		Tree tree = getTree(treeId);
		int depth = 0;
		for (TreeNodes treeNode : node.getTreeNodes()) {
			if (treeNode.getTree().getId() == treeId)
				depth = treeNode.getDepth();
		}
		partner.setPartner(node);
		addNodeToTree(tree, partner, privacy, depth);
		node.setPartner(partner);
		nodeRepository.save(node);
	}

	public void addSiblingsToTree(Long treeId, Node node, Node sibling, int privacy) {
		Tree tree = getTree(treeId);
		int depth = 0;
		for (TreeNodes treeNode : node.getTreeNodes()) {
			if (treeNode.getTree().getId() == treeId)
				depth = treeNode.getDepth();
		}
		for (Node nodeSibling : node.getSiblings()) {
			sibling.addSiblings(nodeSibling);
		}

		sibling.addSiblings(node);
		addNodeToTree(tree, sibling, privacy, depth);
		for (Node siblingSiblings : sibling.getSiblings()) {
			for (Node nodeSibling : node.getSiblings()) {
				if (!siblingSiblings.getId().equals(nodeSibling.getId()))
					node.addSiblings(sibling);
				nodeSibling.addSiblings(sibling);
				nodeRepository.save(nodeSibling);
			}
		}
		for (Node nodeSiblings : sibling.getSiblings()) {
			for (Node allSiblings : nodeSiblings.getSiblings()) {
				allSiblings.addSiblings(sibling);
				nodeRepository.save(allSiblings);
			}
		}

		node.addSiblings(sibling);
		nodeRepository.save(node);
	}

	public void addExPartnerToNodeInTree(Long treeId, Node node, Node exPartner, int privacy) {
		Tree tree = getTree(treeId);
		int depth = 0;
		for (TreeNodes treeNode : node.getTreeNodes()) {
			if (treeNode.getTree().getId() == treeId)
				depth = treeNode.getDepth();
		}
		exPartner.addExPartners(node);
		addNodeToTree(tree, exPartner, privacy, depth);
		node.addExPartners(exPartner);
		nodeRepository.save(node);
	}

	// Faire les removes links

	public void removeNodeFromTree(Tree tree, Node node) {
		Long treeId = tree.getId();
		if (tree != null && node != null) {
			Set<TreeNodes> treeNodes = tree.getTreeNodes();
			if (treeNodes != null) {
				TreeNodes nodeToRemove = treeNodes.stream()
						.filter(treeNode -> treeNode.getNode().getId().equals(node.getId())).findFirst().orElse(null);

				if (nodeToRemove != null) {
					treeNodes.remove(nodeToRemove);
					node.setTreeNodes(treeNodes);
					tree.setTreeNodes(treeNodes);

					nodeRepository.save(node);
					nodeToRemove.setTree(null);
					treeNodesRepository.save(nodeToRemove);
					treeRepository.save(tree);
					tree = getTree(treeId);
				}
			}
		}
	}
}
