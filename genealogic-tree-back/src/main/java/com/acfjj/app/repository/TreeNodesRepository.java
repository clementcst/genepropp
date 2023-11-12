package com.acfjj.app.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.Tree;
import com.acfjj.app.model.TreeNodes;


public interface TreeNodesRepository extends CrudRepository<TreeNodes, Long>{
	Set<TreeNodes> findAll();
}
