package com.ace.studentmanagement_springdatajpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.ace.studentmanagement_springdatajpa.dao.StudentRepository;
import com.ace.studentmanagement_springdatajpa.dao.StudentService;
import com.ace.studentmanagement_springdatajpa.entity.Course;
import com.ace.studentmanagement_springdatajpa.entity.Student;
import com.ace.studentmanagement_springdatajpa.model.StudentBean;
import com.ace.studentmanagement_springdatajpa.model.UserBean;

@SpringBootTest
@AutoConfigureMockMvc

public class StudentControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	MockHttpSession session;
	
	@MockBean
	StudentService studentService;
	
	@MockBean
	StudentRepository repo;
	
	@Test
	public void showregistertest() throws Exception {
		session = new MockHttpSession();
		UserBean userBean = new UserBean();
		userBean.setUserId("USR001");
		userBean.setUserName("Bob");
		userBean.setUserEmail("bob@gmail.com");
		userBean.setUserRole("User");

		session.setAttribute("user", userBean); 
		session.setAttribute("isLogin","Okay");
		
		this.mockMvc.perform(get("/showStudentRegister")
		.session(session))
		.andExpect(model().attributeExists("studentBean"))
		.andExpect(view().name("STU001"));
		
		
	}
	
	@Test
	public void showregisterfailtest() throws Exception {
		this.mockMvc.perform(get("/showStudentRegister"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/"));
	}
	
	@Test
	public void registerfailtest() throws Exception {
		this.mockMvc.perform(post("/studentRegister").flashAttr("studentBean", new StudentBean()))
		.andExpect(status().isOk())
		.andExpect(view().name("STU001"));
	}
	
	@Test
	public void registertest() throws Exception {
		List<Course> courses = new ArrayList<Course>();
		Course c1 = new Course();
		c1.setId(1);
		c1.setName("Java");
		Course c2 = new Course();
		c2.setId(2);
		c2.setName("PHP");
		courses.add(c1);
		courses.add(c2);
		StudentBean studentBean = new StudentBean();
		studentBean.setStudentId("1");
		studentBean.setStudentName("Bob");
		studentBean.setStudentCourse((ArrayList<Course>) courses);
		studentBean.setStudentGender("Male");
		studentBean.setStudentDob("1/1/97");
		studentBean.setStudentPhone("133740");
		studentBean.setStudentEducation("Bachelor in IT");
		studentBean.setStudentPhoto(null);
		when(studentService.idGenerator()).thenReturn("1");
		this.mockMvc.perform(post("/studentRegister").flashAttr("studentBean", studentBean))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("success"))
		.andExpect(view().name("STU001"));
	}
	
	@Test
	public void showfailtest() throws Exception {
		this.mockMvc.perform(get("/showStudentAll"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/"));
	}
	
	@Test
	public void showtest() throws Exception {
		
		session = new MockHttpSession();
		UserBean userBean = new UserBean();
		userBean.setUserId("USR001");
		userBean.setUserName("Bob");
		userBean.setUserEmail("bob@gmail.com");
		userBean.setUserRole("User");

		session.setAttribute("user", userBean); 
		session.setAttribute("isLogin","Okay");
		
		Set<Course> courses = new HashSet<Course>();
		Course c1 = new Course();
		c1.setId(1);
		c1.setName("Java");
		Course c2 = new Course();
		c2.setId(2);
		c2.setName("PHP");
		courses.add(c1);
		courses.add(c2);
		
		Student std1 = new Student();
		std1.setId("1");
		std1.setName("Bob");
		std1.setGender("Male");
		std1.setDob("1/1/97");
		std1.setPhone("091337");
		std1.setEducation("Diploma in IT");
		std1.setCourses(courses);
		
		Student std2 = new Student();
		std2.setId("2");
		std2.setName("John");
		std2.setGender("Male");
		std2.setDob("1/1/98");
		std2.setPhone("091337");
		std2.setEducation("Diploma in IT");
		std2.setCourses(courses);
		List<Student> students = new ArrayList<Student>();
		students.add(std1);
		students.add(std2);
		when(studentService.getAllStudent()).thenReturn(students);
		
		this.mockMvc.perform(get("/showStudentAll")
				.session(session))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("studentList"))
		.andExpect(view().name("STU003"));
	}
	
	@Test
	public void searchalltest() throws Exception {
		this.mockMvc.perform(get("/searchStudent").param("id", "").param("name", "").param("course", ""))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/showStudentAll"));
	}
	
	@Test
	public void searchbytest() throws Exception {
		Set<Course> courses = new HashSet<Course>();
		Course c1 = new Course();
		c1.setId(1);
		c1.setName("Java");
		Course c2 = new Course();
		c2.setId(2);
		c2.setName("PHP");
		courses.add(c1);
		courses.add(c2);
		
		Student std1 = new Student();
		std1.setId("1");
		std1.setName("Bob");
		std1.setGender("Male");
		std1.setDob("1/1/97");
		std1.setPhone("091337");
		std1.setEducation("Diploma in IT");
		std1.setCourses(courses);
		
		Student std2 = new Student();
		std2.setId("2");
		std2.setName("John");
		std2.setGender("Male");
		std2.setDob("1/1/98");
		std2.setPhone("091337");
		std2.setEducation("Diploma in IT");
		std2.setCourses(courses);
		List<Student> students = new ArrayList<Student>();
		students.add(std1);
		students.add(std2);
		when(studentService.findStudentByIdOrNameOrCourses("1", "Bob", "C#")).thenReturn(students);
		this.mockMvc.perform(get("/searchStudent").param("id", "1").param("name", "Bob").param("course", "C#"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("studentList"))
		.andExpect(view().name("STU003"));
		
	}
	
	@Test
	public void seemoretest() throws Exception {
		Set<Course> courses = new HashSet<Course>();
		Course c1 = new Course();
		c1.setId(1);
		c1.setName("Java");
		Course c2 = new Course();
		c2.setId(2);
		c2.setName("PHP");
		courses.add(c1);
		courses.add(c2);
		
		Student std1 = new Student();
		std1.setId("1");
		std1.setName("Bob");
		std1.setGender("Male");
		std1.setDob("1/1/97");
		std1.setPhone("091337");
		std1.setEducation("Diploma in IT");
		std1.setCourses(courses);
		when(studentService.getById("1")).thenReturn(std1);
		
		this.mockMvc.perform(get("/seeMore/1").param("id", "1"))
		.andExpect(status().isOk()).andExpect(model().attributeExists("studentBean"))
		.andExpect(view().name("STU002"));
		
	}
	
	@Test
	public void deletetest() throws Exception {
		this.mockMvc.perform(get("/deleteStudent").param("id", "1"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/showStudentAll"));
	}
	
	@Test
	public void showupdatetest() throws Exception {
		Set<Course> courses = new HashSet<Course>();
		Course c1 = new Course();
		c1.setId(1);
		c1.setName("Java");
		Course c2 = new Course();
		c2.setId(2);
		c2.setName("PHP");
		courses.add(c1);
		courses.add(c2);
		
		Student std1 = new Student();
		std1.setId("1");
		std1.setName("Bob");
		std1.setGender("Male");
		std1.setDob("1/1/97");
		std1.setPhone("091337");
		std1.setEducation("Diploma in IT");
		std1.setCourses(courses);
		when(studentService.getById("1")).thenReturn(std1);
		
		this.mockMvc.perform(get("/showStudentUpdate").param("id", "1"))
		.andExpect(status().isOk()).andExpect(model().attributeExists("studentBean"))
		.andExpect(view().name("STU002-01"));
		
	}
	
	@Test
	public void updatefailtest() throws Exception {
		this.mockMvc.perform(post("/updateStudent"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU002-01"));
	}
	
	@Test
	public void updatetest() throws Exception {
		List<Course> courses = new ArrayList<Course>();
		Course c1 = new Course();
		c1.setId(1);
		c1.setName("Java");
		Course c2 = new Course();
		c2.setId(2);
		c2.setName("PHP");
		
		courses.add(c1);
		courses.add(c2);
		StudentBean studentBean = new StudentBean();
		studentBean.setStudentId("1");
		studentBean.setStudentName("Bob");
		studentBean.setStudentCourse((ArrayList<Course>) courses);
		studentBean.setStudentGender("Male");
		studentBean.setStudentDob("1/1/97");
		studentBean.setStudentPhone("133740");
		studentBean.setStudentEducation("Bachelor in IT");
		studentBean.setStudentPhoto(null);
		
		this.mockMvc.perform(post("/updateStudent").flashAttr("studentBean", studentBean))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/showStudentAll"));
		
		
	}

}
