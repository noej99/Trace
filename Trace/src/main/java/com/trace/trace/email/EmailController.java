package com.trace.trace.email;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class EmailController {
	
	@Autowired
	EmailService ems;
	
	@PostMapping(value = "/api.mailcheck", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> mailCheck(@RequestBody HashMap<String, Object> user,HttpServletRequest request){
	 
	    String username = (String) user.get("username");
	    String authNum = ems.sendVerificationCode(username);
	    request.getSession().setAttribute("emailuser", username);
	    
	    
	    return ResponseEntity.status(HttpStatus.OK).body(authNum);
	}
	
	
}
