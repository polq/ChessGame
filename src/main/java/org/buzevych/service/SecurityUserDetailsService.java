package org.buzevych.service;

import org.buzevych.model.UserModel;
import org.buzevych.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link UserDetailsService} implementation class that has one overrided method that get UserDetail
 * object by a username.
 */
@Service
@Transactional
public class SecurityUserDetailsService implements UserDetailsService {

  UserRepository repository;

  @Autowired
  SecurityUserDetailsService(UserRepository repository) {
    this.repository = repository;
  }

  /**
   * Method that is used to retrieve a real object from a specific repository and return it as a
   * {@link UserDetails} object that will be then compared by a Spring security with an input from a
   * user.
   *
   * @param username specifies username that is to be looked
   * @return {@link UserDetails} containing real user credentials from a {@link
   *     org.springframework.stereotype.Repository}
   * @throws UsernameNotFoundException in case no user with the specified username exists in the
   *     repository
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserModel user = repository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("No user found for " + username + " username");
    }
    User.UserBuilder builder = User.withUsername(user.getUsername());
    builder.password(user.getPassword());
    builder.roles(user.getRoles().get(0));
    return builder.build();
  }
}
