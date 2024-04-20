package org.utech.userservice.respositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.utech.userservice.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
