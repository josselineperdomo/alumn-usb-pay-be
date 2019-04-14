package org.alumnusb.easypay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EasypayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasypayServiceApplication.class, args);
	}
}