package org.buzevych.web.rest.repository;

import org.buzevych.web.rest.model.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGameRepository extends JpaRepository<UserGame, Long> {

    UserGame findByGameID(String gameID);
}
