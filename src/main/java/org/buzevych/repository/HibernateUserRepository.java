package org.buzevych.repository;

import org.buzevych.model.UserModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Repository class that implements ${@link UserRepository} interface and overrides methods to
 * retrieve user by it's username and to save new user.
 */
@Repository
public class HibernateUserRepository implements UserRepository {

  @PersistenceContext EntityManager entityManager;

  /**
   * Method is used to get {@link UserModel} entity by a username.
   *
   * @param username specifies with user we are looking for
   * @return {@link UserModel} instance
   */
  @Override
  public UserModel findByUsername(String username) {
    return entityManager.find(UserModel.class, username);
  }

  /**
   * Method is used to save a new user into a database.
   *
   * @param user specifies which entity is to be save
   */
  @Override
  public void save(UserModel user) {
    entityManager.persist(user);
  }
}
