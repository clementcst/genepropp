package com.acfjj.app.model;

import java.io.Serializable;

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
	
	public User() {
		super();
	}
	public User(PersonInfo personInfo, String email) {
		super();
		this.personInfo=personInfo;
		this.email=email;
	}
	public User(String name, String firstname, String email) {
		super();
		this.personInfo = new PersonInfo(name, firstname);
		this.email=email;
	}
	
	/*Getters & Setters*/
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	
	public String toString() {
		return "User[id="+getId()+"; "+getPersonInfo()+"; email="+getEmail()+"]";
	}
	
}