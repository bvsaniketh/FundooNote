package com.bridgeit.Controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bridgeit.JSON.Response;
import com.bridgeit.Model.Register;
import com.bridgeit.Services.Email;
import com.bridgeit.Services.EmailScheduler;
import com.bridgeit.Services.UserService;
import com.bridgeit.Validators.FormValidator;


@Controller
public class FundooRegisterController {

	@Autowired
	UserService service;
	@Autowired
	FormValidator formvalid;
	
	@Autowired
	Email email;
	@Autowired
	TaskExecutor taskExecutor;
	
	private Logger logger=Logger.getLogger(FundooRegisterController.class);
	ObjectMapper mapperObj=new ObjectMapper();
	/*Mapping for fundooregister*/
	
	@RequestMapping(value = "fundooregister", method = RequestMethod.POST, consumes="application/json", produces="application/json")
	public @ResponseBody ResponseEntity<Response> insertUser(@RequestBody Register user, BindingResult bindResult) {
        System.out.println("Entered insertUser()");
		logger.info("Entered insertUser()");
		Response resp;
		formvalid.validate(user, bindResult);
		if (bindResult.hasErrors()) {
			logger.info("has errors while validating");
			logger.info(bindResult.getFieldErrors().toString());
			resp = new Response();
			resp.setStatus(-1);
			resp.setMessage("Entered invalid details");
			return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST); //Case for invalid details
		}
		if (service.checkUserByEmail(user.getEmail()) != null) {
			logger.info("email already exists");
			resp = new Response();
			resp.setStatus(-1);
			resp.setMessage("User already exist");
			return new ResponseEntity<Response>(resp, HttpStatus.CONFLICT); //Case for duplication of user details
		}

		
		
		try {
			service.insertuser(user);
			logger.info("registered");
			taskExecutor.execute(new EmailScheduler(user,email));
			logger.info("Registered successfully");
			resp = new Response();
			resp.setStatus(1);
			resp.setMessage("User registered successfully!!!");
			String jsonStr=mapperObj.writeValueAsString(user);
			System.out.println(jsonStr + " After converting to json from Java Object");
			
			return new ResponseEntity<Response>(HttpStatus.OK); //Case for new user registration
		} catch (Exception e) {
			logger.info("EXCEPTION OCCURED");
			resp = new Response();
			resp.setStatus(-1);
			resp.setMessage("Internal server error");
			return new ResponseEntity<Response>(HttpStatus.INTERNAL_SERVER_ERROR); //Case for exception
		}
		
	}
	
	//Testing API for Mapping
	@RequestMapping(value="test" )
	public void Test()
	{
		System.out.println("TEst");
		
	}
	
}
