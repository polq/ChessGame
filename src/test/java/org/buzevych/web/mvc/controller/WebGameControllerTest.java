package org.buzevych.web.mvc.controller;

import org.buzevych.core.boardgame.game.GameSnapshot;
import org.buzevych.web.mvc.service.WebGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class WebGameControllerTest {

  @Mock WebGameService service;
  @Mock GameSnapshot snapshot;
  @Mock Model model;
  @Mock MultipartFile file;
  @Autowired MockMvc mockMvc;

  WebGameController controller;

  @BeforeEach
  void init() {
    controller = new WebGameController(service);
    this.mockMvc = MockMvcBuilders.standaloneSetup(controller).setCustomArgumentResolvers().build();
  }

  @Disabled
  @Test
  void testIndexPage() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andReturn();
  }

  @Test
  void testMVCMove() throws Exception {
    this.mockMvc
        .perform(MockMvcRequestBuilders.post("/play").param("move", "E2 E4"))
        .andExpect(status().isOk())
        .andExpect(view().name("game"))
        .andReturn();
  }

  @Test
  void testMVCStart() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/start")
                .param("gameName", "chess")
                .param("sourceType", "File")
                .param("startGame", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("game"))
        .andReturn();
  }

  @Test
  void testMVCLoad() throws Exception {
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.multipart("/start")
                .file("file", "test data".getBytes())
                .param("gameName", "chess")
                .param("sourceType", "File")
                .param("loadGame", ""))
        .andExpect(status().isOk())
        .andExpect(view().name("game"));
  }

  @Test
  void testStartGame() {
    when(service.startGame(anyString(), anyString(), eq(true))).thenReturn(snapshot);
    assertEquals("game", controller.startNewGame("anyGame", "sourceType", model));
  }

  @Test
  void testLoadLatestSave() throws IOException {
    when(file.isEmpty()).thenReturn(true);
    when(service.startGame(anyString(), anyString(), eq(false))).thenReturn(snapshot);
    assertEquals("game", controller.loadSave("anyGame", "sourceType", file, model));
  }

  @Test
  void testLoadSaveFromFile() throws IOException {
    when(file.isEmpty()).thenReturn(false);
    when(service.loadGameFromFile(anyString(), any(MultipartFile.class))).thenReturn(snapshot);
    assertEquals("game", controller.loadSave("anyGame", "sourceType", file, model));
  }

  @Test
  void testMakeMove() {
    when(service.play(anyString())).thenReturn(snapshot);
    assertEquals("game", controller.makeMove("E2 E5", model));
  }
}
