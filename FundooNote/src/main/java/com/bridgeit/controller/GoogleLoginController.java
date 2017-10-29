package com.bridgeit.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bridgeit.model.GoogleProfile;
import com.bridgeit.model.Register;
import com.bridgeit.services.GoogleLoginByRest;
import com.bridgeit.services.UserService;

@Controller
public class GoogleLoginController 
{
	@Autowired
	UserService service;
	
	private static Logger logger=Logger.getLogger(GoogleLoginController.class);
	@RequestMapping(value="/loginG")
	public void SocialLogin(HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("Inside login with Google");
		String lsr=request.getRequestURL().toString();
		System.out.println(lsr);
		String apiRedirectUrl=lsr.substring(0,lsr.lastIndexOf('/'));
		System.out.println(apiRedirectUrl);
		String stateCode=UUID.randomUUID().toString();
		System.out.println(stateCode);
		System.out.println(request.getSession());
		request.getSession().setAttribute("STATE", stateCode);
		String gmailUrl=GoogleLoginByRest.getGmailUrl(apiRedirectUrl,stateCode);
		System.out.println(gmailUrl);
		
		try {
			response.sendRedirect(gmailUrl);
			System.out.println("Redirected successfully");
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value="/postGoogle")
	public String PostSocialLogin(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException
	{
//		if(session!=null)
//		{
//			response.sendRedirect("welcomelogin");
//		}
		System.out.println("In post google");
		String sessioncheck=(String) request.getSession().getAttribute("STATE");
		String statecode=request.getParameter("state");
		if(sessioncheck==null||!sessioncheck.equals(statecode))
		{
			try {
				response.sendRedirect("loginG");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String error = request.getParameter("error");
		
		if(error != null && error.trim().isEmpty()) {
			
			logger.error(error);
			System.out.println("Error is present here");
			response.sendRedirect("login");
		}
		String authcode=request.getParameter("code");
		System.out.println(authcode);
		
		String lsr=request.getRequestURL().toString();
		String apiRedirectUrl=lsr.substring(0,lsr.lastIndexOf('/'));
		GoogleProfile profile=GoogleLoginByRest.authUser(authcode,apiRedirectUrl);
		//System.out.println(profile);
		
		String email = profile.getEmails().get(0).getValue();
		
		Register user = service.checkUserByEmail(email);
		
		System.out.println(profile);
		if(user == null) {
			user = new Register();
			String name = profile.getDisplayName();
			user.setName(name);
			user.setEmail(email);
			user.setPassword("123");
			//user.setUsername(name.substring(0, name.lastIndexOf(" ")));
			service.insertuser(user);
			request.setAttribute("logins", user);
			session.setAttribute("session", user);	
		}
		request.setAttribute("logins", user);
		session.setAttribute("session", user);	
		return "welcomelogin";
		//response.sendRedirect("welcomelogin");
		
	}
}
