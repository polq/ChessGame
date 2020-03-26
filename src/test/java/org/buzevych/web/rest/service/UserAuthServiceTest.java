package org.buzevych.web.rest.service;

import org.buzevych.web.rest.dto.UserDTO;
import org.buzevych.web.rest.model.Role;
import org.buzevych.web.rest.model.User;
import org.buzevych.web.rest.repository.RoleRepository;
import org.buzevych.web.rest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceTest {

  @Mock private UserRepository userRepository;
  @Mock private RoleRepository roleRepository;

  UserAuthService authService;

  @BeforeEach
  void init() {
    authService = new UserAuthService(userRepository, roleRepository, new BCryptPasswordEncoder());
  }

  @Test
  void testRegisterAlreadyRegisterUser(@Mock User user) {
    when(userRepository.findByUsername(anyString())).thenReturn(user);
    UserDTO userDTO = new UserDTO("username", "password");
    assertThrows(IllegalArgumentException.class, () -> authService.register(userDTO));
  }

  @Test
  void testRegisterNewUser(@Mock Role role, @Mock User user) {
    when(userRepository.findByUsername(anyString())).thenReturn(null);
    when(roleRepository.findByName(anyString())).thenReturn(role);
    when(userRepository.save(any())).thenReturn(user);
    UserDTO userDTO = new UserDTO("username", "password");

    assertEquals(user, authService.register(userDTO));
  }

  @Test
  void testFindByUsernameNotFound() {
    when(userRepository.findByUsername(anyString())).thenReturn(null);

    assertThrows(UsernameNotFoundException.class, () -> authService.findByUsername("username"));
  }

  @Test
  void testFindByUsername(@Mock User user) {
    when(userRepository.findByUsername(anyString())).thenReturn(user);

    assertEquals(user, authService.findByUsername("user"));
  }
}
