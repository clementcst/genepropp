package com.acfjj.app.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@SuppressWarnings("serial")
@Entity
public class Node implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "person_info_id")
	private PersonInfo personInfo;
    @OneToOne
    @JoinColumn(name = "created_by_user_id")
	private User createdBy;

    private int privacy;
	private Node Parent1;
	private Node Parent2;
	
	//Voir si person node peux etre null
	//chercher parent avec nom, prénom, etc (pas id)
	//faire updated parent dans service

	
	public Node() {
		super();
	}
	public Node(PersonInfo personInfo, User createdBy, Node Parent1, Node Parent2) {
		super();
		this.personInfo=personInfo;
		this.createdBy=createdBy;
		this.Parent1=Parent1;
		this.Parent2=Parent2;
	}
	public Node(String lastName, String firstname, int gender, LocalDate dateOfBirth, String countryOfBirth, String cityOfBirth, User createdBy, int privacy) {
		super();
		this.personInfo = new PersonInfo(lastName,firstname,gender,dateOfBirth,countryOfBirth,cityOfBirth,false);
		this.createdBy=createdBy;
		this.Parent1=null;
		this.Parent2=null;
		this.privacy = privacy;
	}
	public Node(String lastName, String firstname, int gender, LocalDate dateOfBirth, String countryOfBirth, String cityOfBirth, User createdBy, Node Parent1, Node Parent2, int privacy) {
		super();
		this.personInfo = new PersonInfo(lastName,firstname,gender,dateOfBirth,countryOfBirth,cityOfBirth,false);
		this.createdBy=createdBy;
		this.Parent1=Parent1;
		this.Parent2=Parent2;
		this.privacy = privacy;
	}
	
	@OneToMany(mappedBy = "node")
	private Set<TreeNodes> nodeTrees = new HashSet<>();
	
	
	/*Getters & Setters*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public Node getParent1() {
		return Parent1;
	}
	public void setParent1(Node parent1) {
		Parent1 = parent1;
	}
	public Node getParent2() {
		return Parent2;
	}
	public void setParent2(Node parent2) {
		Parent2 = parent2;
	}
	
	public Set<TreeNodes> getNodeTrees() {
		return nodeTrees;
	}
	public void setNodeTrees(Set<TreeNodes> nodeTrees) {
		this.nodeTrees = nodeTrees;
	}
	
	public boolean isOrphan() {
		return this.getNodeTrees().isEmpty();
	}
	
	public int getPrivacy() {
		return privacy;
	}
	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    Node other = (Node) obj;
	    return (id != null && other.id != null) ? 
	        id.equals(other.id) &&
	        personInfo.equals(other.personInfo) &&
	        createdBy.equals(other.createdBy) &&
	        privacy == other.privacy &&
	        Parent1.equals(other.Parent1) &&
	        Parent2.equals(other.Parent2) &&
	        personInfo.equals(other.personInfo) :
	        super.equals(obj);
	}
	
	@Override
	public String toString() {
		return "PersonNode ["
				+ "id=" + id +
				", personInfo=" + personInfo + 
				", createdBy=" + createdBy + 
				", Parent1=" + Parent1 + 
				", Parent2=" + Parent2 + "]";
	}
	
}