package com.acfjj.app.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
    @ManyToOne
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
    @OneToMany(fetch=FetchType.EAGER)
	private Set<Node> exPartners = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER)
	private Set<Node> siblings = new HashSet<>();
    
    @JsonIgnore
	@OneToMany(mappedBy = "node",fetch=FetchType.EAGER)
	private Set<TreeNodes> treeNodes = new HashSet<>();
	
	public Node() {
		super();
	}
	public Node(PersonInfo personInfo, User createdBy, Node parent1, Node parent2, int privacy) {
		this();
		this.personInfo=personInfo;
		this.createdBy=createdBy;
		this.parent1=parent1;
		this.parent2=parent2;
		this.privacy = privacy;
	}
	public Node(PersonInfo personInfo, User createdBy, int privacy) {
		this(personInfo, createdBy, null, null, 0);
	}
	public Node(String lastName, String firstname, int gender, LocalDate dateOfBirth, String countryOfBirth, String cityOfBirth, User createdBy, Node parent1, Node parent2, int privacy, String nationality, String adress, int postalCode, String profilPictureData64) {
	    this(new PersonInfo(lastName, firstname, gender, dateOfBirth, countryOfBirth, cityOfBirth, false, nationality, adress, postalCode, profilPictureData64),
	    	 createdBy, parent1, parent2, privacy);
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
	public Long getParent1Id() {
		if(Objects.isNull(parent1)) {
			return null;
		}
		return parent1.getId();
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
	public Long getParent1Id() {
		if(Objects.isNull(parent2)) {
			return null;
		}
		return parent2.getId();
	}
	public Set<TreeNodes> getTreeNodes() {
		return treeNodes;
	}
	public void addTreeNodes(TreeNodes nodeTree) {
		this.trees.add(nodeTree);
	}
	public void setTreeNodes(Set<TreeNodes> nodeTree) {
		this.trees = nodeTree;
	}

	public void removeTreeNodes(TreeNodes treeNode) {
		this.trees.remove(treeNode);
	}
	
	public Node getPartner() {
		return partner;
	}
	public void setPartner(Node partner) {
		this.partner = partner;
	}
	public Long getPartnerId() {
		if(Objects.isNull(partner)) {
			return null;
		}
		return partner.getId();
	}
	public Set<Node> getExPartners() {
		return exPartners;
	}
	public void addExPartners(Node exPartner) {
		this.exPartners.add(exPartner);
	}
	public List<Long> getExPartnersId() {
		if(Objects.isNull(exPartners)) {
			return null;
		} 
		List<Long> exPartnersId = new ArrayList<>();
		for (Node exPartner : exPartners) {
			exPartnersId.add(exPartner.getId());
		}
		return exPartnersId;
	}
	public Set<Node> getSiblings() {
		return siblings;
	}
	public void addSiblings(Node sibling) {
		this.siblings.add(sibling);
	}
	public List<Long> getSiblingsId() {
		if(Objects.isNull(siblings)) {
			return null;
		} 
		List<Long> siblingsId = new ArrayList<>();
		for (Node sibling : siblings) {
			siblingsId.add(sibling.getId());
		}
		return siblingsId;
	}
	public boolean isOrphan() {
		return (Objects.isNull(this.getParent1()) && Objects.isNull(this.getParent2()) && this.getSiblings().isEmpty() && Objects.isNull(this.getPartner()) && this.getExPartners().isEmpty());
	}	
	public int getPrivacy() {
		return privacy;
	}
	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}
	public String getLastName() {
	    return personInfo.getLastName();
	}

	public String getFirstName() {
	    return personInfo.getFirstName();
	}

	public int getGender() {
	    return personInfo.getGender();
	}

	public LocalDate getDateOfBirth() {
	    return personInfo.getDateOfBirth();
	}

	public String getCountryOfBirth() {
	    return personInfo.getCountryOfBirth();
	}

	public String getCityOfBirth() {
	    return personInfo.getCityOfBirth();
	}

	public Boolean isDead() {
	    return personInfo.isDead();
	}

	public String getNationality() {
	    return personInfo.getNationality();
	}

	public String getAdress() {
	    return personInfo.getAdress();
	}

	public int getPostalCode() {
	    return personInfo.getPostalCode();
	}

	public String getProfilPictureData64() {
	    return personInfo.getProfilPictureData64();
	}
	
	@JsonIgnore
	public List<Tree> getTree() {
		List<Tree> trees = new ArrayList<>();
		for (TreeNodes treeNodes : treeNodes) {
			trees.add(treeNodes.getTree());
		}
		return trees;
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
		long parent1Id;
		long parent2Id;
		long partnerId;
		if(parent1 == null) {
			parent1Id = -1;
		}else {
			parent1Id = parent1.getId();
		}
		if(parent2 == null) {
			parent2Id = -1;
		}else {
			parent2Id = parent2.getId();
		}
		if(partner == null) {
			partnerId = -1;
		}else {
			partnerId = partner.getId();
		}
		return "Node [id=" + id 
				+ ", personInfo=" + personInfo 
				+ ", createdBy=" + createdBy.getId()
				+ ", privacy=" + privacy
				+ ", parent1=" + getParent2Id()
				+ ", parent2=" + getParent2Id()
				+ ", partner=" + getPartnerId()
				+ ", exPartners=" +  getExPartnersId()
				+ ", siblings=" + getSiblingsId()
				+ ", trees= faire getTreesId" + "]";
	}
	
}
