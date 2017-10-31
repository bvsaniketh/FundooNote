package com.bridgeit.utilityservices;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
public class RedisService 
	{
	
	private final static Logger logger=Logger.getLogger(RedisService.class);
	static Jedis jedis=new Jedis("localhost");
	
	public static void sendtokenredis(int i, String token)
	{
		//jedis.set("key1","anibo");
		
		jedis.hset(Integer.toString(i), "Token", token);
	}
	
	public static String gettokenfromredis(int i)
	{
		String token=jedis.hget(Integer.toString(i), "Token");
		logger.info(token);
		return token;
	}
}
