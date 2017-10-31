package com.bridgeit.utilityservices;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.bridgeit.model.GoogleProfile;
import com.bridgeit.model.GoogleToken;

public class GoogleLoginByRest {

	private static Logger logger=Logger.getLogger(GoogleLoginByRest.class);
	private static String sScope="email";
	private static String sGmail_CLIENT_ID="948035486059-ha0v7orrbp2btbbs0lo0g55h40gah113.apps.googleusercontent.com";
	private static String sGmail_CLIENT_SECRET="qlK2HmoElrfyBrcwTlHDZJGC";
	private static String sGmail_REDIRECT_URI="/postGoogle";
	
	private static String sGmail_URL= "https://accounts.google.com/o/oauth2/v2/auth?client_id=%s&redirect_uri=%s&state=%s&"
			+ "response_type=code&scope=%s&approval_prompt=force&access_type=offline";
	
	private static String sGmail_ACCESS_TOKEN_URL="https://www.googleapis.com/oauth2/v4/token";
	
	private static String sGmail_GET_USER_URL = "https://www.googleapis.com/plus/v1/people/me";

	
	public static String getGmailUrl(String apiRedirectUrl, String stateCode) {
		
		System.out.println("Inside Gmail URL");
		apiRedirectUrl=apiRedirectUrl+sGmail_REDIRECT_URI;
		
		return new String().format(sGmail_URL, new String[] {sGmail_CLIENT_ID,apiRedirectUrl,stateCode,sScope});
		
	}

	public static GoogleProfile authUser(String authcode, String apiRedirectUrl) {
		
		System.out.println("Inside to get user profile");
		String accessToken=getAcessToken(authcode,apiRedirectUrl);
		System.out.println(authcode +" "+apiRedirectUrl +" " +accessToken);
		return getuserProfile(accessToken);
	}
	


	private static GoogleProfile getuserProfile(String accessToken) {
		
		logger.warn("Inside gmail for user profile");
		String accProfileUrl=sGmail_GET_USER_URL;
		
		ResteasyClient client =new ResteasyClientBuilder().build();
		ResteasyWebTarget target=client.target(accProfileUrl);
		
		String headerAuth="Bearer " +accessToken;
		Response response=target.request().header("Authorization", headerAuth).accept(MediaType.APPLICATION_JSON).get();
		//String profile=response.readEntity(String.class);
		GoogleProfile profile=response.readEntity(GoogleProfile.class);
		logger.warn(profile);
		client.close();
		return profile;
		
		
	}

	private static String getAcessToken(String authcode, String apiRedirectUrl) {
		
		logger.warn("Inside gmail for Access token");
		apiRedirectUrl=apiRedirectUrl+sGmail_REDIRECT_URI;
		String accTokenUrl=sGmail_ACCESS_TOKEN_URL;
		
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target=client.target(accTokenUrl);
		System.out.println("here");
		
		Form form=new Form();
		form.param("client_id",sGmail_CLIENT_ID);
		form.param("client_secret",sGmail_CLIENT_SECRET);
		form.param("redirect_uri", apiRedirectUrl);
		form.param("code", authcode);
		form.param("grant_type", "authorization_code");
		
		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(form));
		GoogleToken token=response.readEntity(GoogleToken.class);
		logger.warn(token);
		client.close();
		return token.getAccess_token();
		
		
		
	}
}
