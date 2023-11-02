package com.acfjj.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.User;
import com.acfjj.app.service.TreeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
public class TreeController{	
	@Autowired
	TreeService TreeService;
		
	static private List<Tree> Trees = new ArrayList<Tree>(Arrays.asList(new Tree[] {
            new Tree("Bourhara", 1, null),
            new Tree("Cassiet", 0, null),
            new Tree("Gautier", 0, null),
            new Tree("Cerf", 1, null),
            new Tree("Legrand", 2, null)
        }));
	
	@PostMapping("/trees")
    public void addTree() {
		for (Tree tree : Trees) {
			TreeService.addTree(tree);
		}
    }

    @GetMapping("/trees")
    public List<Tree> getTrees() {
        return TreeService.getAllTrees();
    }

    @GetMapping("/tree/{id}")
    public Tree getTree(@PathVariable Long id) {
        return TreeService.getTree(id);
    }

    @DeleteMapping("/tree/{id}")
    public void deleteTree(@PathVariable Long id) {
    	TreeService.deleteTree(id);
    }
	
	
}