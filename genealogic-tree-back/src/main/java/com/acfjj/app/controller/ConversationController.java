package com.acfjj.app.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acfjj.app.model.Conversation;
import com.acfjj.app.model.Message;
import com.acfjj.app.model.Node;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.User;
import com.acfjj.app.utils.Constants;
import com.acfjj.app.utils.Misc;
import com.acfjj.app.utils.Response;
import com.acfjj.app.utils.ValidationType;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
@Scope("singleton")
@RequestMapping("/conversation")
public class ConversationController extends AbstractController {
	@GetMapping
	public Response getConversation(@RequestParam long conversationId) {
		Conversation conversation = conversationService.getConversation(conversationId);
		if (Objects.isNull(conversation)) {
			return new Response("Conversation not found", false);
		} else {
			return new Response(conversationService.getConversation(conversationId));
		}
	}

	@PostMapping("/new")
	public Response newConversation(@RequestParam long userId1, @RequestParam long userId2) {
		User user1 = userService.getUser(userId1);
		User user2 = userService.getUser(userId2);
		if (Objects.isNull(user1) || Objects.isNull(user2)) {
			return new Response("User not found", false);
		}
		String successMsg = conversationService.addConversation(user1, user2);
		Conversation newConv = user1.getConversationWith(user2);
		if (!Objects.isNull(newConv)) {
			return new Response(newConv.getId(), successMsg, true);
		}
		return new Response("A failure occured during creation of the conversation.", false);
	}

	@PostMapping("/message/new")
	public Response newMessage(@RequestParam long senderId, @RequestParam long receiverId,
			@RequestParam String content) {
		User sender = userService.getUser(senderId);
		User receiver = userService.getUser(receiverId);
		if (Objects.isNull(sender) || Objects.isNull(receiver)) {
			return new Response("User not found", false);
		}
		Conversation conversation = conversationService.getConversationOfUsers(sender, receiver);
		if (Objects.isNull(conversation)) {
			return new Response("Conversation not found or doesn't exist.", false);
		}
		conversationService.addMessageToConversation(
				new Message(sender, receiver, Misc.truncateString(content, Constants.MAX_LONG_STRING_LENGTH)),
				conversation);
		return new Response("Message successfully sent." + (content.length() > Constants.MAX_LONG_STRING_LENGTH
				? " But message content was too long and has been truncated."
				: ""), true);
	}

	public Response newValidationMessage(long senderId, long receiverId, ValidationType validationType,
			User concernedUser, Long baseNodeId, Long additionNodeId, Long relatedToNodeId, String relationType) {
		User sender = userService.getUser(senderId);
		User receiver = userService.getUser(receiverId);
		if (Objects.isNull(sender) || Objects.isNull(receiver)) {
			return new Response("User not found", false);
		}
		Conversation conversation = conversationService.getConversationOfUsers(sender, receiver);
        if(Objects.isNull(conversation)) {
            Response newConvRes = newConversation(senderId, receiverId);
            if(!newConvRes.getSuccess()) {
                return newConvRes;
            }
            conversation = conversationService.getConversationOfUsers(sender, receiver);
        }

		if (!Objects.isNull(concernedUser)) {
			conversationService.addMessageToConversation(new Message(sender, receiver, validationType, concernedUser),
					conversation);
		} else if (!Objects.isNull(baseNodeId) && !Objects.isNull(additionNodeId) && !Objects.isNull(relatedToNodeId)
				&& !Objects.isNull(relationType)) {
			Node baseNode = nodeService.getNode(baseNodeId);
			Node additionNode = nodeService.getNode(additionNodeId);
			Node relatedToNode = nodeService.getNode(relatedToNodeId);
			if (Objects.isNull(baseNode) || Objects.isNull(additionNode) || Objects.isNull(relatedToNode)) {
				return new Response("One or more node id is invalid, nodes not found", false);
			}
			conversationService.addMessageToConversation(
					new Message(sender, receiver, validationType, baseNode, additionNode, relatedToNode, relationType),
					conversation);
		}
		return new Response("Success", true);
	}

	public Response newValidationMessage(long senderId, long receiverId, ValidationType validationType,
			User concernedUser) {
		return newValidationMessage(senderId, receiverId, validationType, concernedUser, null, null, null, null);
	}

	public Response newValidationMessage(long senderId, long receiverId, ValidationType validationType, Long baseNodeId,
			Long additionNodeId, Long relatedToNodeId, String relationType) {
		return newValidationMessage(senderId, receiverId, validationType, null, baseNodeId, additionNodeId,
				relatedToNodeId, relationType);
	}

	@PostMapping("/validation/newTreeMergeValidation")
	public Response newTreeMergeValidation(@RequestParam long senderId, @RequestParam long receiverId,
			@RequestParam long baseNodeId, @RequestParam long additionNodeId, @RequestParam long relatedToNodeId,
			@RequestParam String relationType) {
		return newValidationMessage(senderId, receiverId, ValidationType.TREE_MERGE_VALIDATION, baseNodeId,
				additionNodeId, relatedToNodeId, relationType);
	}

	@PostMapping("/validation/userValidation")
	public Response userValidation(@RequestParam long msgId, @RequestParam long concernedUserId,
			@RequestParam long validatorId, @RequestParam Boolean userResponse) {
		Message validationMsg = conversationService.getMessage(msgId);
		if (Objects.isNull(validationMsg) || !validationMsg.getIsValidation()) {
			return new Response("Validation message not found or has already been validated", false);
		}
		User concernedUser = userService.getUser(concernedUserId);
		if (Objects.isNull(concernedUser)
				|| concernedUserId != Long.parseLong(validationMsg.getValidationInfos().get("concernedUserId"))) {
			return new Response("Concerned user not found or mismatch", false);
		}
		User validator = userService.getUser(validatorId);
		if (Objects.isNull(validator) || validatorId != validationMsg.getReceiverId()) {
			return new Response("Validator user not found or mismatch", false);
		}
		List<Message> similarValidations = conversationService.getUserValidationsOfConcernedUser(concernedUser);
		if (!similarValidations.contains(validationMsg)) {
			return new Response("Validation message and concerned user mismatch", false);
		}
		String successMsg = validationMsg.getValidationType().name() + " successfully "
				+ (userResponse ? "validated and disable" : "disabled");
		if (userResponse) {
			concernedUser.setValidated(true);
			userService.updateUser(concernedUser.getId(), concernedUser);
		}
		for (Message validation : similarValidations) {
			conversationService.disableValidation(validation, userResponse, validator);
		}
		return new Response(successMsg, true);
	}

	@PostMapping("/validation/treeMergeValidation")
	public Response treeMergeValidation(@RequestParam long msgId, @RequestParam long requesterId,
			@RequestParam long validatorId, @RequestParam long baseNodeId, @RequestParam long additionNodeId,
			@RequestParam long relatedToNodeId, @RequestParam String relationType, @RequestParam Boolean userResponse) {
		Message validationMsg = conversationService.getMessage(msgId);
		if (Objects.isNull(validationMsg) || !validationMsg.getIsValidation()) {
			return new Response("Validation message not found or has already been validated", false);
		}
		User requester = userService.getUser(requesterId);
		if (Objects.isNull(requester) || requesterId != validationMsg.getSenderId()) {
			return new Response("Requester user not found or mismatch", false);
		}
		User validator = userService.getUser(validatorId);
		if (Objects.isNull(validator) || validatorId != validationMsg.getReceiverId()) {
			return new Response("Validator user not found or mismatch", false);
		}
		String successMsg = validationMsg.getValidationType().name() + " successfully "
				+ (userResponse ? "validated and disable" : "disabled");
		Node baseNode = nodeService.getNode(baseNodeId);
		Node additionNode = nodeService.getNode(additionNodeId);
		Node relatedToNode = nodeService.getNode(relatedToNodeId);
		Tree tree1 = userService.getUser(validatorId).getMyTree();
		Tree tree2 = userService.getUser(requesterId).getMyTree();
		if (Objects.isNull(baseNode) || Objects.isNull(additionNode) || Objects.isNull(relatedToNode)) {
			return new Response("One or more node id is invalid, nodes not found", false);
		}
		if (userResponse) {
			additionNode.getPersonInfo()
					.setCountryOfBirth(additionNode.getCountryOfBirth().replace(Constants.TEMPORARY_STR_MAKER, ""));
			treeService.treeMerge(tree1, tree2, baseNode, relatedToNode, additionNode, relationType);
		} 
		//delete addition Node, help ici, jsp comment faire pour supprimer ça
		conversationService.disableValidation(validationMsg, userResponse, validator);
		return new Response(successMsg, true);
	}
}
