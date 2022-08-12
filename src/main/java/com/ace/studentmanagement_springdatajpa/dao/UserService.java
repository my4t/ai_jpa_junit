package com.ace.studentmanagement_springdatajpa.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.studentmanagement_springdatajpa.entity.User;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	public List<User> getAllUser(){
		return userRepo.findAll
				();
	}
	
	public User getUser(int id) {
		return userRepo.findById(id).get();
	}
	
	public User save(User user) {
		return userRepo.save(user);
	}
	
	public void delete(int id) {
		 userRepo.deleteById(id);
	}
	
	public User update(User user) {
		return userRepo.save(user);
	}
	
	public List<User> selectUsersByIdAndName(int id, String name){
		return userRepo.findDintinctUserByIdOrName(id, name);
	}
	
	public User selectUserByEmailAndPassword(String email, String password) {
		return userRepo.findUserByEmailAndPassword(email, password);
	}
	
}
