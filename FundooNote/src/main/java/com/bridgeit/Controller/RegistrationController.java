package com.bridgeit.Controller;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bridgeit.DAO.UserMapperImpl;
import com.bridgeit.JMS.SpringJmsConsumer;
import com.bridgeit.JMS.SpringJmsProducer;
import com.bridgeit.Model.Login;
import com.bridgeit.Model.Register;
import com.bridgeit.Services.UserService;
import com.bridgeit.Validators.FormValidator;
import com.google.gson.Gson;

@Controller
public class RegistrationController {
	//UserMapperImpl impl=new UserMapperImpl();
	@Autowired
	UserService service;
	@Autowired
	FormValidator formvalid;
	
	@Autowired
	SpringJmsProducer jms;
	@Autowired
	SpringJmsConsumer jms1;
	
	@RequestMapping(value="Register",method=RequestMethod.GET)
	public ModelAndView dologinbean1()
	{
		
		ModelAndView mav=new ModelAndView("register");
		Register user1=new Register();
		mav.addObject("user1",user1);
		return mav;
		
	}

	
	@RequestMapping(value="register",method=RequestMethod.POST)
	public String adduser(@ModelAttribute("user1") Register user,BindingResult res,ModelMap ma)
	{
		 System.out.println(user);
		// ModelAndView mav=new ModelAndView("login","Registration",user);
		// System.out.println(mav);
		// impl.insertUser(user);
		 
		 formvalid.validate(user, res);
		 
		 if(res.hasErrors())
			 
		 {
			 return "register";
		 }
		 service.insertuser(user);
		 Gson gson=new Gson();
		 String userjson=gson.toJson(user);
		 System.out.println(userjson +"This is the user's json");
		 ma.addAttribute("reg","User has been Registered sucessfully");
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
		
//		 System.out.println(modelmap);
		 //ModelAndView mav=new ModelAndView
		 
		 //return "redirect:/" ;
		 return "register";
	}
	
	
	
	
}
