package com.shyam.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.shyam.model.ConfigData;
import com.shyam.model.JwtConfig;
import com.shyam.repository.JwtRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component("tokenProvider")
public class JwtTokenProvider {
	private static final Logger LOGGER = LogManager.getLogger(JwtTokenProvider.class);

	@Autowired
	private AES aes;
	
	@Autowired
	private JwtRepository jwtRepository;
	
	public String generateToken()throws Exception  {

		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Date now = new Date();
		JwtConfig jwtConfig=jwtRepository.findById(1l).get();
		//Date expiryDate = new Date(now.getTime() + ConfigData.jwtExpirationInMs);
		Date expiryDate = new Date(now.getTime() + jwtConfig.getJwtExpirationInMs());

		if(userPrincipal!=null) {
		/*UserName mean mobile number, UserId mean device token*/
		System.out.println("userId:"+userPrincipal.getUserId()+" userName:"+userPrincipal.getUsername());
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", encode(userPrincipal.getUserId(), jwtConfig.getSecret()));
		claims.put("sessionKey", getUUID());
		claims.put("userName", encode(userPrincipal.getUsername(), jwtConfig.getSecret()));
		
		/*lOGGER.info(String.format("claims=%s", claims));
		lOGGER.info(String.format("now=%s", now));
		lOGGER.info(String.format("jwtExpirationInMs=%s", jwtExpirationInMs));
		lOGGER.info(String.format("jwtExpirationInMs=%s", expiryDate));*/
		
		return Jwts.builder().setClaims(claims).setSubject(encode(userPrincipal.getUserId(), jwtConfig.getSecret()))
				.setIssuedAt(new Date()).setExpiration(expiryDate).setIssuer("afx-arthmate")
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret()).compact();
		
		  } else { throw new
		  RuntimeException("Can not Provide JWT Token Before Login");
		  }		 
	}

	// public Long getUserIdFromJWT(String token) {
	public Claims getLoginDetailsFromJWT(String token)throws Exception {
		JwtConfig jwtConfig=jwtRepository.findById(1l).get();
		Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
		// return Long.parseLong(claims.getSubject());
		return claims;
	}

	public boolean validateToken(String authToken) {
		JwtConfig jwtConfig=jwtRepository.findById(1l).get();
		try {
			Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			LOGGER.error("Invalid JWT signature for token {}", authToken);
		} catch (MalformedJwtException ex) {
			LOGGER.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			LOGGER.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			LOGGER.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			LOGGER.error("JWT claims string is empty.");
		}
		return false;
	}

	public String getUUID() {
		return UUID.randomUUID().toString();
	}

	private String encode(String toEncryptData, String jwtSecret) throws Exception {
		return aes.encrypt(toEncryptData, jwtSecret);
	}
}
