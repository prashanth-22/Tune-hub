package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Users;
import com.example.demo.repositories.UsersRepository;

@Service
public class UsersServiceImplementation implements UsersService
{
   @Autowired
	UsersRepository usrepo;
   
	@Override
	public String addUsers(Users user) {
		usrepo.save(user);
		return "User Added Succesfully";
	}

	@Override
	public boolean emailExists(String email) {
		if(usrepo.findByEmail(email)==null) {
			return false;
		}
		else
		{
			return true;
		}
		
	}

	@Override
	public boolean validateUser(String email, String password) {
      	Users user=	usrepo.findByEmail(email);
      	String db_pass=user.getPassword();
      	
      	if(password.equals(db_pass)) {
      		return true;
      	}
      	else {
		return false;
	}
	}

	@Override
	public String getRole(String email) {
		Users user=usrepo.findByEmail(email);
		return user.getRole();
	}

	@Override
	public Users getUser(String email) {
		 return usrepo.findByEmail(email);
	}

	@Override
	public void updateUser(Users user) {
		usrepo.save(user);
		
	}

	
}
