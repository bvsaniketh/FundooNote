package com.bridgeit.dao;

import com.bridgeit.model.Login;
import com.bridgeit.model.Register;

public interface UserMapper {

	public void insertUser(Register user);
	public Register getUser(Login user);
	public Register checkUser(String email);
	public void updateUser(String password,String usernameupdation);
	public Register checkUserByEmail(String email);
}
