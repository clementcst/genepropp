package com.acfjj.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.Conversation;
import java.util.List;
import com.acfjj.app.model.User;


public interface ConversationRepository extends CrudRepository<Conversation, Long>{
	List<Conversation> findByUser1(User user1);
	List<Conversation> findByUser2(User user2);
	Conversation findByUser1AndUser2(User user1, User user2);
}
