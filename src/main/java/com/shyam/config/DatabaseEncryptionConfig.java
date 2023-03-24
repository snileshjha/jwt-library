package com.shyam.config;

import java.io.FileInputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseEncryptionConfig {
	
	@Value("#{systemProperties[propFileName] ?: 'src/main/resources/application.properties'}")
	String propFileName;
	
	
	@Bean
	public EncryptionDB getEncryptionDB() {
		// Do any additional configuration here
		return new EncryptionDB();
	}

	@SuppressWarnings("rawtypes")
	@Bean
	public DataSource mySqlDataSource() throws Exception {

		Properties prop = new Properties();
		prop.load(new FileInputStream(propFileName));
		
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

		dataSourceBuilder.driverClassName(prop.getProperty("spring.datasource.driver-class-name"));
		dataSourceBuilder.url(prop.getProperty("spring.datasource.url"));
		dataSourceBuilder.username(prop.getProperty("spring.datasource.username"));
		dataSourceBuilder.password(getEncryptionDB().toDecrypt(prop.getProperty("spring.datasource.password")));

		return dataSourceBuilder.build();
	}

}
