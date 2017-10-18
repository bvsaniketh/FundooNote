package com.bridgeit.Model;

public class Register {
	
	//@Size(min=2,max=30)
	
	private int user_id;
	private String name;
	private int mobile;
	private String email;
	private String password;
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getMobile() {
		return mobile;
	}
	public void setMobile(int mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Register [user_id=" + user_id + ", name=" + name + ", mobile=" + mobile + ", email=" + email
				+ ", password=" + password + "]";
	}
	
	

	
	
	

}
