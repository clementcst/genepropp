package com.acfjj.app.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class PersonInfo implements Serializable {

    @Id @GeneratedValue
    private Long id;
    
	private String name;
	private String firstname;
	
	public PersonInfo() {
		super();
	}
	public PersonInfo(String name, String firstname) {
		super();
		this.name=name;
		this.firstname=firstname;
	}
	
	/*Getters & Setters*/
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
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String toString() {
		return "Person Info[id="+getId()+"; name="+getName()+"; firstname="+getFirstname()+"]";
	}
	
}