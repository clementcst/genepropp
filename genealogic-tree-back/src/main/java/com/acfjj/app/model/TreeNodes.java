package com.acfjj.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TreeNodes {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "tree_id")
	    private Tree tree;

	    @ManyToOne
	    @JoinColumn(name = "node_id")
	    private Node node;

	    private int privacy;
	    private int depth;
	    
	    public TreeNodes() {
	    	super();
	    }
	    
	    public TreeNodes(Tree tree, Node node, int privacy, int depth) {
	    	this();
	        this.tree = tree;
	        this.node = node;
	        this.privacy = privacy;
	        this.depth = depth;
	    }
	    
	    /*Getters & Setters*/
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Tree getTree() {
			return tree;
		}
		public void setTree(Tree tree) {
			this.tree = tree;
		}
		public Node getNode() {
			return node;
		}
		public void setNode(Node node) {
			this.node = node;
		}
		public int getPrivacy() {
			return privacy;
		}
		public void setPrivacy(int privacyNode) {
			this.privacy = privacyNode;
		}
		public int getDepth() {
			return depth;
		}
		public void setDepth(int profondeur) {
			this.depth = profondeur;
		}

		@Override
		public boolean equals(Object obj) {
		    if (this == obj) {
		        return true;
		    }
		    if (obj == null || getClass() != obj.getClass()) {
		        return false;
		    }
		    TreeNodes otherTreeNodes = (TreeNodes) obj;
		    return (id != null && otherTreeNodes.id != null) ? 
		        id.equals(otherTreeNodes.id) &&
		        tree.equals(otherTreeNodes.tree) &&
		        node.equals(otherTreeNodes.node) &&
		        privacy == otherTreeNodes.privacy &&
		        depth == otherTreeNodes.depth :
		        super.equals(obj);
		}

		@Override
		public String toString() {
			return "TreeNodes [id=" + id + ", tree=" + tree.getId() + ", node=" + node.getId() + ", privacy=" + privacy + ", depth="
					+ depth + "]";
		}
}
