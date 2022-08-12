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

import com.ace.studentmanagement_springdatajpa.dao.UserRepository;
import com.ace.studentmanagement_springdatajpa.dao.UserService;
import com.ace.studentmanagement_springdatajpa.entity.User;

@SpringBootTest
public class UserServiceTest {
	@Mock
	UserRepository repo;
	
	@InjectMocks
	UserService userService;
	

	@Test
	public void getAllUsersTest() {
		List<User> users = new ArrayList<User>();
		User usr1 = new User();
		usr1.setId(1);
		usr1.setEmail("bob@gmail.com");
		usr1.setName("Bob");
		usr1.setPassword("pass");
		usr1.setRole("User");
		User usr2 = new User();
		usr2.setId(2);
		usr2.setEmail("alisa@gmail.com");
		usr2.setName("Alisa");
		usr2.setPassword("pass123");
		usr2.setRole("User");
		User usr3 = new User();
		usr3.setId(3);
		usr3.setEmail("john@gmail.com");
		usr3.setName("John");
		usr3.setPassword("abc");
		usr3.setRole("Admin");
		
		users.add(usr3);
		users.add(usr2);
		users.add(usr1);
		
		when(repo.findAll()).thenReturn(users);
		List<User> userList = userService.getAllUser();
		assertEquals(3, userList.size());
		verify(repo, times(1)).findAll();
	}
	
	@Test
	public void getUserTest() {
		Optional<User> usr1 = Optional.of(new User());

		usr1.get().setId(2);
		usr1.get().setEmail("bob@gmail.com");
		usr1.get().setName("Bob");
		usr1.get().setPassword("pass");
		usr1.get().setRole("User");
		when(repo.findById(1)).thenReturn(usr1);
		User retUser = userService.getUser(1);
		assertEquals("bob@gmail.com", retUser.getEmail());
		verify(repo, times(1)).findById(1);
	}
	
	@Test
	public void saveTest() {
		User usr3 = new User();
		usr3.setId(3);
		usr3.setEmail("john@gmail.com");
		usr3.setName("John");
		usr3.setPassword("abc");
		usr3.setRole("Admin");
		when(repo.save(usr3)).thenReturn(usr3);
		User user = userService.save(usr3);
		assertEquals("John", user.getName());
		verify(repo, times(1)).save(usr3);
		
	}
	
	@Test
	public void updateTest() {
		User usr3 = new User();
		usr3.setId(3);
		usr3.setEmail("john@gmail.com");
		usr3.setName("John");
		usr3.setPassword("abc");
		usr3.setRole("Admin");
		when(repo.save(usr3)).thenReturn(usr3);
		User user = userService.update(usr3);
		assertEquals("John", user.getName());
		verify(repo, times(1)).save(usr3);
		
	}
	
	@Test
	public void deleteTest() {
		userService.delete(1);
		verify(repo, times(1)).deleteById(1);
	}
	
	@Test
	public void searchTest() {
		List<User> users = new ArrayList<User>();
		User usr1 = new User();
		usr1.setId(1);
		usr1.setEmail("bob@gmail.com");
		usr1.setName("Bob");
		usr1.setPassword("pass");
		usr1.setRole("User");
		User usr2 = new User();
		usr2.setId(2);
		usr2.setEmail("alisa@gmail.com");
		usr2.setName("Alisa");
		usr2.setPassword("pass123");
		usr2.setRole("User");
		User usr3 = new User();
		usr3.setId(3);
		usr3.setEmail("john@gmail.com");
		usr3.setName("John");
		usr3.setPassword("abc");
		usr3.setRole("Admin");
		
		users.add(usr3);
		users.add(usr2);
		users.add(usr1);
		
		when(repo.findDintinctUserByIdOrName(1, "test")).thenReturn(users);
		List<User> userList = userService.selectUsersByIdAndName(1, "test");
		assertEquals(3, userList.size());
		verify(repo, times(1)).findDintinctUserByIdOrName(1, "test");
	}
	
	@Test
	public void loginTest() {
		
		User usr1 = new User();
		usr1.setId(1);
		usr1.setEmail("bob@gmail.com");
		usr1.setName("Bob");
		usr1.setPassword("1337");
		usr1.setRole("User");
		
		when(repo.findUserByEmailAndPassword("test@gmail.com", "1337")).thenReturn(usr1);
		User user = userService.selectUserByEmailAndPassword("test@gmail.com", "1337");
		assertEquals("1337",user.getPassword());
		verify(repo, times(1)).findUserByEmailAndPassword("test@gmail.com", "1337");
	}
}
