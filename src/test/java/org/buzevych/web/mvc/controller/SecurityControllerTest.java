package org.buzevych.web.mvc.controller;

import org.buzevych.web.mvc.model.UserModel;
import org.buzevych.web.mvc.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SecurityControllerTest {

  @Mock SecurityService service;

  MockMvc mockMvc;
  SecurityController controller;

  @BeforeEach
  void init() {
    controller = new SecurityController(service);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setCustomArgumentResolvers().build();
  }

  @Test
  void testLoginPage() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/login/"))
        .andExpect(status().isOk())
        .andExpect(view().name("login"))
        .andReturn();
  }

  @Test
  void testRegistration() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/registration/"))
        .andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andReturn();
  }

  @Test
  void testRegistrationPost() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.post("/registration").flashAttr("user", new UserModel()))
        .andExpect(status().isOk())
        .andExpect(view().name("login"))
        .andReturn();
  }

  @Test
  void testRegistrationPostUserExists() throws Exception {
    willThrow(IllegalArgumentException.class).given(service).registerNewUser(any(UserModel.class));

    mockMvc
        .perform(MockMvcRequestBuilders.post("/registration/").flashAttr("user", new UserModel()))
        .andExpect(status().isOk())
        .andExpect(view().name("registration"))
        .andReturn();
  }

  @Test
  void testLoginError() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/login-error"))
        .andExpect(model().attribute("loginError", true))
        .andExpect(status().isOk())
        .andExpect(view().name("login"))
        .andReturn();
  }
}
