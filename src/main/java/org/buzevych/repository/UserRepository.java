package org.buzevych.repository;

import org.buzevych.model.UserModel;

public interface UserRepository {

    UserModel findByUsername (String userName);

    UserModel save(UserModel user);
}
