package com.example.demo;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.
content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.
status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.web.GreetingController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class DemoApplicationTests {
	
	private MockMvc mvc;
	
	@Before
	public void setOp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new GreetingController()).build();
	}
	
	@Test
	public void hello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/greeting") .accept(MediaType.
		APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().string(equalTo("Hello World"))); 
	}
}
