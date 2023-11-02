package com.acfjj.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.Conversation;

public interface ConversationRepository extends CrudRepository<Conversation, Long>{
	
}
