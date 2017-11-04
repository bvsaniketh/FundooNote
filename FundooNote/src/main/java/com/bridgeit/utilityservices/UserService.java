package com.bridgeit.utilityservices;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.bridgeit.dao.UserMapperImpl;
import com.bridgeit.model.Login;
import com.bridgeit.model.Register;




@Service
public class UserService {
	
	@Autowired
	UserMapperImpl mapperimpl;
	
	Logger logger=Logger.getLogger(UserService.class);
	
	public void insertuser(Register user)
	{
		logger.info("insertuser");
		mapperimpl.insertUser(user);
		logger.info("insertuser");
	}
	
	public Register getUser(Login user)
	{
		logger.info("hello");
		Register reg=mapperimpl.getUser(user);
		return reg;
	}
	
	public Register checkUser(String uname)
	{
		Register reg=mapperimpl.checkUser(uname);
		logger.info(reg);
		return reg;
	}
	
	public void updateUser(String password,String usernameupdation)
	{
		//Register reg=
		mapperimpl.updateUser(password,usernameupdation);
		//return reg;
	}
	
	public Register checkUserByEmail(String email)
	{
		Register reg=mapperimpl.checkUserByEmail(email);
		logger.info(reg);
		return reg;
	}
	
}
