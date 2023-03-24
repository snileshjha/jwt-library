package com.shyam.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shyam.model.ConfigData;
import com.shyam.model.JwtConfig;
import com.shyam.model.UserAuthentication;
import com.shyam.repository.JwtRepository;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter 
{
	@SuppressWarnings("unused")
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private AES aes;
	
	@Autowired
	private JwtRepository jwtRepository;
	
	@Bean
	public JwtTokenProvider getJwtTokenProvider() {
		return new JwtTokenProvider();
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException
	{
		/*
		 * final List<String> apiEndpoints = Arrays.asList("/Api/test","/Api/token");
		 * Predicate<HttpServletRequest> isApiSecured = r -> apiEndpoints.stream()
		 * .noneMatch(uri -> r.getRequestURI().contains(uri));
		 * 
		 * if (isApiSecured.test(request))
		 * {System.out.println("request.getRequestURI()="+request.getRequestURI());}
		 */
		try
		{
			UserAuthentication userAuthentication = new UserAuthentication();
			
			String jwt = getJwtFromRequest(request);
			
			System.out.println("request.getRequestURI()="+request.getRequestURI());
			
			if (StringUtils.hasText(jwt) && getJwtTokenProvider().validateToken(jwt)) 
			{
				/*UserName mean mobile number, UserId mean device token*/
				
				Claims claims = getJwtTokenProvider().getLoginDetailsFromJWT(jwt);
				
				JwtConfig jwtConfig=jwtRepository.findById(1l).get();
				
			//	System.out.println("UserName:"+decode((String) claims.get("userName"), ConfigData.jwtSecret));

			//	userAuthentication.setUserName(decode((String) claims.get("userName"), ConfigData.jwtSecret));
				userAuthentication.setUserName(decode((String) claims.get("userName"), jwtConfig.getSecret()));

				UserDetails userDetails = new org.springframework.security.core.userdetails.User(userAuthentication.getUserName(), "", new ArrayList<>());
				
				String userId = decode((String) claims.get("sub"), jwtConfig.getSecret());
				String userName = decode((String) claims.get("userName"), jwtConfig.getSecret());
				
				if (userId == null && userName == null) 
				{
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "");
					return;		
				}
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						new UserPrincipal(decode((String) claims.get("userName"), jwtConfig.getSecret()), userId),
						null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				filterChain.doFilter(request, response);

			} else if(request.getRequestURI().startsWith("/Api") || request.getRequestURI().startsWith("/authApi") || request.getRequestURI().startsWith("/leadGen") || request.getRequestURI().startsWith("/notiApi")
					|| request.getRequestURI().startsWith("/busiLogic") || request.getRequestURI().startsWith("/esign") || request.getRequestURI().startsWith("/nach") ) {
				filterChain.doFilter(request, response);
			} else if(request.getRequestURI().startsWith("/favicon.ico")) {}
			else {
				//System.out.println("request.getRequestURI()="+request.getRequestURI());
				//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Full authentication is required to access this resource"); 
				filterChain.doFilter(request, response);
			}
			
			
		} catch (Exception ex) 
		{	
			log.info("API Called :"+request.getRequestURL());
			log.info("API JWT :"+request.getHeader("authentication-token"));
			
			logger.error("Could not set user authentication in security context", ex);
			
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "");
		}
	}

	private String getJwtFromRequest(HttpServletRequest request) throws Exception {
		String bearerToken = request.getHeader("authentication-token");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	@SuppressWarnings("static-access")
	private String decode(String encryptedData, String jwtSecret) throws Exception {
		return aes.decrypt(encryptedData, jwtSecret);
	}
}
