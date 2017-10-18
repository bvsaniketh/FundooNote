package com.bridgeit.Filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bridgeit.Services.JWT;
import com.bridgeit.Services.RedisService;

/**
 * Servlet Filter implementation class TokenAuth
 */
public class TokenAuth implements Filter {

	
	//RedisService redisservice;
	
	
   
    public TokenAuth() {
        
    }

    public void init(FilterConfig fConfig) throws ServletException {
    	
    	System.out.println("Hello Filter" + "dgsdgs");
		
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{

		System.out.println("Hello Filter");
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		String token=req.getHeader("token");
		
		try
		{
		System.out.println(token);
		int tokenuserid=JWT.verifyToken(token);
		Date expdate=JWT.verifyTokenDate(token);
		System.out.println(tokenuserid);
		System.out.println(expdate +" is the expiry date");
		String tokenfromredis=RedisService.gettokenfromredis(tokenuserid);
		System.out.println(tokenfromredis);
		
		
		if(token.equals(tokenfromredis))
		{
			if(new Date().before(expdate))
			{
				System.out.println("Yipeeeeeeeeeeee");
				chain.doFilter(request, response);
				System.out.println("Okay out of bounds I guess");
			}
		}
		
		}catch(io.jsonwebtoken.ExpiredJwtException e)
		{
			System.out.println(e);
			System.out.println("Please Login Again");
			resp.sendRedirect("/login");
		}
	}
	


	

	public void destroy() {
		
		// TODO Auto-generated method stub
	}

}
