package com.bridgeit.Services;

import com.bridgeit.Model.Register;

public class EmailScheduler implements Runnable{

	Register user;
	Email email;
	
	
	public EmailScheduler(Register user, Email email) {
		this.user = user;
		this.email = email;
	}


	@Override
	public void run() {
		System.out.println("Sending Email");
		email.sendEmailsuccess(user);
	}

}
