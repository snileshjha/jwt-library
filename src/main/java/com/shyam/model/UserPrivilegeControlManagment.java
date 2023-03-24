package com.shyam.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@Table(name = "userprivilegecontrolmanagment")
@DynamicUpdate
public class UserPrivilegeControlManagment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RowId", nullable = false)
	private Long rowId;
	@Column(name="ApiID")
	private String apiId;
	@Column(name="Role")
	private String role;
	@Column(name="CreatedBy")
	private String createdBy;
	@Column(name="CreatedDate")
	private Date createdDate;
	@Column(name="ModifiedBy")
	private String modifiedBy;
	@Column(name="ModifiedDate")
	private Date modifiedDate;
	@Column(name="Version")
	private Integer version;
	@Column(name="IsDelete")
	private String isDelete;
}
