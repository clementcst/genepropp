package com.acfjj.app.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.acfjj.app.utils.Constants;
import com.acfjj.app.utils.Misc;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Tree {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private int privacy;

	private long viewOfMonth;
	private long viewOfYear;
	private LocalDate VOMresetDate;
	private LocalDate VOYresetDate;

	@JsonIgnore
	@OneToMany(mappedBy = "tree", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<TreeNodes> treeNodes = new HashSet<>();

	public Tree() {
		super();
	}

	public Tree(String name, int privacy, TreeNodes treeNode) {
		this();
		this.name = name;
		this.privacy = privacy;
		this.treeNodes.add(treeNode);
		this.viewOfMonth = 0;
		this.viewOfYear = 0;
		this.VOMresetDate = Constants.DEFAULT_DATEOFBIRTH;
		this.VOYresetDate = Constants.DEFAULT_DATEOFBIRTH;
	}

	// utiliser ce constructeur à la création
	public Tree(String name, int privacy) {
		this(name, privacy, null);
	}

	/* Getters & Setters */
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
		this.privacy = Constants.TREE_PRIVACY_LIST.contains(privacy) ? privacy : 1;
	}

	public Set<TreeNodes> getTreeNodes() {
		return treeNodes;
	}

	public TreeNodes getTreeNodesByNode(Node node) {
		for (TreeNodes treeNodes : getTreeNodes()) {
			if (treeNodes.getNode().equals(node)) {
				return treeNodes;
			}
		}
		return null;
	}

	public void addTreeNodes(TreeNodes treeNode) {
		this.getTreeNodes().add(treeNode);
	}

	public void setTreeNodes(Set<TreeNodes> treeNode) {
		this.treeNodes = treeNode;
	}

	// public void removeTreeNodes(TreeNodes treeNode) {
	// this.getNodes().remove(treeNode);
	// }

	@JsonIgnore
	public boolean isPublic() {
		return this.getPrivacy() == Constants.TREE_PRIVACY_PUBLIC;
	}

	public void setIsPublic(Boolean isPublic) {
		if(isPublic) {
			this.setPrivacy(Constants.TREE_PRIVACY_PUBLIC);
		} else {
			this.setPrivacy(Constants.TREE_PRIVACY_PRIVATE);
		}
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
	
	public void addAView() {
		resetViewIfNec();
		setViewOfMonth(getViewOfMonth()+1);
		setViewOfYear(getViewOfYear()+1);
	}

	public void resetViewIfNec() {
		LocalDate today = Misc.getLocalDateTime().toLocalDate();
		if(today.getMonthValue() != this.getVOMresetDate().getMonthValue()) {
			setViewOfMonth(0);
			setVOMresetDate(today);
		}
		if(today.getYear() != this.getVOMresetDate().getYear()) {
			setViewOfMonth(0);
			setVOYresetDate(today);
		}
	}

	public LocalDate getVOMresetDate() {
		return VOMresetDate;
	}

	public void setVOMresetDate(LocalDate vOMresetDate) {
		VOMresetDate = vOMresetDate;
	}

	public LocalDate getVOYresetDate() {
		return VOYresetDate;
	}

	public void setVOYresetDate(LocalDate vOYresetDate) {
		VOYresetDate = vOYresetDate;
	}

	public List<Node> getNodes() {
		List<Node> nodes = new ArrayList<>();
		for (TreeNodes treeNodes : treeNodes) {
			nodes.add(treeNodes.getNode());
		}
		return nodes;
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
		return (id != null && otherTree.id != null)
				? id.equals(otherTree.id) && name.equals(otherTree.name) && viewOfMonth == otherTree.viewOfMonth
						&& viewOfYear == otherTree.viewOfYear && privacy == otherTree.privacy
				: super.equals(obj);
	}

	@Override
	public String toString() {
		return "Tree [id=" + id + ", name=" + name + ", privacy=" + privacy + ", viewOfMonth=" + viewOfMonth
				+ ", viewOfYear=" + viewOfYear + ", nodes=" + treeNodes + "]";
	}

}
