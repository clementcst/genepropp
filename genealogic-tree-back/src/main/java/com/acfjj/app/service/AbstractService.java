package com.acfjj.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.acfjj.app.repository.ConversationRepository;
import com.acfjj.app.repository.MessageRepository;
import com.acfjj.app.repository.NodeRepository;
import com.acfjj.app.repository.PersonInfoRepository;
import com.acfjj.app.repository.TreeNodesRepository;
import com.acfjj.app.repository.TreeRepository;
import com.acfjj.app.repository.UserRepository;

@Service
@Scope("session")
public abstract class AbstractService {
    @Autowired
    ConversationRepository conversationRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired 
    NodeRepository nodeRepository;
    @Autowired
    PersonInfoRepository personInfoRepository;
    @Autowired 
    TreeNodesRepository treeNodesRepository;
    @Autowired 
    TreeRepository treeRepository;
    @Autowired 
    UserRepository userRepository;
}
