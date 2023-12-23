package com.acfjj.test;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.acfjj.app.GenTreeApp;
import com.acfjj.test.model.*;
import com.acfjj.test.service.*;


@Suite
@SelectClasses({
	PersonInfoTest.class,
	UserTest.class,
	TreeTest.class,
	NodeTest.class,
	UserServiceTest.class,
	TreeServiceTest.class,
	NodeServiceTest.class	
})
@SpringBootTest(classes = GenTreeApp.class)
@TestPropertySource(locations = "classpath:test.properties")
public class GenTreeAppTests {		
	@Test
	void contextLoader() {
	}	
}
