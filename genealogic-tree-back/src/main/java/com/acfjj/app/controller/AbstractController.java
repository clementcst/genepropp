package com.acfjj.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import com.acfjj.app.service.ConversationService;
import com.acfjj.app.service.NodeService;
import com.acfjj.app.service.TreeService;
import com.acfjj.app.service.UserService;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
@Scope("session")
public abstract class AbstractController {
	@Autowired
	protected TreeService treeService;
	@Autowired
	protected NodeService nodeService;
	@Autowired
	protected UserService userService;
	@Autowired 
	protected ConversationService conversationService;
}