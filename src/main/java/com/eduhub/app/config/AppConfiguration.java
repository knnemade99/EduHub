package com.eduhub.app.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Import;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@org.springframework.context.annotation.Configuration
@Import({ org.hibernate.cfg.Configuration.class})
public class AppConfiguration {
	
	@Bean
	public SessionFactory sessionFactory() {
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		return sessionFactory;
	}

}
