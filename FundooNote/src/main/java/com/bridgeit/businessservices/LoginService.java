package com.bridgeit.businessservices;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.bridgeit.controller.FundooLoginController;
import com.bridgeit.json.TokenGeneration;
import com.bridgeit.model.Login;
import com.bridgeit.model.Register;
import com.bridgeit.utilityservices.JWT;
import com.bridgeit.utilityservices.RedisService;
import com.bridgeit.utilityservices.UserService;

@Service
public class LoginService {
	
	
	@Autowired
	UserService service;
	@Autowired
	RedisService redisService;
	


	Logger logger = Logger.getLogger(LoginService.class);
	
	TokenGeneration tokenobject = new TokenGeneration();
	
	public TokenGeneration loginService(Login user)
	{
		Register reg = service.getUser(user);
		logger.info(reg);
		
		
		if (reg != null) {
			
			/* Validating User Credentials */
			tokenobject.setUserstatus("Valid User");
			logger.info("Login Successful");
			
			/* Token Generation */

			String token = JWT.getToken(reg);
			logger.info(token);

			/* Sending to Redis */
			logger.info("After sending to redis");
			logger.info(reg.getUser_id());
			redisService.sendtokenredis(reg.getUser_id(), token);

			/* Fetching from Redis */
			logger.info("After fetching the value from Redis");
			String redistoken = redisService.gettokenfromredis(reg.getUser_id());
			logger.info("Redis Final generation" + redistoken);
			tokenobject.setToken(token);

			/* Checking Decoding the token and fetching the id */
			int id = JWT.verifyToken(token);
			logger.info(id);
			
			return tokenobject;
	}
		return null;
	
	}
	
}
