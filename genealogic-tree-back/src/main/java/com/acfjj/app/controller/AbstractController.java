package com.acfjj.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import com.acfjj.app.model.Node;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.User;
import com.acfjj.app.service.ConversationService;
import com.acfjj.app.service.NodeService;
import com.acfjj.app.service.TreeService;
import com.acfjj.app.service.UserService;
import com.acfjj.app.utils.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
@Scope("singleton")
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