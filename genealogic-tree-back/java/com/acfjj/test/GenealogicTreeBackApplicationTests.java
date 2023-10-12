package com.acfjj.test;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.JupiterTestEngine;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import com.acfjj.app.GenealogicTreeApplication;




@SpringBootTest(classes = GenealogicTreeApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class GenealogicTreeBackApplicationTests {
	  @Test
	    void contextLoader() {
		  
	    }
	
}
