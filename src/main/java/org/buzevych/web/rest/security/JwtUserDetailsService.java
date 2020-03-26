package org.buzevych.web.rest.security;

import org.buzevych.web.rest.model.JwtUser;
import org.buzevych.web.rest.model.Role;
import org.buzevych.web.rest.model.User;
import org.buzevych.web.rest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User with the " + username + " was not found");
    }

    List<GrantedAuthority> userAuthorities = convertToAuthorities(user.getRoles());
    return new JwtUser(user.getID(), user.getUsername(), user.getPassword(), userAuthorities);
  }

  private List<GrantedAuthority> convertToAuthorities(List<Role> roles) {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
  }
}
