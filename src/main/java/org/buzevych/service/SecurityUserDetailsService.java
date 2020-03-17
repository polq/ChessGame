package org.buzevych.service;

import org.buzevych.model.UserModel;
import org.buzevych.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SecurityUserDetailsService implements UserDetailsService {

  @Autowired UserRepository repository;

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
