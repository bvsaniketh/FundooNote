package com.bridgeit.utilityservices;

import org.apache.log4j.Logger;

import com.bridgeit.model.Register;

public class EmailScheduler implements Runnable{

	Register user;
	Email email;
	
	static Logger logger=Logger.getLogger(EmailScheduler.class);
	
	public EmailScheduler(Register user, Email email) {
		this.user = user;
		this.email = email;
	}


	//@Override
	public void run() {
		logger.info("Sending Email");
		email.sendEmailsuccess(user);
	}

}
