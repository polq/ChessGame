package org.buzevych.service;

import org.buzevych.model.UserModel;
import org.buzevych.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {

  @Mock UserRepository repository;

  SecurityService securityService;

  @BeforeEach
  void init() {
    securityService = new SecurityService(repository, new BCryptPasswordEncoder());
  }

  @Test
  void testUserAlreadyExists(@Mock UserModel model) {
    when(model.getUsername()).thenReturn("");
    when(repository.findByUsername(anyString())).thenReturn(model);
    assertThrows(IllegalArgumentException.class, () -> securityService.registerNewUser(model));
  }

  @Test
  void testRegisterNewUser() {
    UserModel model = new UserModel();
    model.setUsername("username");
    model.setPassword("password");

    assertDoesNotThrow(() -> securityService.registerNewUser(model));
  }
}
