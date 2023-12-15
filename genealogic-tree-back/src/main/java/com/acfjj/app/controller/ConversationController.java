package com.acfjj.app.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acfjj.app.model.Conversation;
import com.acfjj.app.model.Message;
import com.acfjj.app.model.User;
import com.acfjj.app.service.ConversationService;
import com.acfjj.app.service.UserService;
import com.acfjj.app.utils.Response;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
public class ConversationController{	
	@Autowired
	UserService userService;
	@Autowired
	ConversationService conversationService;
		
	@PostMapping("/conversation/new") 
	public Response newConversation(@RequestParam long userId1, @RequestParam long userId2) {
		User user1 = userService.getUser(userId1);
		User user2 = userService.getUser(userId2);
		if(Objects.isNull(user1) || Objects.isNull(user2)) {
			return new Response("User not found", false);
		}
		return new Response(conversationService.addConversation(user1, user2));
	}
	
	@PostMapping("/conversation/message/new") 
	public Response newMessage(@RequestParam long senderId, @RequestParam long receiverId, @RequestParam String content) {
		User sender = userService.getUser(senderId);
		User receiver = userService.getUser(receiverId);
		if(Objects.isNull(sender) || Objects.isNull(receiver)) {
			return new Response("User not found", false);
		}
		Conversation conversation = conversationService.getConversationOfUsers(sender, receiver);
		conversationService.addMessageToConversation(new Message(sender, receiver, content), conversation);
		return new Response("Success", true);
	}
	
	@GetMapping("/conversation")
    public Response getConversation(@RequestParam long conversationId) {
        Conversation conversation = conversationService.getConversation(conversationId);
        if (Objects.isNull(conversation)) {
            return new Response("Conversation not found", false);
        } else {
            return new Response(conversationService.getConversation(conversationId));
        }
    }
}