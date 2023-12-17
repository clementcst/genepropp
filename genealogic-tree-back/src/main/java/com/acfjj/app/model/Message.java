package com.acfjj.app.model;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Objects;

import com.acfjj.app.utils.Constants;
import com.acfjj.app.utils.Misc;
import com.acfjj.app.utils.ValidationType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Message {
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
	 	
	 	@Column(length = Constants.MAX_STRING_LENGTH)
	 	private String content;
	 	
	 	private ValidationType validationType;
	 	private LocalDateTime messageDateTime;
	 	private Long concernedUserId;
	 	
	 	public Message() {
	 		super();
	 	}
	 	
	 	public Message(User sender, User receiver, String content, ValidationType validationType, User concernedUser) {
			this();
			this.sender = sender;
			this.receiver = receiver;
			this.conversation = null;
			this.validationType = validationType;
			this.messageDateTime = Misc.getLocalDateTime();	
			this.concernedUserId = Objects.isNull(concernedUser) ? null : concernedUser.getId();
			this.setContent(Objects.isNull(validationType) ? content : validationType.getValidationMsg(concernedUser));
		}
	 	
	 	public Message(User sender, User receiver, ValidationType validationType, User userConcernedByValidation) {
	 		this(sender, receiver, null, validationType, userConcernedByValidation);
	 	}
	    
	    public Message(User sender, User receiver, String content) {
	    	this(sender,receiver,content,null, null);
	    }
	    
		/*Getters & Setters*/
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		@JsonIgnore
		public User getSender() {
			return sender;
		}
		@JsonIgnore
		public User getReceiver() {
			return receiver;
		}
		public String getContent() {
			return content;
		}
		
		public void setContent(String content) {
			if(content.length() > Constants.MAX_STRING_LENGTH)
				this.content = "Message content was to big and has been truncated";
			else
				this.content = content;
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

		public LocalDateTime getMessageDateTime() {
			return messageDateTime;
		}

		public void setMessageDateTime(LocalDateTime messageDateTime) {
			this.messageDateTime = messageDateTime;
		}
		
		@JsonIgnore
		public ValidationType getValidationType() {
			return validationType;
		}

		public void setValidationType(ValidationType validationType) {
			this.validationType = validationType;
		}
		
		public Boolean getIsValidation() {
			return !Objects.isNull(getValidationType());
		}
		public LinkedHashMap<String, String> getRequests() {
			if(getIsValidation()) {
				return getValidationType().getValidationRequests(this);
			} else {
				return null;
			}
		}
		@JsonIgnore
		public Long getConcernedUserId() {
			return concernedUserId;
		}		
		public void setConcernedUserId(Long concernedUserId) {
			this.concernedUserId = concernedUserId;
		}

		public void disableValidation() {
			this.getValidationType().setDisableValidationMsg(this);
			this.setValidationType(null);
			this.setConcernedUserId(null);
		}
		//equals à faire
}
