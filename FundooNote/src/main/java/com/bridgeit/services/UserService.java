package com.bridgeit.services;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.bridgeit.dao.UserMapperImpl;
import com.bridgeit.model.Login;
import com.bridgeit.model.Register;




@Service
public class UserService {
	
	@Autowired
	UserMapperImpl mapperimpl;
	
	public void insertuser(Register user)
	{
		System.out.println("insertuser");
		mapperimpl.insertUser(user);
		System.out.println("insertuser");
	}
	
	public Register getUser(Login user)
	{
		System.out.println("hello");
		Register reg=mapperimpl.getUser(user);
		return reg;
	}
	
	public Register checkUser(String uname)
	{
		Register reg=mapperimpl.checkUser(uname);
		System.out.println(reg);
		return reg;
	}
	
	public void updateUser(String password,String usernameupdation)
	{
		//Register reg=
		mapperimpl.updateUser(password,usernameupdation);
		System.out.println();
		//return reg;
	}
	
	public Register checkUserByEmail(String email)
	{
		Register reg=mapperimpl.checkUserByEmail(email);
		System.out.println(reg);
		return reg;
	}
	
}
