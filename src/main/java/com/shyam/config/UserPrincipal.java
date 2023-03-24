package com.shyam.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserPrincipal implements UserDetails
{
	private static final long serialVersionUID = 1L;
	
	private String username;
    private String password;
    private String userId;
	
    private Collection<? extends GrantedAuthority> authorities;
    
    public UserPrincipal(String username,String userId) {
    	 this.username = username;
         this.userId=userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}