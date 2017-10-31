package com.bridgeit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bridgeit.businessservices.LoginService;
import com.bridgeit.json.Response;
import com.bridgeit.json.TokenGeneration;
import com.bridgeit.model.Login;
import com.bridgeit.model.Register;
import com.bridgeit.utilityservices.GoogleLogin;
import com.bridgeit.utilityservices.JWT;
import com.bridgeit.utilityservices.RedisService;
import com.bridgeit.utilityservices.UserService;

@Controller
public class FundooLoginController {
	@Autowired
	UserService service;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	LoginService loginService;

	Logger logger = Logger.getLogger(FundooLoginController.class);
	
	HttpHeaders headers = new HttpHeaders();
	
	/*
	 * Controller from JUnit to test the Valid Login.
	 */

	@RequestMapping(value = "fundoologin", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<TokenGeneration> loginuser(@RequestBody Login user) {
		logger.info(user);
		logger.info("In Fundoo Login");

		TokenGeneration tokenobject = loginService.loginService(user);

		if (tokenobject != null) {
			logger.info(tokenobject.getToken());
			headers.add("token", tokenobject.getToken());
			logger.info(headers.toString());
			

			return new ResponseEntity<TokenGeneration>(tokenobject, headers, HttpStatus.OK);
		}
		
		return new ResponseEntity<TokenGeneration>(tokenobject, HttpStatus.BAD_REQUEST);
	}

	
	
	@RequestMapping(value = "log", method = RequestMethod.POST)
	public void lo() {
		System.out.println("Hellooo");
	}

	
	
	/*
	 * Login Controller testing from interface.
	 * 
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView dologinbean() {

		ModelAndView mav = new ModelAndView("login");
		Login loginuser = new Login();
		mav.addObject("loginuser", loginuser);
		logger.info("message");
		System.out.println("Inside home page");
		return mav;

	}

	@RequestMapping(value = "lo", method = RequestMethod.GET)
	public ModelAndView dologinbean1() {

		ModelAndView mav = new ModelAndView("login");
		Login loginuser = new Login();
		mav.addObject("loginuser", loginuser);
		return mav;

	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView dologinbean2(HttpSession session) {
		if (session != null) {
			ModelAndView mav1 = new ModelAndView("welcomelogin");
			// mav.addObject("logins",loginuser);
			return mav1;
		}

		ModelAndView mav = new ModelAndView("login");
		Login loginuser = new Login();
		mav.addObject("loginuser", loginuser);

		return mav;

	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ModelAndView loginuser(@ModelAttribute("loginuser") @PathParam("loginuser") Login loginuser,
			HttpServletRequest request, HttpSession session) {

		System.out.println(loginuser.getEmail());
		// ModelAndView mav=new ModelAndView("welcomelogin","Login",loginuser);
		Register reg = service.getUser(loginuser);
		ModelAndView mav = null;
		if (reg != null) {
			session = request.getSession(true);
			session.setAttribute("session", loginuser);
			System.out.println(session);
			System.out.println(reg.getName() + " " + reg.getPassword() + reg.getUser_id());
			mav = new ModelAndView("welcomelogin", "logins", reg);
			String token = JWT.getToken(reg);
			logger.info(token);
			System.out.println(token);
			logger.info("After sending to redis");
			redisService.sendtokenredis(reg.getUser_id(), token);
			logger.info("After fetching the value from Redis");
			redisService.gettokenfromredis(reg.getUser_id());
		} else {
			mav = new ModelAndView("login", "message", "Invalid Login");
		}

		return mav;
	}


	@RequestMapping(value = "logout")
	public ModelAndView logout(HttpServletRequest request, HttpSession session) {
		ModelAndView mv = new ModelAndView("login");
		mv.addObject("loginuser", new Login());
		session = request.getSession();
		System.out.println(session);
		session.invalidate();
		return mv;
	}

	@RequestMapping(value = "googlelogin")
	public String googlelogin(HttpServletRequest request, HttpSession session) {
		System.out.println("Controller -> googlelogin");
		String code = request.getParameter("code");
		System.out.println(code);
		Register user = GoogleLogin.glogin(code);
		System.out.println("Hi Dude");
		System.out.println(user + " Hi Bro");
		String a = user.getEmail();
		System.out.println(a);
		Register user1 = service.checkUserByEmail(a);
		System.out.println("This is the user after email " + user1);
		session = request.getSession(true);
		if (user1 == null) {
			service.insertuser(user);
			session.setAttribute("session", user);
			request.setAttribute("logins", user);
		} else {
			session.setAttribute("session", user);
			request.setAttribute("logins", user1);
		}

		System.out.println(session);
		return "welcomelogin";
	}

}
