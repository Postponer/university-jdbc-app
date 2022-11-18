package ua.com.foxminded.springbootjdbcapi.task22;

import javax.sql.DataSource;

import org.junit.ClassRule;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

import com.zaxxer.hikari.HikariDataSource;

public class Config {
	
	@ClassRule
    private PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");
    
    @Bean
    public DataSource getDataSource(){
    	
        container.start();
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        return dataSource;
        
    }

}