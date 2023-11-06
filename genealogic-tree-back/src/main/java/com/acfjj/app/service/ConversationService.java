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
import com.acfjj.app.model.User;

@Service
public class ConversationService {

    @Autowired
    ConversationRepository conversationRepository;
    
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
    
    
}
