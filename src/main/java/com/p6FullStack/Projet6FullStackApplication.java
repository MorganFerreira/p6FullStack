package com.p6FullStack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import com.p6FullStack.configuration.RsaKeyProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
@EnableJpaAuditing
public class Projet6FullStackApplication {

	public static void main(String[] args) {
		SpringApplication.run(Projet6FullStackApplication.class, args);
	}

}
