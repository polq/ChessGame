package org.buzevych.service;

import org.buzevych.model.UserModel;
import org.buzevych.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class SecurityService {

  @Autowired UserRepository repository;

  @Transactional
  public UserModel registerNewUser(UserModel user) {
    if (repository.findByUsername(user.getUsername()) != null) {
      throw new IllegalArgumentException(
          "User with the " + user.getUsername() + " username already exists");
    }
    user.setRoles(Collections.singletonList("USER"));
    return repository.save(user);
  }
}
