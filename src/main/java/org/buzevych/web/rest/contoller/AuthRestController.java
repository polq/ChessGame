package org.buzevych.web.rest.contoller;

import lombok.extern.slf4j.Slf4j;
import org.buzevych.web.rest.dto.UserDTO;
import org.buzevych.web.rest.model.User;
import org.buzevych.web.rest.security.JwtTokenProvider;
import org.buzevych.web.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthRestController {

  JwtTokenProvider tokenProvider;
  UserService userService;
  AuthenticationManager authenticationManager;

  @Autowired
  public AuthRestController(JwtTokenProvider tokenProvider, UserService userService, AuthenticationManager authenticationManager) {
    this.tokenProvider = tokenProvider;
    this.userService = userService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/registration")
  public ResponseEntity registration(@RequestBody UserDTO userDTO) {
    User registeredUser = userService.register(userDTO);
    Map<Object, Object> response = new HashMap<>();
    response.put("username", registeredUser.getUsername());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody UserDTO userDTO) {
    String username = userDTO.getUsername();
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, userDTO.getPassword()));

    User user = userService.findByUsername(username);
    String token = tokenProvider.createToken(username, user.getRoles());
    Map<Object, Object> response = new HashMap<>();
    response.put("username", username);
    response.put("token", token);
    return ResponseEntity.ok(response);
  }
}
