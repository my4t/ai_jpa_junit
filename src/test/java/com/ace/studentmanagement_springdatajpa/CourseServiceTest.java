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

import com.ace.studentmanagement_springdatajpa.dao.CourseRepository;
import com.ace.studentmanagement_springdatajpa.dao.CourseService;
import com.ace.studentmanagement_springdatajpa.entity.Course;

@SpringBootTest
public class CourseServiceTest {
	@Mock
	CourseRepository repo;
	
	@InjectMocks
	CourseService courseService;
	
	@Test
	public void fetchAllTest() {
		List<Course> courses = new ArrayList<Course>();
		Course c1 = new Course();
		c1.setId(1);
		c1.setName("Java");
		Course c2 = new Course();
		c2.setId(2);
		c2.setName("PHP");
		Course c3 = new Course();
		c3.setId(3);
		c3.setName("OJT");
		
		courses.add(c1);
		courses.add(c2);
		courses.add(c3);
		
		when(repo.findAll()).thenReturn(courses);
		List<Course> courseList = courseService.getAllCourse();
		assertEquals(3, courseList.size());
		verify(repo, times(1)).findAll();
	}
	
	@Test
	public void fetchOneTest() {
		Course c1 = new Course();
		c1.setId(1);
		c1.setName("Java");
		
		Optional<Course> c = Optional.of(c1);
		when(repo.findById(1)).thenReturn(c);
		Course course = courseService.getCourse(1);
		assertEquals("Java", course.getName());
		verify(repo, times(1)).findById(1);
	}
	
	@Test
	public void saveTest() {
		Course c1 = new Course();
		c1.setId(1);
		c1.setName("Java");
		
		when(repo.save(c1)).thenReturn(c1);
		Course c = courseService.save(c1);
		assertEquals("Java", c.getName());
		verify(repo, times(1)).save(c1);
	}

}
