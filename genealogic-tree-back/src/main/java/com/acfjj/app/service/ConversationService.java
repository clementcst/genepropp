package com.acfjj.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acfjj.app.repository.ConversationRepository;
import com.acfjj.app.repository.MessageRepository;
import com.acfjj.app.repository.UserRepository;
import com.acfjj.app.model.Conversation;
import com.acfjj.app.model.Message;
import com.acfjj.app.model.User;

@Service
public class ConversationService {

    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired 
    UserRepository userRepository;
    
    public Conversation getConversation(long id) {
		return conversationRepository.findById(id).orElse(null);
	}
    
    public List<Conversation> getUserConversations(User user) {
    	List<Conversation> conversationsResult = conversationRepository.findByUser1(user);
    	conversationsResult.addAll(conversationRepository.findByUser2(user));
    	return conversationsResult;    	
    }
    
    public Conversation getConversationOfUsers(User user1, User user2) {
    	Conversation conversationResult = conversationRepository.findByUser1AndUser2(user1, user2);
    	if(Objects.isNull(conversationResult)) {
    		conversationResult = conversationRepository.findByUser1AndUser2(user2, user1);
    	} 
    	return conversationResult;
    }
    
    public String addConversation(User user1, User user2) {
    	List<User> users = new ArrayList<>();
    	for (Conversation conversation : user1.getConversations()) { 
        	users.add(conversation.getUser1());
        	users.add(conversation.getUser2());
			if(users.contains(user1) || users.contains(user2)) {
				return "Conversation already exist";
			}		
		}
    	Conversation conversation = new Conversation(user1, user2);
    	conversationRepository.save(conversation);
    	user1.addConversation1(conversation);
		userRepository.save(user1);
		user2.addConversation2(conversation);
		userRepository.save(user2);
		return "Success";
    }
    
    public void updateConversation(long id, Conversation conversation) {
    	if(getConversation(id) != null && conversation.getId() == id) {
			conversationRepository.save(conversation);
		}
    }
    
    public void addMessageToConversation(Message message, Conversation conversation) {
    	messageRepository.save(message);
    	conversation.addMessage(message);
    	messageRepository.save(message);
    	updateConversation(conversation.getId(), conversation);
    }
    
 
}
