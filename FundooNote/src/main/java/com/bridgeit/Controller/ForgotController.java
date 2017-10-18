package com.bridgeit.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bridgeit.Model.Login;
import com.bridgeit.Model.Register;
import com.bridgeit.Services.Email;
import com.bridgeit.Services.UserService;

@Controller
public class ForgotController {
	@Autowired
	UserService service;
	@Autowired(required=true)
	Email email;
	
	private String OTP;
	private String usernameupdation;
	
	@RequestMapping(value="forgot")
	public String forgotpassword()
	{
		return "forgot";
	}
	
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
		String otpfinal=email.sendEmail(reg);
		//mv.addAttribute("otp",otpfinal);
		session.setAttribute("otp", otpfinal);
		request.setAttribute("user",reg.getName());
		System.out.println(otpfinal);
		return "resetpassword";
	}

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
