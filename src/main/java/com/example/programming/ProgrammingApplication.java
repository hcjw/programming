package com.example.programming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
public class ProgrammingApplication {

    @RequestMapping("/")
    String home() {
      return "login";
    }
    
	public static void main(String[] args) {
		SpringApplication.run(ProgrammingApplication.class, args);
	}

}