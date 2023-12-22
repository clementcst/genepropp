package com.acfjj.app.model;

import java.time.LocalDate;
import java.util.Objects;

import com.acfjj.app.utils.Constants;
import com.acfjj.app.utils.Misc;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class PersonInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String lastName;
	private String firstName;
	private int gender;
	private LocalDate dateOfBirth;
	private String countryOfBirth;
	private String cityOfBirth;
	private Boolean isDead;
	private String nationality;
	@Column(length = Constants.MAX_LONG_STRING_LENGTH)
	private String adress;
	private int postalCode;
	@Column(length = Constants.MAX_LONG_STRING_LENGTH)
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

	public PersonInfo(String lastName, String firstname, int gender, LocalDate dateOfBirth, String countryOfBirth,
			String cityOfBirth, Boolean isDead, String nationality, String adress, int postalCode,
			String profilPictureUrl) {
		this();
		this.setLastName(lastName);
		this.setFirstName(firstname);
		this.setGender(gender);
		this.setDateOfBirth(dateOfBirth);
		this.setCountryOfBirth(countryOfBirth);
		this.setCityOfBirth(cityOfBirth);
		this.isDead = isDead;
		this.relatedUser = null;
		this.relatedNode = null;
		this.setNationality(nationality);
		this.setAdress(adress);
		this.setPostalCode(postalCode);
		this.setProfilPictureUrl(profilPictureUrl);
	}

	/* Getters & Setters */
	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = Constants.GENDER_LIST.contains(gender) ? gender : Constants.DEFAULT_GENDER;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = Misc.isDateOfBirthAcceptable(dateOfBirth) ? dateOfBirth : Constants.DEFAULT_DATEOFBIRTH;
	}

	public void setDateOfBirth(int year, int month, int day) {
		setDateOfBirth(LocalDate.of(year, month, day));
	}

	public String getCityOfBirth() {
		return cityOfBirth;
	}

	public void setCityOfBirth(String cityOfBirth) {
		this.cityOfBirth = Misc.truncateString(cityOfBirth, Constants.MAX_STRING_LENGTH);
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

	public void setLastName(String lastName) {
		this.lastName = Misc.truncateString(lastName, Constants.MAX_STRING_LENGTH);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = Misc.truncateString(firstname, Constants.MAX_STRING_LENGTH);
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = Misc.truncateString(nationality, Constants.MAX_STRING_LENGTH);
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = Misc.truncateString(adress, Constants.MAX_LONG_STRING_LENGTH);
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = Misc.isPostalCodeAcceptable(postalCode) ? postalCode : Constants.DEFAULT_POSTAL_CODE;
	}

	public String getProfilPictureUrl() {
		return profilPictureUrl;
	}

	public void setProfilPictureUrl(String profilPictureUrl) {
		String ppUrl = Misc.truncateString(profilPictureUrl, Constants.MAX_LONG_STRING_LENGTH);
		this.profilPictureUrl = Misc.isLink(ppUrl) && !Objects.isNull(ppUrl) ? ppUrl : Constants.DEFAULT_PP_URL;
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
		this.countryOfBirth = Misc.truncateString(countryOfBirth, Constants.MAX_STRING_LENGTH);
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
		return (id != null && otherInfo.id != null) ? id.equals(otherInfo.id) && lastName.equals(otherInfo.lastName)
				&& firstName.equals(otherInfo.firstName) && gender == otherInfo.gender
				&& dateOfBirth.equals(otherInfo.dateOfBirth) && countryOfBirth.equals(otherInfo.countryOfBirth)
				&& cityOfBirth.equals(otherInfo.cityOfBirth) && Objects.equals(isDead, otherInfo.isDead)
				&& nationality.equals(otherInfo.nationality) && adress.equals(otherInfo.adress)
				&& postalCode == otherInfo.postalCode && Objects.equals(profilPictureUrl, otherInfo.profilPictureUrl)
				: super.equals(obj);
	}

	@Override
	public String toString() {
		return "PersonInfo [" + "id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", gender="
				+ gender + ", dateOfBirth=" + dateOfBirth + ", countryOfBirth=" + countryOfBirth + ", cityOfBirth="
				+ cityOfBirth + ", isDead=" + isDead + ", nationality=" + nationality + ", adress=" + adress
				+ ", postalCode=" + postalCode + ", profilPictureUrl=" + profilPictureUrl + ", relatedUser ="
				+ (Objects.isNull(relatedUser) ? null : relatedUser.getId()) + ", relatedNode ="
				+ (Objects.isNull(relatedNode) ? null : relatedNode.getId()) + "]";
	}

}