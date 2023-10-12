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
    private String hashedPassword;
    private Boolean validated;
    private Boolean isAdmin;
	
	public User() {
		super();
	}
	public User(String name, String firstname, int gender, LocalDate dateOfBirth, String cityOfBirth, String email, String hashedPassword) {
		this();
		this.email = email;
		this.hashedPassword = hashedPassword;
		this.validated = false;
		this.isAdmin = false;
		this.personInfo = new PersonInfo(name,firstname,gender,dateOfBirth,cityOfBirth,false);
	}
	
	/*Getters & Setters*/
	public Long getId() {
		return id;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHashedPassword() {
		return hashedPassword;
	}
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	public Boolean getValidated() {
		return validated;
	}
	public void setValidated(Boolean validated) {
		this.validated = validated;
	}
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String getLastName() {
		return getPersonInfo().getLastName();
	}
	public void setLastName(String lastName) {
		getPersonInfo().setLastName(lastName);
	}
	public String getFirstName() {
		return getPersonInfo().getLastName();
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
	
	public String toString() {
		return "User[" + "id="+getId()+"; "+getPersonInfo()+"; email="+getEmail()+"]";
	}
	
}