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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bridgeit.jms.SpringJmsConsumer;
import com.bridgeit.jms.SpringJmsProducer;
import com.bridgeit.json.Response;
import com.bridgeit.model.Login;
import com.bridgeit.model.Register;
import com.bridgeit.services.Email;
import com.bridgeit.services.EmailScheduler;
import com.bridgeit.services.UserService;
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
	
	private String OTP;
	private String usernameupdation;

	private Logger logger = Logger.getLogger(FundooRegisterController.class);
	ObjectMapper mapperObj = new ObjectMapper();
	
	/* Mapping for fundooregister */

	@RequestMapping(value = "fundooregister", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
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
			return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST); // Case for invalid details
		}
		if (service.checkUserByEmail(user.getEmail()) != null) {
			logger.info("email already exists");
			resp = new Response();
			resp.setStatus(-1);
			resp.setMessage("User already exist");
			return new ResponseEntity<Response>(resp, HttpStatus.CONFLICT); // Case for duplication of user details
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

			return new ResponseEntity<Response>(HttpStatus.OK); // Case for new user registration
		} catch (Exception e) {
			logger.info("EXCEPTION OCCURED");
			resp = new Response();
			resp.setStatus(-1);
			resp.setMessage("Internal server error");
			return new ResponseEntity<Response>(HttpStatus.INTERNAL_SERVER_ERROR); // Case for exception
		}

	}

	// Testing API for Mapping
	@RequestMapping(value = "test")
	public void Test() {
		System.out.println("TEst");

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
		System.out.println(user);
		// ModelAndView mav=new ModelAndView("login","Registration",user);
		// System.out.println(mav);
		// impl.insertUser(user);

		formvalid.validate(user, res);

		if (res.hasErrors())

		{
			return "register";
		}
		service.insertuser(user);
		Gson gson = new Gson();
		String userjson = gson.toJson(user);
		System.out.println(userjson + "This is the user's json");
		ma.addAttribute("reg", "User has been Registered sucessfully");
		jms.sendMessage(userjson);
		System.out.println("Receiving from Queue jms");
		String json;
		try {
			json = jms1.receiveMessage();
			System.out.println(json);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(modelmap);
		// ModelAndView mav=new ModelAndView

		// return "redirect:/" ;
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
