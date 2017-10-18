/*package com.bridgeit.Services;

import java.security.Key;
import java.util.Date;
import java.util.Random;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;



import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWT_TokenService {

	private final static Logger log = Logger.getLogger(JWT_TokenService.class);
	
	public String generateToken(String client_ID) {
		
		String token = null;
		long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	 
	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	    	 
	    //Let's set the JWT Claims
	    JwtBuilder builder = Jwts.builder().setId("123421123")
	                                .setIssuedAt(now)
	                                .setSubject("My JWT Token")
	                                .setIssuer("BMSCorp")
	                                .claim("Name", "BMS")
	                                .claim("Age", 23)
	                                .claim("Location", "Mumbai")
	                                .signWith(SignatureAlgorithm.HS256, client_ID);
	 
	    long expTime = 36000;
		//if it has been specified, let's add the expiration
	    if (expTime  >= 0) {
	    long expMillis = nowMillis + expTime;
	        Date exp = new Date(expMillis);
	        builder.setExpiration(exp);
	    }
		token = builder.compact();
		
		log.warn("Generated Token: "+token);
		
		return token;
	}
	

}
*/