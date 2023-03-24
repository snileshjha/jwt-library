package com.shyam.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class URMUserDetails {
	@JsonProperty("user_status")
	private String userStatus;
	@JsonProperty("error_message")
	private String errorStatus;
	@JsonProperty("token")
	private String token;
	@JsonProperty("user_type")
	private String userType;
	@JsonProperty("password_expiry")
	private Date passwordExpiry;
	@JsonProperty("authenticated1")
	private String authenticated1;
	@JsonProperty("password_status")
	private String passwordStatus;
	@JsonProperty("user_name")
	private String userName;
	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("useraccount_id")
	private String useraccountId;
	@JsonProperty("tenant_id")
	private String tenantId;
	@JsonProperty("last_accessed_time")
	private Date lastAccessedTime;
	@JsonProperty("last_login_time")
	private Date lastLoginTime;
	@JsonProperty("roleDesc")
	private List<String> roleDesc;
	@JsonProperty("dataPrivacyFlag")
	private String dataPrivacyFlag;
	@JsonProperty("authenticated")
	private boolean authenticated;
	@JsonProperty("status")
	private String status;
	@JsonProperty("name")
	private List<String> name;
	@JsonIgnoreProperties
	private String nbfcToken;
}
