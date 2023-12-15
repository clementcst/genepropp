package com.acfjj.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long>{
	
}
