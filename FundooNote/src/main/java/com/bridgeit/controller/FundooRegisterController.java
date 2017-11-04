package com.bridgeit.controller;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bridgeit.businessservices.RegisterService;
import com.bridgeit.jms.SpringJmsConsumer;
import com.bridgeit.jms.SpringJmsProducer;
import com.bridgeit.json.Response;
import com.bridgeit.model.Login;
import com.bridgeit.model.Register;
import com.bridgeit.utilityservices.Email;
import com.bridgeit.utilityservices.EmailScheduler;
import com.bridgeit.utilityservices.UserService;
import com.bridgeit.validators.FormValidator;
import com.google.gson.Gson;

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

	@Autowired
	SpringJmsProducer jms;
	@Autowired
	SpringJmsConsumer jms1;
	
	@Autowired
	RegisterService regService;
	
	
	private String OTP;
	private String usernameupdation;

	private Logger logger = Logger.getLogger(FundooRegisterController.class);
	ObjectMapper mapperObj = new ObjectMapper();
	
	/* Mapping for fundooregister */

	@RequestMapping(value = "fundooregister", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody ResponseEntity<Response> insertUser(@RequestBody Register user, BindingResult bindResult) {
		
		logger.info("Entered insertUser()");
		Response resp=regService.registerService(user, bindResult);
		logger.info("Entered after registerService in insertUser()" + resp);
		
		if(resp.getStatus()==-1)
			return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
		if(resp.getStatus()==20)
			return new ResponseEntity<Response>(resp, HttpStatus.CONFLICT);
		if(resp.getStatus()==1)
			return new ResponseEntity<Response>(HttpStatus.OK); 
			
		return new ResponseEntity<Response>(HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// Testing API for Mapping
	@RequestMapping(value = "test")
	public void Test() {
		logger.info("TEst");

	}

	/*
	 * Register Controller from the interface
	 * 
	 * 
	 */

	@RequestMapping(value = "Register", method = RequestMethod.GET)
	public ModelAndView dologinbean1() {

		ModelAndView mav = new ModelAndView("register");
		Register user1 = new Register();
		mav.addObject("user1", user1);
		return mav;

	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String adduser(@ModelAttribute("user1") Register user, BindingResult res, ModelMap ma) {
		logger.info(user);
		
		formvalid.validate(user, res);

		if (res.hasErrors())

		{
			return "register";
		}
		service.insertuser(user);
		Gson gson = new Gson();
		String userjson = gson.toJson(user);
		logger.info(userjson + "This is the user's json");
		ma.addAttribute("reg", "User has been Registered sucessfully");
		jms.sendMessage(userjson);
		logger.info("Receiving from Queue jms");
		String json;
		try {
			json = jms1.receiveMessage();
			logger.info(json);
		} catch (JMSException e) {
			
			e.printStackTrace();
		}


		return "register";
	}
	
	
	/* Forgot Password Controller
	 * 
	 */
	 

	
	
	@RequestMapping(value="forgot")
	public String forgotpassword()
	{
		return "forgot";
	}
	
	
	/*Reset the password functionality*/
	
	@RequestMapping(value="reset")
	public String resetpassword(ModelMap mv,HttpServletRequest request,HttpSession session)
	{
		String uname=request.getParameter("name");
		Register reg=service.checkUser(uname);
		if(reg==null)
		{
			mv.addAttribute("message", "Invalid User, Not found in the database");
			System.out.println("abc");
			return "forgot";
			
		}
		usernameupdation=reg.getName();
		System.out.println(reg);
		System.out.println(request.getParameter("name"));
		
		/*Send the OTP if valid credentials are entered*/
		
		String otpfinal=email.sendEmail(reg);
		//mv.addAttribute("otp",otpfinal);
		session.setAttribute("otp", otpfinal);
		request.setAttribute("user",reg.getName());
		System.out.println(otpfinal);
		return "resetpassword";
	}
	
	/*Validating OTP*/
	
	@RequestMapping(value="otp")
	public String otp(ModelMap map,HttpServletRequest request,HttpSession session)
	{
		String var=(String) session.getAttribute("otp");
		System.out.println(var);
		String var1=request.getParameter("OTP");
		//String usernameform=request.getParameter("userhidden");
		System.out.println(var1 +" "+ "User typed");
		if(var.equals(var1))
		{
		return "otp";
		}
		
		map.addAttribute("Invalid","The OTP entered is invalid" );
		return "resetpassword";
	}
	
	/*Changing Password mapping */
	
	@RequestMapping(value="changepassword")
	public ModelAndView changepassword(HttpServletRequest request,HttpSession session)
	{
		String password=request.getParameter("password");
		String confirmpassword=request.getParameter("passwordconfirm");
		
		if(password.equals(confirmpassword))
		{
			ModelAndView mode=new ModelAndView("login");
			service.updateUser(password,usernameupdation);
			System.out.println("After Updation of the password details" + " " );
			mode.addObject("loginuser",new Login());
			mode.addObject("passwordupdated","Password has been updated,Login now");
			return mode;
		}
		ModelAndView mode=new ModelAndView("otp");
		mode.addObject("passwordupdationinvalid","Password Updation not done successfully");
		return mode;
	}

}
