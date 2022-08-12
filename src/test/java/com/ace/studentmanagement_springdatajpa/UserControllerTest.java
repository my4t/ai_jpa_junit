package com.ace.studentmanagement_springdatajpa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ace.studentmanagement_springdatajpa.controller.UserController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.ace.studentmanagement_springdatajpa.dao.UserRepository;
import com.ace.studentmanagement_springdatajpa.dao.UserService;
import com.ace.studentmanagement_springdatajpa.entity.User;
import com.ace.studentmanagement_springdatajpa.model.UserBean;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	MockHttpSession session;
	
	@MockBean
	UserService userService;
	
	@MockBean
	UserRepository repo;
	
	
	@Test
	public void displayregistertest() throws Exception {
		this.mockMvc.perform(get("/displayUserRegister"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"))
		.andExpect(model().attributeExists("userBean"));
	}
	
	@Test
	public void registerfailtest() throws Exception {
		this.mockMvc.perform(post("/userRegister"))
		.andExpect(status().isOk())
		.andExpect(model().attributeDoesNotExist("success"))
		.andExpect(view().name("USR001"));	
	}
	
	@Test
	public void registersuccesstest() throws Exception {
		UserBean userBean = new UserBean();
		userBean.setUserId("USR001");
		userBean.setUserName("Bob");
		userBean.setUserEmail("bob@gmail.com");
		userBean.setUserPassword("1337");
		userBean.setUserCfPassword("1337");
		userBean.setUserRole("User");
		this.mockMvc.perform(post("/userRegister").flashAttr("userBean", userBean))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("success"))
		.andExpect(view().name("USR001"));	
	}
	
	@Test
	public void invalidsessiontest() throws Exception {
		this.mockMvc.perform(get("/showUser"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/"));	
	}
	
	@Test
	public void showTest() throws Exception{
		session = new MockHttpSession();
		UserBean userBean = new UserBean();
		userBean.setUserId("USR001");
		userBean.setUserName("Bob");
		userBean.setUserEmail("bob@gmail.com");
		userBean.setUserRole("User");

		session.setAttribute("user", userBean); 
		 session.setAttribute("isLogin","Okay");
		this.mockMvc.perform(get("/showUser")
		.session(session))
		
		.andExpect(status().is(200))
		.andExpect(model().attributeExists("userList"))
		.andExpect(view().name("USR003"));
	}
	
	@Test
	public void setupaddtest() throws Exception {
		this.mockMvc.perform(get("/showAddUser"))
		.andExpect(view().name("USR001-01"))
		.andExpect(model().attributeExists("userBean"));
	}
	
	@Test
	public void addfailtest() throws Exception {
		this.mockMvc.perform(post("/userAdd"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001-01"));	
	}
	
	@Test
	public void addsuccesstest() throws Exception {
		UserBean userBean = new UserBean();
		userBean.setUserId("USR001");
		userBean.setUserName("Bob");
		userBean.setUserEmail("bob@gmail.com");
		userBean.setUserPassword("1337");
		userBean.setUserCfPassword("1337");
		userBean.setUserRole("User");
		this.mockMvc.perform(post("/userAdd").flashAttr("userBean", userBean))
		//.andExpect(model().attributeExists("success"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/showUser"));	
	}
	
	@Test
	public void allsearchtest() throws Exception {
		this.mockMvc.perform(get("/userSearch").param("id", "").param("name", ""))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/showUser"));	
	}
	
	@Test
	public void idsearchtest() throws Exception {
		this.mockMvc.perform(get("/userSearch").param("id", "1").param("name", ""))
		.andExpect(status().is(200))
		.andExpect(view().name("USR003"));
	}
	
	@Test
	public void showupdatetest() throws Exception {
		User usr = new User();
		usr.setId(1);
		usr.setName("Bob");
		usr.setEmail("bob@gmail.com");
		usr.setPassword("1337");
		usr.setRole("User");
		
		when(userService.getUser(1)).thenReturn(usr);
		this.mockMvc.perform(get("/showUserUpdate").param("id", "1"))
		.andExpect(status().is(200))
		.andExpect(view().name("USR002"))
		.andExpect(model().attributeExists("userBean"));
	}
	
	@Test
	public void updatefailtest() throws Exception {
		this.mockMvc.perform(post("/userUpdate"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"));
	}
	
	@Test
	public void updatetest() throws Exception {
		UserBean ub = new UserBean();
		ub.setUserId("1");
		ub.setUserName("Bob");
		ub.setUserEmail("bob@gmail.com");
		ub.setUserCfPassword("1337");
		ub.setUserPassword("1337");
		ub.setUserRole("User");
		this.mockMvc.perform(post("/userUpdate").flashAttr("userBean", ub))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/showUser"));	
	}
	
	@Test
	public void deletetest() throws Exception {
		this.mockMvc.perform(get("/userDelete").param("id", "1"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/showUser"));	
	}
	
	
	
	
}
