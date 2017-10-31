package com.bridgeit.businessservices;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.bridgeit.controller.FundooRegisterController;
import com.bridgeit.jms.SpringJmsConsumer;
import com.bridgeit.jms.SpringJmsProducer;
import com.bridgeit.json.Response;
import com.bridgeit.model.Register;
import com.bridgeit.utilityservices.Email;
import com.bridgeit.utilityservices.EmailScheduler;
import com.bridgeit.utilityservices.UserService;
import com.bridgeit.validators.FormValidator;

@Service
public class RegisterService {
	
	@Autowired
	UserService service;
	@Autowired
	FormValidator formvalid;

	@Autowired
	Email email;
	
	
	@Autowired
	TaskExecutor taskExecutor;

	@Autowired
	SpringJmsProducer jms;
	@Autowired
	SpringJmsConsumer jms1;

	Response resp;
	
	ObjectMapper mapperObj = new ObjectMapper();
	private Logger logger = Logger.getLogger(FundooRegisterController.class);
	
	public Response registerService(Register user,BindingResult bindResult)
	{
		logger.info("Entered RegisterService()");
		
		formvalid.validate(user, bindResult);
		
		// Case for invalid details
		if (bindResult.hasErrors()) {
			logger.info("has errors while validating");
			logger.info(bindResult.getFieldErrors().toString());
			resp = new Response();
			resp.setStatus(-1);
			resp.setMessage("Entered invalid details");
			return resp;									
		}
		if (service.checkUserByEmail(user.getEmail()) != null) {
			logger.info("email already exists");
			resp = new Response();
			resp.setStatus(20);
			resp.setMessage("User already exist");
			return resp;
			 // Case for duplication of user details
		}

		try {
			service.insertuser(user);
			logger.info("registered");
			taskExecutor.execute(new EmailScheduler(user, email));
			logger.info("Registered successfully");
			resp = new Response();
			resp.setStatus(1);
			resp.setMessage("User registered successfully!!!");
			String jsonStr = mapperObj.writeValueAsString(user);
			System.out.println(jsonStr + " After converting to json from Java Object");
			return resp;
			 // Case for new user registration
		} catch (Exception e) {
			logger.info("EXCEPTION OCCURED");
			resp = new Response();
			resp.setStatus(500);
			resp.setMessage("Internal server error");
			return resp;
			 // Case for exception
		}

	
	
	}
}
