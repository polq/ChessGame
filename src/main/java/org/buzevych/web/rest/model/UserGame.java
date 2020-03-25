package org.buzevych.web.rest.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_games")
public class UserGame {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long ID;

  @Column(name = "game_id", unique = true)
  private String gameID;

  @Column(name = "username")
  private String username;

  @Column(name = "game_name")
  private String gameName;

  public UserGame(String gameID, String username, String gameName) {
    this.gameID = gameID;
    this.username = username;
    this.gameName = gameName;
  }
}
