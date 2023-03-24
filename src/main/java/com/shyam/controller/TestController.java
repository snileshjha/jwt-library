package com.shyam.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.config.JwtTokenProvider;
import com.shyam.config.UserPrincipal;

@RestController
@RequestMapping("/Api")
public class TestController {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	//Testing purpose use.
	/*
	 * @Bean public JwtTokenProvider getJwtTokenProvider() { return new
	 * JwtTokenProvider(); }
	 */
	
	@GetMapping("/test")
	public String getTest(HttpServletRequest request)throws Exception {
		
		UserDetails userDetails = new org.springframework.security.core.userdetails.User("9967783107", "",new ArrayList<>());
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				new UserPrincipal("9967783107", String.valueOf("23456")),
				null, userDetails.getAuthorities());
		
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	
		SecurityContextHolder.getContext().setAuthentication(authentication);		
		
		String jwt = "Bearer " + tokenProvider.generateToken();
		HttpHeaders responseHeaders = new HttpHeaders();
		
		responseHeaders.add("Authentication-Token", jwt);
		
//		Testing purpose use.
//		getJwtTokenProvider().validateToken(jwt.substring(7, jwt.length()));
		
		return "Authentication-Token:"+jwt;
	}
	
	@PostMapping(path = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getToken() {		
		return "hello world--1";
	}
}
