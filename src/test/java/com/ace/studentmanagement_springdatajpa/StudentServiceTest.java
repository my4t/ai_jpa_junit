package com.ace.studentmanagement_springdatajpa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.studentmanagement_springdatajpa.dao.StudentRepository;
import com.ace.studentmanagement_springdatajpa.dao.StudentService;
import com.ace.studentmanagement_springdatajpa.entity.Student;

@SpringBootTest
public class StudentServiceTest {
	@Mock
	StudentRepository repo;
	
	@InjectMocks
	StudentService studentService;
	
	@Test
	public void saveTest() {
		Student s = new Student();
		s.setId("STU001");
		s.setName("Bob");
		s.setDob("13/3/97");
		s.setGender("Male");
		s.setEducation("Diploma in IT");
		s.setPhone("09133740");
		
		when(repo.save(s)).thenReturn(s);
		Student std = studentService.save(s);
		assertEquals("STU001", std.getId());
		verify(repo, times(1)).save(s);
	}
	
	@Test
	public void fetchOneTest() {
		
		Student s = new Student();
		s.setId("STU001");
		s.setName("Bob");
		s.setDob("13/3/97");
		s.setGender("Male");
		s.setEducation("Diploma in IT");
		s.setPhone("09133740");
		
		Optional<Student> student = Optional.of(s);
		
		when(repo.findById("STU001")).thenReturn(student);
		Student std = studentService.getById("STU001");
		assertEquals("STU001", std.getId());
		verify(repo, times(1)).findById("STU001");
	}
	
	@Test
	public void fetchAllTest() {
		List<Student> students = new ArrayList<Student>();
		Student s1 = new Student();
		s1.setId("STU001");
		s1.setName("Bob");
		s1.setDob("13/3/97");
		s1.setGender("Male");
		s1.setEducation("Diploma in IT");
		s1.setPhone("09133740");
		Student s2 = new Student();
		s2.setId("STU002");
		s2.setName("Alice");
		s2.setDob("13/3/97");
		s2.setGender("Male");
		s2.setEducation("Diploma in IT");
		s2.setPhone("09133740");
		Student s3 = new Student();
		s3.setId("STU003");
		s3.setName("John");
		s3.setDob("13/3/97");
		s3.setGender("Male");
		s3.setEducation("Diploma in IT");
		s3.setPhone("09133740");
		
		students.add(s3);
		students.add(s2);
		students.add(s1);
		
		when(repo.findAll()).thenReturn(students);
		List<Student> studentList = studentService.getAllStudent();
		assertEquals(3, studentList.size());
		verify(repo, times(1)).findAll();
	}
	
	@Test
	public void updateTest() {
		Student s = new Student();
		s.setId("STU001");
		s.setName("Bob");
		s.setDob("13/3/97");
		s.setGender("Male");
		s.setEducation("Diploma in IT");
		s.setPhone("09133740");
		
		when(repo.save(s)).thenReturn(s);
		Student std = studentService.update(s);
		assertEquals("STU001", std.getId());
		verify(repo, times(1)).save(s);
	}
	
	@Test
	public void deleteTest() {
		studentService.deleteById("STU001");
		verify(repo, times(1)).deleteById("STU001");
	}
	
	@Test
	public void searchtest() {
		List<Student> students = new ArrayList<Student>();
		Student s1 = new Student();
		s1.setId("STU001");
		s1.setName("Bob");
		s1.setDob("13/3/97");
		s1.setGender("Male");
		s1.setEducation("Diploma in IT");
		s1.setPhone("09133740");
		Student s2 = new Student();
		s2.setId("STU002");
		s2.setName("Alice");
		s2.setDob("13/3/97");
		s2.setGender("Male");
		s2.setEducation("Diploma in IT");
		s2.setPhone("09133740");
		Student s3 = new Student();
		s3.setId("STU003");
		s3.setName("John");
		s3.setDob("13/3/97");
		s3.setGender("Male");
		s3.setEducation("Diploma in IT");
		s3.setPhone("09133740");
		
		students.add(s3);
		students.add(s2);
		students.add(s1);
		
		when(repo.findDistinctStudentByIdOrNameOrCourses_Name("STU003", "John", "Test")).thenReturn(students);
		List<Student> stdList = studentService.findStudentByIdOrNameOrCourses("STU003", "John", "Test");
		assertEquals(3, stdList.size());
		verify(repo, times(1)).findDistinctStudentByIdOrNameOrCourses_Name("STU003", "John", "Test");
	}
	
	@Test
	public void idIncTest() {
		List<Student> students = new ArrayList<Student>();
		Student s1 = new Student();
		s1.setId("STU001");
		s1.setName("Bob");
		s1.setDob("13/3/97");
		s1.setGender("Male");
		s1.setEducation("Diploma in IT");
		s1.setPhone("09133740");
		Student s2 = new Student();
		s2.setId("STU002");
		s2.setName("Alice");
		s2.setDob("13/3/97");
		s2.setGender("Male");
		s2.setEducation("Diploma in IT");
		s2.setPhone("09133740");
		Student s3 = new Student();
		s3.setId("STU003");
		s3.setName("John");
		s3.setDob("13/3/97");
		s3.setGender("Male");
		s3.setEducation("Diploma in IT");
		s3.setPhone("09133740");
		
		students.add(s1);
		students.add(s2);
		students.add(s3);
		
		when(repo.findAll()).thenReturn(students);
		String newId = studentService.idGenerator();
		assertEquals("STU004", newId);
		verify(repo, times(1)).findAll();
	}

}
