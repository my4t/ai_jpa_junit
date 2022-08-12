package com.ace.studentmanagement_springdatajpa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.ace.studentmanagement_springdatajpa.controller.Authentication;
import com.ace.studentmanagement_springdatajpa.dao.UserRepository;
import com.ace.studentmanagement_springdatajpa.dao.UserService;
import com.ace.studentmanagement_springdatajpa.model.UserBean;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	MockHttpSession session;
	
	@MockBean
	UserRepository repo;
	
	@MockBean
	UserService userService;
	
	@Test
	public void displaylogintest() throws Exception {
		this.mockMvc.perform(get("/"))
		.andExpect(view().name("LGN001"));
	}
	
	@Test
	public void showmenufail() throws Exception {
		this.mockMvc.perform(get("/showMenu"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/"));
	}
	
	@Test
	public void showmenutest() throws Exception {
		session = new MockHttpSession();
		UserBean userBean = new UserBean();
		userBean.setUserId("USR001");
		userBean.setUserName("Bob");
		userBean.setUserEmail("bob@gmail.com");
		userBean.setUserRole("User");

		session.setAttribute("user", userBean); 
		session.setAttribute("isLogin","Okay");
		
		this.mockMvc.perform(get("/showMenu")
		.session(session))
		.andExpect(status().is(200))
		.andExpect(view().name("MNU001"));
		
	}
}
