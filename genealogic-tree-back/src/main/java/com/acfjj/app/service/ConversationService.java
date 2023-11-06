package com.acfjj.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.acfjj.app.repository.ConversationRepository;
import com.acfjj.app.repository.MessageRepository;

@Service
public class ConversationService {

    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
	MessageRepository messageRepository;
}
