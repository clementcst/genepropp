package com.acfjj.app.model;

import java.time.LocalDate;
import java.util.Objects;

import com.acfjj.app.utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class PersonInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	private String lastName;
	private String firstName;
	private int gender;
	private LocalDate dateOfBirth;
	private String countryOfBirth;
	private String cityOfBirth;
	private Boolean isDead;
	private String nationality;
	private String adress;
	private int postalCode;
	@Column(length = Constants.MAX_STRING_LENGTH)
	private String profilPictureUrl;
	@JsonIgnore
	@OneToOne(mappedBy = "personInfo", optional = true, targetEntity = User.class)
	private User relatedUser;
	@JsonIgnore
	@OneToOne(mappedBy = "personInfo", optional = true, targetEntity = Node.class)
	private Node relatedNode;

	
	public PersonInfo() {
		super();
	}
	public PersonInfo(
			String lastName, String firstname, int gender, LocalDate dateOfBirth, String countryOfBirth,
			String cityOfBirth, Boolean isDead, String nationality, String adress, int postalCode, String profilPictureUrl) {
		this();
		this.lastName = lastName;
		this.firstName = firstname;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.countryOfBirth = countryOfBirth;
		this.cityOfBirth = cityOfBirth;
		this.isDead = isDead;
		this.relatedUser = null;
		this.relatedNode = null;
		this.nationality = nationality;
		this.adress = adress;
		this.postalCode = postalCode;
		this.profilPictureUrl = profilPictureUrl;
	}

	
	/*Getters & Setters*/
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public void setDateOfBirth(int year, int month, int day) {
		setDateOfBirth(LocalDate.of(year, month, day));
	}
	public String getCityOfBirth() {
		return cityOfBirth;
	}
	public void setCityOfBirth(String cityOfBirth) {
		this.cityOfBirth = cityOfBirth;
	}
	public Boolean isDead() {
		return isDead;
	}
	public void setIsDead(Boolean isDead) {
		this.isDead = isDead;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String name) {
		this.lastName = name;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}
	
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	public String getProfilPictureUrl() {
		return profilPictureUrl;
	}
	public void setProfilPictureUrl(String profilPictureUrl) {
		this.profilPictureUrl = profilPictureUrl;
	}
	public User getRelatedUser() {
		return relatedUser;
	}
	public void setRelatedUser(User relatedUser) {
		this.relatedUser = relatedUser;
	}
	public Node getRelatedNode() {
		return relatedNode;
	}
	public void setRelatedNode(Node relatedNode) {
		this.relatedNode = relatedNode;
	}
	public String getCountryOfBirth() {
		return countryOfBirth;
	}
	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}
	public Boolean isOrphan() {
		return Objects.isNull(getRelatedUser()) && Objects.isNull(getRelatedNode());
	}
	
	public static PersonInfo mergeUserPersonInfoWithNode(Node node, User user) {
		PersonInfo nodePI = node.getPersonInfo();
		PersonInfo userPI = user.getPersonInfo();
		PersonInfo finalPI = new PersonInfo();
		finalPI.setId(nodePI.getId());
		finalPI.setLastName(userPI.getLastName());
		finalPI.setFirstName(userPI.getFirstName());
		finalPI.setDateOfBirth(userPI.getDateOfBirth());
		finalPI.setCountryOfBirth(userPI.getCountryOfBirth());
		finalPI.setCityOfBirth(userPI.getCityOfBirth());
		finalPI.setGender(userPI.getGender());
		finalPI.setIsDead(userPI.isDead());
		finalPI.setAdress(Objects.isNull(userPI.getAdress()) ? nodePI.getAdress() : userPI.getAdress());
		finalPI.setNationality(
				Objects.isNull(userPI.getNationality()) ? nodePI.getNationality() : userPI.getNationality());
		finalPI.setPostalCode(Objects.isNull(userPI.getPostalCode()) ? nodePI.getPostalCode() : userPI.getPostalCode());
		finalPI.setProfilPictureUrl(Objects.isNull(userPI.getProfilPictureUrl()) ? nodePI.getProfilPictureUrl()
				: userPI.getProfilPictureUrl());
		return finalPI;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    PersonInfo otherInfo = (PersonInfo) obj;
	    return (id != null && otherInfo.id != null) ? 
	        id.equals(otherInfo.id) &&
	        lastName.equals(otherInfo.lastName) &&
	        firstName.equals(otherInfo.firstName) &&
	        gender == otherInfo.gender &&
	        dateOfBirth.equals(otherInfo.dateOfBirth) &&
	        countryOfBirth.equals(otherInfo.countryOfBirth) &&
	        cityOfBirth.equals(otherInfo.cityOfBirth) &&
	        Objects.equals(isDead, otherInfo.isDead) &&
	        nationality.equals(otherInfo.nationality) &&
	        adress.equals(otherInfo.adress) &&
	        postalCode == otherInfo.postalCode &&
	        Objects.equals(profilPictureUrl, otherInfo.profilPictureUrl) :
	        super.equals(obj);
	}

	@Override
	public String toString() {
	    return "PersonInfo [" +
	            "id=" + id + 
	            ", lastName=" + lastName + 
	            ", firstName=" + firstName +
	            ", gender=" + gender + 
	            ", dateOfBirth=" + dateOfBirth + 
	            ", countryOfBirth=" + countryOfBirth + 
	            ", cityOfBirth=" + cityOfBirth + 
	            ", isDead=" + isDead + 
	            ", nationality=" + nationality +
	            ", adress=" + adress +
	            ", postalCode=" + postalCode +
	            ", profilPictureUrl=" + profilPictureUrl +
	            ", relatedUser =" + (Objects.isNull(relatedUser) ? null : relatedUser.getId()) +
	            ", relatedNode =" + (Objects.isNull(relatedNode) ? null : relatedNode.getId()) +
	        "]";
	}

}