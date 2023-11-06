package com.acfjj.app.model;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Message implements Serializable {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	
	 	@OneToOne
	 	@JoinColumn(name = "sender_id")
	 	private User sender;
	 	
	 	@OneToOne
	 	@JoinColumn(name = "receiver_id")
	 	private User receiver;
	 	
	 	@ManyToOne
	 	@JoinColumn(name = "conversation_id")
	 	private Conversation conversation;
	 	
	 	private String content;
	   
	 	public Message() {
	 		super();
	 	}
	    public Message(User sender, User receiver, String content) {
			this();
			this.sender = sender;
			this.receiver = receiver;
			this.content = content;
		}
	    
		/*Getters & Setters*/
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public User getSender() {
			return sender;
		}
		public void setSender(User sender) {
			this.sender = sender;
		}
		public User getReceiver() {
			return receiver;
		}
		public void setReceiver(User receiver) {
			this.receiver = receiver;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}

		public Conversation getConversation() {
			return conversation;
		}

		public void setConversation(Conversation conversation) {
			this.conversation = conversation;
		}
		
		
		
				
		//equals à faire
}
