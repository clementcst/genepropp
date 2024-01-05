package com.acfjj.app.utils;

import java.util.LinkedHashMap;
import java.util.List;

import com.acfjj.app.model.Message;
import com.acfjj.app.model.Node;
import com.acfjj.app.model.User;

public enum ValidationType {
	USER_VALIDATION("A new user as been created and request a validation") {
		@Override
		public String getValidationMsg(Object obj) {
			User concernedUser = (User) obj;
			String validationMsg = this.validationMsg;
			validationMsg += "\n User to validate :";
			validationMsg += concernedUser.toString();
			return validationMsg;
		}

		@Override
		public LinkedHashMap<String, String> getValidationRequests(Message msg) {
			LinkedHashMap<String, String> requests = new LinkedHashMap<String, String>();
			String requestsStart = "/conversation/validation/userValidation?msgId=" + msg.getId() + "&concernedUserId="
					+ msg.getValidationInfos().get("concernedUserId") + "&validatorId=" + msg.getReceiverId()
					+ "&userResponse=";
			requests.put("Yes", requestsStart + "1");
			requests.put("No", requestsStart + "0");
			return requests;
		}

		@Override
		public void setDisableValidationMsg(Message msg, boolean userResponse, User validator) {
			String disabledMsg = msg.getContent() + "\nUser has been ";
			disabledMsg += userResponse ? "validated " : "refused ";
			disabledMsg += "by the admin: " + validator.getFullName() + " on " + Misc.getLocalDateTime();
			msg.setContent(disabledMsg);
		}
	},
	TREE_MERGE_VALIDATION("Hello, I want to create a link with a node of which you're the creator. ") {
		@Override
		public String getValidationMsg(Object obj) {
			@SuppressWarnings("unchecked")
			List<Object> objList = (List<Object>) obj;
			Message msg = (Message) objList.get(0);
			Node baseNode = (Node) objList.get(1);
			Node relatedToNode = (Node) objList.get(2);
			String validationMsg = this.validationMsg;
			validationMsg += "\n\n The node concerned in your tree is this one : \n" + baseNode.toString();
			validationMsg += "\n\n and will be linked with this node in my tree : \n" + relatedToNode.toString();
			validationMsg += "\n\n The relation beetween them would be : " + msg.getValidationInfos().get("relationType");
			validationMsg += "\n\n Here is my tree";
			validationMsg += "\n link to my tree : "
			+ "<a class=\"text-green-700 hover:underline\" ng-reflect-router-link=\"/myTreePage\" ng-reflect-query-params=\"[object Object]\" href=\"/myTreePage?treeId="+ msg.getSender().getMyTreeId()+"\">View Tree</a>" ;
			return validationMsg;
		}

		@Override
		public LinkedHashMap<String, String> getValidationRequests(Message msg) {
			LinkedHashMap<String, String> requests = new LinkedHashMap<String, String>();
			String requestsStart = "/conversation/validation/treeMergeValidation?msgId=" + msg.getId() + "&requesterId="
					+ msg.getSenderId() + "&validatorId=" + msg.getReceiverId() + "&baseNodeId="
					+ msg.getValidationInfos().get("baseNodeId") + "&additionNodeId="
					+ msg.getValidationInfos().get("additionNodeId") + "&relatedToNodeId="
					+ msg.getValidationInfos().get("relatedToNodeId") + "&relationType="
					+ msg.getValidationInfos().get("relationType") + "&userResponse=";
			requests.put("Yes", requestsStart + "1");
			requests.put("No", requestsStart + "0");
			return requests;
		}

		@Override
		public void setDisableValidationMsg(Message msg, boolean userResponse, User validator) {
			String disabledMsg = msg.getContent() + "\nTree merge proposal has been ";
			disabledMsg += userResponse ? "accepted " : "refused ";
			disabledMsg += "by : " + validator.getFullName() + " on " + Misc.getLocalDateTime();
			disabledMsg += userResponse
					? "\nTree " + msg.getReceiver().getMyTreeId() + "of " + msg.getReceiver().getFullName()
							+ " has been merge with the tree " + msg.getSender().getMyTreeId() + "of "
							+ msg.getSender().getFullName() + "."
					: "\nIf one of you wanted to create a node that was in the tree of the other, or was the other person, create it in private";
			msg.setContent(disabledMsg);
		}
	};

	protected final String validationMsg;

	private ValidationType(String validationMsg) {
		this.validationMsg = validationMsg;
	}

	public abstract String getValidationMsg(Object obj);

	public abstract void setDisableValidationMsg(Message msg, boolean userResponse, User validator);

	public abstract LinkedHashMap<String, String> getValidationRequests(Message msg);
}
