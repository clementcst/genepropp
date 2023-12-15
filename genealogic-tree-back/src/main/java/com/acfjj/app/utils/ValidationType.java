package com.acfjj.app.utils;

import java.util.LinkedHashMap;

import com.acfjj.app.model.Message;
import com.acfjj.app.model.User;
import com.acfjj.app.service.AbstractService;
import com.acfjj.app.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.xml.ws.Service;

public enum ValidationType {
	USER_VALIDATION("A new user as been created and request a validation") {
		@Override
		public String getValidationMsg(User concernedUser) {
			String validationMsg = this.validationMsg;
			validationMsg += "\n User to validate :";
			validationMsg += concernedUser.toString();
			return validationMsg;
		}
		@Override
		public LinkedHashMap<String, String> getValidationRequests(Message msg) {
			LinkedHashMap<String,String> requests = new LinkedHashMap<String, String>();
			String requestsStart = "/conversation/validation/userValidation?msgId="+ msg.getId() +
								   "&concernedUserId=" + msg.getConcernedUserId() + 
								   "&validatorId=" + msg.getReceiverId() +
								   "&userResponse=";
			requests.put("Yes", requestsStart + "1");
			requests.put("No", requestsStart + "0");
			return requests;
		}
		@Override
		public void setDisableValidationMsg(Message msg) {
			msg.setContent(msg.getContent() + "\nUser has been validated by the admin: " + msg.getSender().getFullName() + " on " + Misc.getLocalDateTime());
		}
	};
	protected final String validationMsg;
	
	private ValidationType(String validationMsg) {
		this.validationMsg = validationMsg;
	}
	public abstract String getValidationMsg(User concernedUser);
	
	public abstract void setDisableValidationMsg(Message msg);
	
	public abstract LinkedHashMap<String,String> getValidationRequests(Message msg);
}
