package com.example.springbootstarteramqp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.springbootstarteramqp.SpringBootStarterAmqpApplication;
import com.example.springbootstarteramqp.config.Sender;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootStarterAmqpApplication.class) 
public class HelloApplicationTests {
	
	@Autowired
	private Sender sender;
	
	@Test
	public void hello() throws Exception {
		sender.send() ; 
	}
	
}
