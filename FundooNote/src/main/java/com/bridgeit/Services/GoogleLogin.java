package com.bridgeit.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import com.bridgeit.Model.Register;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class GoogleLogin {
	
	@Autowired
	UserService service;
	
	private static Logger logger=Logger.getLogger(GoogleLogin.class);
	
	public static Register glogin(String code)
	{
		Register user=new Register();
		try {
			String urlParameters="code="+code+"&client_id=948035486059-ha0v7orrbp2btbbs0lo0g55h40gah113.apps.googleusercontent.com"
					+"&client_secret=qlK2HmoElrfyBrcwTlHDZJGC"+"&redirect_uri=http://localhost:8080/MVCLogin2/googlelogin"+"&grant_type=authorization_code";
			logger.warn("inside");
		
		
			URL url = new URL("https://accounts.google.com/o/oauth2/token");
			URLConnection con = url.openConnection();
			con.setDoOutput(true);
			OutputStreamWriter writer=new OutputStreamWriter(con.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			
			String line,outputstring="";
			BufferedReader reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
			while((line=reader.readLine())!=null)
			{
				outputstring+=line;
			}
			logger.warn(outputstring +" Hi Output ");
			
			
			JsonObject json=(JsonObject) new JsonParser().parse(outputstring);
			String access_token=json.get("access_token").getAsString();
			logger.info(access_token);
			
			//Display User Info
			url=new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token="+access_token);
			con= url.openConnection();
			outputstring="";
			reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
			while((line=reader.readLine())!=null)
			{
				outputstring+=line;
			}
			System.out.println(outputstring);
			
			user= new Gson().fromJson(outputstring, Register.class);
			System.out.println("The details are as follows "+ user);
			
			writer.close();
			reader.close();
			
			 
			
			
			
			//
		} catch (MalformedURLException e) {
			logger.error(e);
		}
		 catch (IOException e) {
			logger.error(e);
		}
		return user;
	}
}
