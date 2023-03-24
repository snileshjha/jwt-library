package com.shyam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyam.model.JwtConfig;

public interface JwtRepository extends JpaRepository<JwtConfig, Long>{

}
