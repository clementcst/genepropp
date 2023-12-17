package com.acfjj.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.Message;
import com.acfjj.app.utils.ValidationType;

public interface MessageRepository extends CrudRepository<Message, Long>{
	List<Message> findByConcernedUserIdAndValidationType(Long concernedUserId, ValidationType validationType);
}
