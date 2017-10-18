package com.bridgeit.Controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bridgeit.JSON.Response;
import com.bridgeit.JSON.TokenGeneration;
import com.bridgeit.Model.Login;
import com.bridgeit.Model.Register;
import com.bridgeit.Services.JWT;
import com.bridgeit.Services.RedisService;
import com.bridgeit.Services.UserService;

@Controller
public class FundooLoginController {
	@Autowired
	UserService service;
	@Autowired
	RedisService redisservice;
	
	Logger logger=Logger.getLogger(FundooLoginController.class);
/*	String CLIENT_ID="12345";
	final static String CLIENT_SECRET="abcdefghijklmnopqrstuvwxyz";*/
	
	@RequestMapping(value="fundoologin",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<TokenGeneration> loginuser(@RequestBody Login user1)
	{
		System.out.println(user1);
		System.out.println("ashdbabd");
		Register reg=service.getUser(user1);
		logger.info(reg);
		TokenGeneration tokenobject=new TokenGeneration();
		if(reg!=null)
		{
			 /*Validating User Credentials */
			 tokenobject.setUserstatus("Valid User");
			 logger.info("Login Successfull");

			 /*Token Generation*/
			 
			 String token=JWT.getToken(reg);
			 logger.info(token);
			 
			 /*Sending to Redis*/
			 logger.info("After sending to redis");
			 logger.info(reg.getUser_id());
			 redisservice.sendtokenredis(reg.getUser_id(), token);
			 
			 /*Fetching from Redis*/
			 logger.info("After fetching the value from Redis");
			 String redistoken= redisservice.gettokenfromredis(reg.getUser_id());
			 logger.info("Redis Final generation" + redistoken);
			 tokenobject.setToken(token);
			 
			 /*Setting Headers*/
			 HttpHeaders headers=new HttpHeaders();
			 headers.add("token",token);
			 logger.info(headers.toString());
			 
			 /*Checking Decoding the token and fetching the id*/
			 int id=JWT.verifyToken(token);
			 logger.info(id);
			 
			return new ResponseEntity<TokenGeneration>(tokenobject, headers, HttpStatus.OK);
		}
		return new ResponseEntity<TokenGeneration>(tokenobject,HttpStatus.BAD_REQUEST);
		 
	}
	
	@RequestMapping(value="log",method=RequestMethod.POST )
	public void lo()
	{ 
		System.out.println("Hellooo");
	}
	
}
