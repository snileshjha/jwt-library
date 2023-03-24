package com.shyam.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jwt_config")
public class JwtConfig {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name="jwt_Secret")
	private String jwtSecret;
	@Column(name="jwtExpirationInMs")
	private Integer jwtExpirationInMs;
	@Column(name="secret")
	private String secret;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getJwtSecret() {
		return jwtSecret;
	}
	public void setJwtSecret(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}
	public Integer getJwtExpirationInMs() {
		return jwtExpirationInMs;
	}
	public void setJwtExpirationInMs(Integer jwtExpirationInMs) {
		this.jwtExpirationInMs = jwtExpirationInMs;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	@Override
	public String toString() {
		return "JwtConfig [id=" + id + ", jwtSecret=" + jwtSecret + ", jwtExpirationInMs=" + jwtExpirationInMs
				+ ", secret=" + secret + ", getId()=" + getId() + ", getJwtSecret()=" + getJwtSecret()
				+ ", getJwtExpirationInMs()=" + getJwtExpirationInMs() + ", getSecret()=" + getSecret()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}
