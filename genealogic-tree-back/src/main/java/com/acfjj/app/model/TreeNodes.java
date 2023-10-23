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

}
