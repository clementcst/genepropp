package com.acfjj.app.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@SuppressWarnings("serial")
@Entity
public class Conversation implements Serializable {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	
	 	@ManyToOne
	    @JoinColumn(name = "user_1_id")
	    private User user1;

	    @ManyToOne
	    @JoinColumn(name = "user_2_id")
	    private User user2;
	 	
	 	@OneToMany(mappedBy = "conversation")
		private Set<Message> messages = new HashSet<>();
		
	 	 public Conversation() {
	 		super();
	 	}
	    public Conversation(User user1, User user2, Set<Message> messages) {
			this();
			this.messages = messages;
			this.user1 = user1;
	 		this.user2 = user2;
		}
	    public Conversation(User user1, User user2) {
	 		this(user1, user2, null);
	 	}
	    
		/*Getters & Setters*/
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Set<Message> getMessages() {
			return messages;
		}
		public void setMessages(Set<Message> messages) {
			this.messages = messages;
		}
		public User getUser1() {
			return user1;
		}
		public void setUser1(User user1) {
			this.user1 = user1;
		}
		public User getUser2() {
			return user2;
		}
		public void setUser2(User user2) {
			this.user2 = user2;
		}
		
		
		
		//equals à faire
}
