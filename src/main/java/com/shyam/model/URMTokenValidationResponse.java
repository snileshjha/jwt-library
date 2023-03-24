package com.shyam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class URMTokenValidationResponse {
	@JsonProperty("status")
	private String status;
	@JsonProperty("message")
	private String message;
	@JsonProperty("error_code")
	private String errorCode;
}
