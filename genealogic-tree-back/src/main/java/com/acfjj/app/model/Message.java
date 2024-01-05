package com.acfjj.app.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
	 	
	 	@Column(length = Constants.MAX_LONG_STRING_LENGTH)
	 	private String content;
	 	
	 	private LocalDateTime messageDateTime;
	 	
	 	//for validations
	 	private ValidationType validationType;

	 	@Column(length = Constants.MAX_LONG_STRING_LENGTH)
	 	private String validationInfos;
	 	
	 	public Message() {
	 		super();
	 	}
	 	
	 	public Message(User sender, User receiver, String content, ValidationType validationType, User concernedUser, Node baseNode, Node additionNode, Node relatedToNode, String relationType) {
			this();
			this.sender = sender;
			this.receiver = receiver;
			this.conversation = null;
			this.validationType = validationType;
			this.messageDateTime = Misc.getLocalDateTime();	
			LinkedHashMap<String,String> LHMvalidationInfos = new LinkedHashMap<>();
			if (!Objects.isNull(concernedUser) && !Objects.isNull(validationType)) {
				LHMvalidationInfos.put("concernedUserId", concernedUser.getId().toString());
				this.setContent(validationType.getValidationMsg(concernedUser));
				this.setValidationInfos(LHMvalidationInfos);
			} else if (!Objects.isNull(baseNode) && !Objects.isNull(additionNode) && !Objects.isNull(validationType)) {
				LHMvalidationInfos.put("baseNodeId",baseNode.getId().toString());
				LHMvalidationInfos.put("additionNodeId",additionNode.getId().toString());
				LHMvalidationInfos.put("relatedToNodeId",relatedToNode.getId().toString());
				LHMvalidationInfos.put("relationType",relationType);
				this.setValidationInfos(LHMvalidationInfos);
				this.setContent(validationType.getValidationMsg(new ArrayList<Object>(Arrays.asList(this, baseNode, relatedToNode))));
			} else {
				this.setContent(content);
				this.setValidationInfos(null);
			}
		}
	 	
	 	public Message(User sender, User receiver, ValidationType validationType, User userConcernedByValidation) {
	 		this(sender, receiver, null, validationType, userConcernedByValidation, null, null, null, null);
	 	}
	    
	 	public Message(User sender, User receiver, ValidationType validationType, Node baseNode, Node additionNode, Node relatedToNode, String relationType) {
	 		this(sender, receiver, null, validationType, null, baseNode, additionNode, relatedToNode, relationType);
	 	}
	 	
	    public Message(User sender, User receiver, String content) {
	    	this(sender,receiver,content,null, null, null, null, null, null);
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
				this.content = Misc.truncateString(content, Constants.MAX_LONG_STRING_LENGTH);
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
		public LinkedHashMap<String, String> getValidationInfos() {
			return Objects.isNull(validationInfos) ? null : Misc.convertFromString(validationInfos);
		}
		public void setValidationInfos(LinkedHashMap<String, String> validationInfos) {
			this.validationInfos = Objects.isNull(validationInfos) ? null : Misc.convertToString(validationInfos);
		}

		public void disableValidation(boolean response, User validator) {
			this.getValidationType().setDisableValidationMsg(this, response, validator);
			this.setValidationType(null);
			this.setValidationInfos(null);
		}
		//equals à faire
}
