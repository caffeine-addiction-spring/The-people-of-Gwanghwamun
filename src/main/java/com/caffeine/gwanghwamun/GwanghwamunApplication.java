package com.caffeine.gwanghwamun;

import com.caffeine.gwanghwamun.common.domain.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileProperties.class)
public class GwanghwamunApplication {

	public static void main(String[] args) {
		SpringApplication.run(GwanghwamunApplication.class, args);
	}
}
