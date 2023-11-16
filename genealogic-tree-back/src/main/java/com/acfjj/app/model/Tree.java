package com.acfjj.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@SuppressWarnings("serial")
@Entity
public class Tree implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
	private int privacy;
	
	private long viewOfMonth;
	private long viewOfYear;
	
	@JsonIgnore
	@OneToMany(mappedBy = "tree")
	private Set<TreeNodes> treeNodes = new HashSet<>();
	
	
	public Tree() {
		super();
	}
	public Tree(String name, int privacy, Set<TreeNodes> nodes) {
		this();
		this.name=name;
		this.privacy = privacy;
		this.treeNodes = nodes;
		this.viewOfMonth = 0;
		this.viewOfYear = 0;
	}	
	
	//utiliser ce constructeur à la création
	public Tree(String name, int privacy) {
		this(name, privacy, null);
	}
	
	/*Getters & Setters*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrivacy() {
		return privacy;
	}

	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}
	
	public Set<TreeNodes> getTreeNodes() {
		return treeNodes;
	}
	
	public void addTreeNodes(TreeNodes treeNode) {
		this.getTreeNodes().add(treeNode);
	}

	public boolean isTreePublic() {
		return this.getPrivacy() == 1;		
	}
	
	public long getViewOfMonth() {
		return viewOfMonth;
	}

	public void setViewOfMonth(long viewOfMonth) {
		this.viewOfMonth = viewOfMonth;
	}

	public long getViewOfYear() {
		return viewOfYear;
	}

	public void setViewOfYear(long viewOfYear) {
		this.viewOfYear = viewOfYear;
	}
	
	public List<Node> getNodes() {
		List<Node> nodes = new ArrayList<>();
		for (TreeNodes treeNodes : treeNodes) {
			nodes.add(treeNodes.getNode());
		}
		return nodes;
	}
	
	public void addNode(Node node, int privacy, int depth) {
	    if (node != null) {
	        if (this.treeNodes == null) {
	            this.treeNodes = new HashSet<>();
	        }
	        boolean associationExists = this.treeNodes.stream()
	                .anyMatch(treeNodes -> treeNodes.getNode().equals(node));
	        if (!associationExists) {
	            TreeNodes treeNodes = new TreeNodes(this, node, privacy, depth);
	            this.addTreeNodes(treeNodes);
	            node.addTreeNodes(treeNodes);
	        }
	    }
	}
	
	public void removeNode(Node node) {
	    if (node != null && this.treeNodes != null) {
	        TreeNodes nodesToRemove = this.treeNodes.stream()
	                .filter(treeNodes -> treeNodes.getNode().equals(node))
	                .findFirst()
	                .orElse(null);

	        if (nodesToRemove != null) {
	            this.treeNodes.remove(nodesToRemove);
	            node.getTreeNodes().remove(nodesToRemove);
	        }
	    }
	}


	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Tree otherTree = (Tree) obj;
	    return (id != null && otherTree.id != null) ? 
	        id.equals(otherTree.id) &&
	        name.equals(otherTree.name) &&
	        viewOfMonth == otherTree.viewOfMonth &&
	        viewOfYear == otherTree.viewOfYear &&
	        privacy == otherTree.privacy  :
		    super.equals(obj);
	}

	@Override
	public String toString() {
		return "Tree [id=" + id 
				+ ", name=" + name 
				+ ", privacy=" + privacy 
				+ ", viewOfMonth=" + viewOfMonth
				+ ", viewOfYear=" + viewOfYear 
				+ ", nodes=" + treeNodes + "]";
	}	
	
}