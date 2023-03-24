package com.shyam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class UserRoleDetails {
	@JsonProperty("roleDesc")
	private String roleDesc;
	@JsonProperty("created_by")
	private String createdBy;
	@JsonProperty("created_date")
	private String createdDate;
	@JsonProperty("id")
	private String roleId;
	@JsonProperty("name")
    private String roleName;
	@JsonProperty("parent_id")
    private String parentId;
	@JsonProperty("parent_name")
    private String parentName;
	@JsonProperty("context_id")
    private String contextId;
	@JsonProperty("context_name")
    private String contextName;
	@JsonProperty("role_status")
    private String roleStatus;
}
