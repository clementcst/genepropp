package com.acfjj.app.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;




@SuppressWarnings("serial")
@Entity
public class User implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "person_info_id")
	private PersonInfo personInfo;
    
    private String email;
    private String password;
    private Boolean validated;
    private Boolean isAdmin;
	
	public User() {
		super();
	}
	public User(String lastName, String firstname, int gender, LocalDate dateOfBirth, String countryOfBirth, String cityOfBirth, String email, String password) {
		this();
		this.email = email;
		this.password = password;
		this.validated = false;
		this.isAdmin = false;
		this.personInfo = new PersonInfo(lastName,firstname,gender,dateOfBirth,countryOfBirth,cityOfBirth,false);
	}
	
	/*Getters & Setters*/
	public Long getId() {
		return id;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String hashedPassword) {
		this.password = hashedPassword;
	}
	public Boolean isValidated() {
		return validated;
	}
	public void setValidated(Boolean validated) {
		this.validated = validated;
	}
	public Boolean isAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean admin) {
		this.isAdmin = admin;
	}
	
	public String getLastName() {
		return getPersonInfo().getLastName();
	}
	public void setLastName(String lastName) {
		getPersonInfo().setLastName(lastName);
	}
	public String getFirstName() {
		return getPersonInfo().getFirstName();
	}
	public void setFirstName(String firstName) {
		getPersonInfo().setFirstName(firstName);
	}
	public LocalDate getDateOfBirth() {
		return getPersonInfo().getDateOfBirth();
	}
	public void setDateOfBirth(int year, int month, int day) {
		getPersonInfo().setDateOfBirth(year, month, day);
	}
	public int getGender() {
		return getPersonInfo().getGender();
	}
	public void setGender(int gender) {
		getPersonInfo().setGender(gender);
	}
	public String getCityOfBirth() {
		return getPersonInfo().getCityOfBirth();
	}
	public void setCityOfBirth(String cityOfBirth) {
		getPersonInfo().setCityOfBirth(cityOfBirth);
	}
	public String getCountryOfBirth() {
		return getPersonInfo().getCountryOfBirth();
	}
	public void setCountryOfBirth(String countryOfBirth) {
		getPersonInfo().setCountryOfBirth(countryOfBirth);
	}
	
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    User otherUser = (User) obj;
	    return (id != null && otherUser.id != null) ? 
	        id.equals(otherUser.id) &&
	        email.equals(otherUser.email) &&
	        password.equals(otherUser.password) &&
	        validated.equals(otherUser.validated) &&
	        isAdmin.equals(otherUser.isAdmin) &&
	        personInfo.equals(otherUser.personInfo) :
	        super.equals(obj);
	}
	
	public String toString() {
		return "User[" + 
					"id="+getId()+  
					"," + getPersonInfo() +
					", email=" + getEmail() + 
				"]";
	}
	
}