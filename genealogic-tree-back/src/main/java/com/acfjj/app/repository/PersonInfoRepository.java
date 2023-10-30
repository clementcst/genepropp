package com.acfjj.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.PersonInfo;

public interface PersonInfoRepository extends CrudRepository<PersonInfo, Long>{
	
}
