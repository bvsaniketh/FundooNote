/*package com.bridgeit.Controller;

import java.util.Random;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bridgeit.Model.Register;
import com.bridgeit.Services.JWT_TokenService;
import com.bridgeit.Services.RedisService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Controller
public class NavigationController {

	private final static Logger log = Logger.getLogger(NavigationController.class); 
	final static String CLIENT_ID = "123123123";
	final static String CLIENT_SECRET_ID = "zxckjnafjkdsnjklqwrek";
	
	@Autowired
	JWT_TokenService jwtService;

	@Autowired
	RedisService redis;
	
	@RequestMapping(value="Register",method=RequestMethod.GET)
	public ModelAndView doregistrationbean()
	{
		ModelAndView mav =new ModelAndView("register");
		mav.addObject("user1",new Register());
		System.out.println(mav);
		return mav;
	}
	
	@RequestMapping(value = "Register1", method = RequestMethod.GET)
	public void gotoreg() {

		ModelAndView mav = new ModelAndView("register");
		log.warn("Gone to Registration");
	}

	@RequestMapping(value = "Login", method = RequestMethod.GET)
	public void gotologin() {
		ModelAndView mav = new ModelAndView("login");
		log.warn("Gone to Login");

	}

	@RequestMapping("getToken")
	public void getToken() {
		
		String token = jwtService.generateToken(CLIENT_SECRET_ID);
		redis.insertToken(CLIENT_ID,token);
		
		String tokenFromRedis = redis.RetreiveTokenFromRedis(CLIENT_ID);
		
		if(token.equals(tokenFromRedis))
			System.out.println("Valid");
		else
			System.out.println("InValid");
		

		Claims claims = Jwts.parser()         
			       .setSigningKey(CLIENT_SECRET_ID)
			       .parseClaimsJws(tokenFromRedis).getBody();
		
		System.out.println(claims.get("Name"));
		System.out.println(claims.get("Age"));
		System.out.println(claims.get("Location"));
		
	}
}
*/