package com.shyam.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint
{
	 private static final Logger logger = LogManager.getLogger(JwtAuthenticationEntryPoint.class);
	 
	 @Override
	 public void commence(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,
	                   AuthenticationException e) throws IOException, ServletException 
	 {
		 logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
	     
	     httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());  
	 }  
}
