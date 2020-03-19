package org.buzevych.service;

import org.buzevych.model.UserModel;
import org.buzevych.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Service class that is used to register a new user into a given {@link
 * org.springframework.stereotype.Repository} class with the specified {@link PasswordEncoder}
 */
@Service
public class SecurityService {

  private UserRepository repository;
  private PasswordEncoder encoder;

  @Autowired
  SecurityService(UserRepository repository, PasswordEncoder encoder) {
    this.repository = repository;
    this.encoder = encoder;
  }

  /**
   * Method that takes a new {@link UserModel} and saves it to a given repository while encoding
   * password with a given {@link PasswordEncoder}
   *
   * @param user that is to be save
   */
  @Transactional
  public void registerNewUser(UserModel user) {
    if (repository.findByUsername(user.getUsername()) != null) {
      throw new IllegalArgumentException(
          "User with the " + user.getUsername() + " username already exists");
    }
    user.setPassword(encoder.encode(user.getPassword()));
    user.setRoles(Collections.singletonList("USER"));
    repository.save(user);
  }
}
