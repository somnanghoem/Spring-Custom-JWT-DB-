package com.security.springsecurity.controller.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greeting")
public class GreetingController {
	
	
	@GetMapping("/hello")
	public ResponseEntity<String> hello( ){
		return ResponseEntity.ok("hello from api");
	}
	
	@GetMapping("/hi")
	public ResponseEntity<String> hi( ){
		return ResponseEntity.ok("hi from api");
	}
	
	@GetMapping("/la")
	public ResponseEntity<String> la( ){
		return ResponseEntity.ok("la from api");
	}

}
