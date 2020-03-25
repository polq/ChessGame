package org.buzevych.web.mvc.repository;

import org.buzevych.web.mvc.model.UserModel;

/** Interface that is used to handle login/registration methods from a service class. */
public interface UserRepository {

  /**
   * Method is used to find a user by a username;
   *
   * @param username specifies user to be found
   * @return retrieved user
   */
  UserModel findByUsername(String username);

  /**
   * Used to save a new user
   *
   * @param user which should be save
   */
  void save(UserModel user);
}
