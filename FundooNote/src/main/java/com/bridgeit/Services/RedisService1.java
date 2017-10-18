/*package com.bridgeit.Services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.bridgeit.Controller.NavigationController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import redis.clients.jedis.Jedis;

@Service
public class RedisService1 {

	private final static Logger log = Logger.getLogger(RedisService.class);
	
	public void insertToken(String id,String token) {
		
		Jedis jd = new Jedis("localhost", 6379);
		
		jd.hset(id, "token", token);
	}
	
	public String RetreiveTokenFromRedis(String clientId) {
		
		Jedis jd = new Jedis("localhost",6379);
		String tokenFromRedis = jd.hget(clientId, "token");
		
		log.warn("\nTokenFromRedis: "+tokenFromRedis);

		return tokenFromRedis;
	}
}
*/