package org.buzevych.controller;

import java.util.ArrayList;
import java.util.List;

public class GameProperties {


  List<String> allGames;
  String gameName;

  public GameProperties() {
    this.allGames = new ArrayList<>();
    this.allGames.add("Chess");
    this.allGames.add("Checkers");
  }

  public String getGameName() {
    return gameName;
  }

  public void setGameName(String gameName) {
    this.gameName = gameName;
  }

  public List<String> getAllGames() {
    return allGames;
  }
}

