package com.acfjj.app.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.TreeNodes;
import com.acfjj.app.model.Node;


public interface TreeNodesRepository extends CrudRepository<TreeNodes, Long>{
	  Set<TreeNodes> findAll();
	  TreeNodes findByNode(Node node);
}
