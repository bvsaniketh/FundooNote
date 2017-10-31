package com.bridgeit.utilityservices;

import java.util.Date;

import org.apache.log4j.Logger;

import com.bridgeit.model.Register;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWT {

	private final static Logger logger=Logger.getLogger(JWT.class);
	static String CLIENT_ID="12345";
	final static String CLIENT_SECRET="abcdefghijklmnopqrstuvwxyz";
	
	public static String getToken(Register reg) 
	
	{
	
	
		String token;
		long currentmillis=System.currentTimeMillis();
		Date now=new Date(currentmillis);
		logger.info(now);
		System.out.println("----------------------"+reg);
		SignatureAlgorithm signaturealgorithm=SignatureAlgorithm.HS256;
		JwtBuilder builder=Jwts.builder().setId(CLIENT_ID)
				.setIssuedAt(now)
				.setSubject("JWT Token")
				.setIssuer("Aniketh's Tokens")
				.claim("Name", reg.getName())
				.claim("Mobile", reg.getMobile())
				.claim("Id", reg.getUser_id())
				.signWith(SignatureAlgorithm.HS256, CLIENT_SECRET);
		
		long exptime=40000000;
		if(exptime>=0)
		{
			long expmillis=currentmillis+exptime;
			Date expdate=new Date(expmillis);
			logger.info(expdate);
			builder.setExpiration(expdate);	
		}
		token=builder.compact();
		logger.info(token);
		
		
		return token;
	}
	
	
	
	
	public static int verifyToken(String token)
	{
		Claims claims=Jwts.parser().setSigningKey(CLIENT_SECRET).parseClaimsJws(token).getBody();
		logger.info(claims.getId());
		logger.info(claims.getIssuedAt());
		logger.info(claims.getSubject());
		logger.info(claims.getIssuer());
		logger.info(claims.get("Name"));
		logger.info(claims.get("Mobile"));
		logger.info(claims.get("Id"));
		logger.info(claims.getExpiration());
		int idno= (int) claims.get("Id");
		Date expdate =claims.getExpiration();
		logger.info(expdate);
//		String id=Integer.toString(idno);
		logger.info(idno);
		return idno;
		
	}
	
	public static Date verifyTokenDate(String token)
	{
		Claims claims=Jwts.parser().setSigningKey(CLIENT_SECRET).parseClaimsJws(token).getBody();
		Date expdate =claims.getExpiration();
		return expdate;
	}

}
