package org.buzevych.service;

import org.buzevych.model.UserModel;
import org.buzevych.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityUserDetailsServiceTest {

  @Mock UserRepository repository;
  SecurityUserDetailsService service;

  @BeforeEach
  void init() {
    service = new SecurityUserDetailsService(repository);
  }

  @Test
  void testNoSuchUser() {
    when(repository.findByUsername(anyString())).thenReturn(null);
    assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("username"));
  }

  @Test
  void testLoadUserByUsername() {
    UserModel model = new UserModel();
    model.setUsername("username");
    model.setPassword("pass");
    model.setRoles(Collections.singletonList("ADMIN"));
    when(repository.findByUsername(anyString())).thenReturn(model);

    UserDetails details = service.loadUserByUsername("username");

    assertEquals("pass", details.getPassword());
  }
}
