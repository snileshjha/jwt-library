package com.shyam.model;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class UserAuthentication {
	
	@NotBlank
	@NotEmpty
	@NotNull
	@JsonProperty("user_name")
	private String userName;
	@NotBlank
	@NotEmpty
	@NotNull
	@JsonProperty("password")
	private String password;
}

