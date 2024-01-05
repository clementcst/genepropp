package com.acfjj.app.repository;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.Message;
import com.acfjj.app.model.User;
import com.acfjj.app.utils.ValidationType;

public interface MessageRepository extends CrudRepository<Message, Long>{
	List<Message> findByValidationInfosAndValidationType(String validationInfos, ValidationType validationType);
	List<Message> findByReceiverAndValidationType(User receiver, ValidationType validationType);
	List<Message> findBySenderAndValidationType(User sender, ValidationType validationType);
}
