package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Users;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {
	
	@Autowired
	UsersService service;
	
	@PostMapping("/register")
	public String addUsers(@ModelAttribute Users user )
	{
		
		boolean userStatus=service.emailExists(user.getEmail());
		
		if(userStatus==false) 
		{
			service.addUsers(user);
			System.out.println("User added");
		}
		else 
		{
			System.out.println("User already Exists");
		}
		return "home";
		
	}
	
	
		@PostMapping("/validate")
		public String validate(@RequestParam("email") String email,
				@RequestParam("password") String password,
				HttpSession session)//Accepting the Session as a Parameter
		{
			
			if(service.validateUser(email,password)==true) {
				String role=service.getRole(email);
				
				//Setting the Attribute Using session Object and Storing an email entered by the user to instance Variable email
				session.setAttribute("email", email);
				
				if(role.equals("admin")) {
					return "adminHome";
				}
				else {
					return "customerHome";
				}
		
			}
			else {
				return "login";
			}
		}
		
	
		
		@GetMapping("/logout")
		public String logout(HttpSession session) {
			
			//Getting the Session Object and Invalidate the session in order Logout(Means Session Object Will be Deleted
			session.invalidate();
			return "login";
		}
		
	

}
