package org.buzevych.web.rest.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ID;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_roles",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "ID")})
  private List<Role> roles;


}
