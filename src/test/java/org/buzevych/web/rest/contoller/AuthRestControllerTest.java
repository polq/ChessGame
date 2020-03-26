package org.buzevych.web.rest.contoller;

import org.buzevych.web.rest.model.User;
import org.buzevych.web.rest.security.JwtTokenProvider;
import org.buzevych.web.rest.service.UserAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthRestControllerTest {

  MockMvc mockMvc;
  @Mock private JwtTokenProvider tokenProvider;
  @Mock private UserAuthService userAuthService;
  @Mock private AuthenticationManager authenticationManager;

  AuthRestController controller;

  @BeforeEach
  void init() {
    controller = new AuthRestController(tokenProvider, userAuthService, authenticationManager);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setCustomArgumentResolvers().build();
  }

  @Test
  void testRegistration() throws Exception {
    User user = new User();
    user.setUsername("customUsername");
    when(userAuthService.register(any())).thenReturn(user);
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/auth/registration/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"username\", \"password\": \"password\" }")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value(user.getUsername()))
        .andReturn();
  }

  @Test
  void testLogin(@Mock User user) throws Exception {
    when(userAuthService.findByUsername(anyString())).thenReturn(user);
    when(tokenProvider.createToken(anyString(), any())).thenReturn("myToken");

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/auth/login/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"testUsername\", \"password\": \"pass\" }")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("testUsername"))
        .andExpect(jsonPath("$.token").value("myToken"))
        .andReturn();
  }
}
