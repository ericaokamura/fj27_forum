package br.com.alura.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.input.LoginInputDto;
import br.com.alura.forum.controller.dto.output.AuthenticationTokenOutputDto;
import br.com.alura.forum.security.service.TokenManager;

@RequestMapping("/api/auth")
@RestController
public class AuthController {
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	TokenManager tokenManager;
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthenticationTokenOutputDto> authenticate(@RequestBody LoginInputDto loginInput) {
		UsernamePasswordAuthenticationToken authentication = loginInput.build();
		try {
			Authentication authenticate = authManager.authenticate(authentication);
			String jwt = tokenManager.generateToken(authenticate);
			AuthenticationTokenOutputDto tokenOutput = new AuthenticationTokenOutputDto("Bearer", jwt);
			return ResponseEntity.ok(tokenOutput);
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
