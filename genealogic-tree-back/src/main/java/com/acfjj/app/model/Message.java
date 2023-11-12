package com.acfjj.app.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Message implements Serializable {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	
	 	@ManyToOne
	 	@JsonIgnore
	 	@JoinTable(name = "message_sender")
	 	private User sender;
	 	
	 	@ManyToOne
	 	@JsonIgnore
	 	@JoinTable(name = "message_receiver")
	 	private User receiver;
	 	
	 	@ManyToOne
	 	@JsonIgnore
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
			this.conversation = null;
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
		public User getReceiver() {
			return receiver;
		}
		public String getContent() {
			return content;
		}
		public Long getSenderId() {
			return sender.getId();
		}
		public Long getReceiverId() {
			return receiver.getId();
		}
		public Conversation getConversation() {
			return conversation;
		}
		public Long getConversationId() {
			return conversation.getId();
		}

		public void setConversation(Conversation conversation) {
			this.conversation = conversation;
		}
		
		
		
				
		//equals à faire
}
