package com.acfjj.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.Node;
import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.User;

public interface NodeRepository extends CrudRepository<Node, Long>{
	Node findByPersonInfo(PersonInfo personInfo);
}
