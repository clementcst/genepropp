package com.acfjj.app.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Conversation {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	
	 	@ManyToOne
	 	@JsonIgnore
	    @JoinColumn(name = "user_1_id")
	    private User user1;

	    @ManyToOne
	    @JsonIgnore
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
	 		this(user1, user2, new HashSet<Message>());
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
		public void addMessage(Message message) {
			getMessages().add(message);
			message.setConversation(this);
		}
		public User getUser1() {
			return user1;
		}
		public User getUser2() {
			return user2;
		}
		public Long getUserId1() {
			return user1.getId();
		}
		public Long getUserId2() {
			return user2.getId();
		}
		public User getWithWho(User asker) {
			return asker.equals(getUser1()) ? getUser2() : getUser1();
		}
		
		
		
		//equals à faire
}
