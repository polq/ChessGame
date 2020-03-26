package org.buzevych.web.rest.contoller;

import org.buzevych.web.rest.dto.UserDTO;
import org.buzevych.web.rest.model.User;
import org.buzevych.web.rest.security.JwtTokenProvider;
import org.buzevych.web.rest.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/** Controller class for REST application that is used to register a user and to login. */
@RestController
@RequestMapping("/auth")
public class AuthRestController {

  private JwtTokenProvider tokenProvider;
  private UserAuthService userAuthService;
  private AuthenticationManager authenticationManager;

  @Autowired
  public AuthRestController(
      JwtTokenProvider tokenProvider,
      UserAuthService userAuthService,
      AuthenticationManager authenticationManager) {
    this.tokenProvider = tokenProvider;
    this.userAuthService = userAuthService;
    this.authenticationManager = authenticationManager;
  }

  /**
   * Method that is used to register a new user with the given credentials in a following JSON
   * format: { "username": "admin", "password": "pass" }
   *
   * @param userDTO defines input
   * @return 200 code with the username, if user was successfully registered.
   */
  @PostMapping("/registration")
  public ResponseEntity registration(@RequestBody UserDTO userDTO) {
    User registeredUser = userAuthService.register(userDTO);
    Map<Object, Object> response = new HashMap<>();
    response.put("username", registeredUser.getUsername());
    return ResponseEntity.ok(response);
  }

  /**
   * Method that is used to obtain JWT token for a already registered user. Input should be in the
   * following JSON format: { "username": "admin", "password": "pass" }
   *
   * @param userDTO defines input
   * @return a JSON string in the following format
   * { "username": "user",
   *   "token": "eyJhbjE1ODUyMTc0NDksIs"
   * }
   * Given token will be used to make further requests to an REST application.
   */
  @PostMapping("/login")
  public ResponseEntity login(@RequestBody UserDTO userDTO) {
    String username = userDTO.getUsername();
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, userDTO.getPassword()));

    User user = userAuthService.findByUsername(username);
    String token = tokenProvider.createToken(username, user.getRoles());
    Map<Object, Object> response = new HashMap<>();
    response.put("username", username);
    response.put("token", token);
    return ResponseEntity.ok(response);
  }
}
