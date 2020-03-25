package org.buzevych.web.mvc.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserModel {

  @Id
  private String username;

  private String password;

  @Column @ElementCollection
  private List<String> roles;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }
}
