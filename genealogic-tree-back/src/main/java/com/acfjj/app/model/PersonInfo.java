package com.acfjj.app.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@SuppressWarnings("serial")
@Entity
public class PersonInfo implements Serializable {

    @Id @GeneratedValue
    private Long id;
    
	private String lastName;
	private String firstName;
	private int gender;
	private LocalDate dateOfBirth;
	private String cityOfBirth;
	private Boolean isDead;
	private Long related_user_id;
	
	
	public PersonInfo() {
		super();
	}
	public PersonInfo(String name, String firstname, int gender, LocalDate dateOfBirth, String cityOfBirth, Boolean isDead) {
		this();
		this.lastName=name;
		this.firstName=firstname;
		this.gender=gender;
		this.dateOfBirth=dateOfBirth;
		this.cityOfBirth=cityOfBirth;
		this.isDead=isDead;
		this.related_user_id=null;
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
	public Boolean getIsDead() {
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
	
	public Long getRelated_user_id() {
		return related_user_id;
	}
	public void setRelated_user_id(Long related_user_id) {
		this.related_user_id = related_user_id;
	}
	
	@Override
	public String toString() {
		return "PersonInfo [" +
					"id=" + id + 
					", name=" + lastName + 
					", firstname=" + firstName +
					", gender="	+ gender + 
					", dateTimeOfBirth=" + dateOfBirth + 
					", cityOfBirth=" + cityOfBirth + 
					", isDead="	+ isDead + 
					", relatedUser=" + related_user_id +
				"]";
	}
	
	
	
}