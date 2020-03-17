package org.buzevych.repository;

import org.buzevych.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SimpleListUserRepository implements UserRepository {

  @Autowired private PasswordEncoder encoder;

  private static Map<String, UserModel> userDAOMap = new HashMap<>();

  @Override
  public UserModel findByUsername(String userName) {
    return userDAOMap.get(userName);
  }

  @Override
  public UserModel save(UserModel user) {
    user.setPassword(encoder.encode(user.getPassword()));
    return userDAOMap.put(user.getUsername(), user);
  }
}
