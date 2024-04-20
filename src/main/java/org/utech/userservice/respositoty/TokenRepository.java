package org.utech.userservice.respositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.utech.userservice.model.Token;

import java.util.Optional;

@Repository()
// This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByValueAndDeletedEquals(String value, boolean isDeleted);

    Optional<Token> findByValue(String value);
}
