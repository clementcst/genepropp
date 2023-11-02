package com.acfjj.app.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "person_info_id")
	private PersonInfo personInfo;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "created_by_user_id")
	private User createdBy;

    private int privacy;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "parent_1_node_id")
	private Node parent1; 
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "parent_2_node_id")
	private Node parent2;
    
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "partner_node_id")
	private Node partner;
    
    @JsonIgnore
    @OneToMany
	private Set<Node> exPartners = new HashSet<>();
    
    @JsonIgnore
    @OneToMany
	private Set<Node> siblings = new HashSet<>();
    
    @JsonIgnore
	@OneToMany(mappedBy = "node")
	private Set<TreeNodes> trees = new HashSet<>();
	
	public Node() {
		super();
	}
	public Node(PersonInfo personInfo, User createdBy, Node parent1, Node parent2) {
		this();
		this.personInfo=personInfo;
		this.createdBy=createdBy;
		this.parent1=parent1;
		this.parent2=parent2;
	}
	public Node(String lastName, String firstname, int gender, LocalDate dateOfBirth, String countryOfBirth, String cityOfBirth, User createdBy, Node parent1, Node parent2, int privacy, String nationality, String adress, int postalCode, String profilPictureData64) {
	    this(new PersonInfo(lastName, firstname, gender, dateOfBirth, countryOfBirth, cityOfBirth, false, nationality, adress, postalCode, profilPictureData64),
	    	 createdBy, parent1, parent2);
	}
	public Node(String lastName, String firstname, int gender, LocalDate dateOfBirth, String countryOfBirth, String cityOfBirth, User createdBy, Node parent1, int privacy, String nationality, String adress, int postalCode, String profilPictureData64) {
	    this(lastName, firstname, gender, dateOfBirth, countryOfBirth, cityOfBirth, createdBy, parent1, null, privacy, nationality, adress, postalCode, profilPictureData64);
	}
	public Node(String lastName, String firstname, int gender, LocalDate dateOfBirth, String countryOfBirth, String cityOfBirth, User createdBy, int privacy, String nationality, String adress, int postalCode, String profilPictureData64) {
	    this(lastName, firstname, gender, dateOfBirth, countryOfBirth, cityOfBirth, createdBy, null, null, privacy, nationality, adress, postalCode, profilPictureData64);
	}

	
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
		return parent1;
	}
	public void setParent1(Node parent1) {
		this.parent1 = parent1;
	}
	public Node getParent2() {
		return parent2;
	}
	public void setParent2(Node parent2) {
		this.parent2 = parent2;
	}
	
	public Set<TreeNodes> getTrees() {
		return trees;
	}
	public void setTrees(Set<TreeNodes> nodeTrees) {
		this.trees = nodeTrees;
	}
	
	public Node getPartner() {
		return partner;
	}
	public void setPartner(Node partner) {
		this.partner = partner;
	}
	public Set<Node> getExPartners() {
		return exPartners;
	}
	public void setExPartners(Set<Node> exPartners) {
		this.exPartners = exPartners;
	}
	public Set<Node> getSiblings() {
		return siblings;
	}
	public void setSiblings(Set<Node> siblings) {
		this.siblings = siblings;
	}
	public boolean isOrphan() {
		return this.getTrees().isEmpty();
	}
	
	public int getPrivacy() {
		return privacy;
	}
	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}
	public String getUserLastName() {
	    return personInfo.getLastName();
	}

	public String getUserFirstName() {
	    return personInfo.getFirstName();
	}

	public int getUserGender() {
	    return personInfo.getGender();
	}

	public LocalDate getUserDateOfBirth() {
	    return personInfo.getDateOfBirth();
	}

	public String getUserCountryOfBirth() {
	    return personInfo.getCountryOfBirth();
	}

	public String getUserCityOfBirth() {
	    return personInfo.getCityOfBirth();
	}

	public Boolean isUserDead() {
	    return personInfo.isDead();
	}

	public String getUserNationality() {
	    return personInfo.getNationality();
	}

	public String getUserAdress() {
	    return personInfo.getAdress();
	}

	public int getUserPostalCode() {
	    return personInfo.getPostalCode();
	}

	public String getUserProfilPictureData64() {
	    return personInfo.getProfilPictureData64();
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
	        parent1.equals(other.parent1) &&
	        parent2.equals(other.parent2) &&
	        personInfo.equals(other.personInfo) :
	        super.equals(obj);
	}
	
	@Override
	public String toString() {
		return "PersonNode ["
				+ "id=" + id +
				", personInfo=" + personInfo + 
				", createdBy=" + createdBy + 
				", parent1=" + parent1.getPersonInfo().getFirstName() + 
				", parent2=" + parent2.getPersonInfo().getFirstName()  + "]";
	}
	
}