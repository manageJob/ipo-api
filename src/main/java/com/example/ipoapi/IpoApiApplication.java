package com.example.ipoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class IpoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpoApiApplication.class, args);
	}

	@GetMapping("/getName")
	public String getName()
	{
		return "EIEI";
	}

}
