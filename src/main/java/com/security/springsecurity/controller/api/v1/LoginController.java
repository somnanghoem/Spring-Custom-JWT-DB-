/**
 * 
 */
package com.security.springsecurity.controller.api.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hoem Somnang
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController {

	
	@GetMapping("")
	public String login() {
		return "login";
	}
}
