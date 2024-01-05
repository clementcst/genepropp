package com.acfjj.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.Tree;

public interface TreeRepository extends CrudRepository<Tree, Long>{
	List<Tree> findByPrivacy(int i);
	Tree findByName(String name);
}
