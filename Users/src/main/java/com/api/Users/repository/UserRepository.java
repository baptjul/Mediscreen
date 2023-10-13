package com.api.Users.repository;

import com.api.Users.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for user database operations
 */
@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    /**
     * Finds a user by their username
     *
     * @param username The username to search for
     * @return An Optional containing the user if found, or empty otherwise
     */
    Optional<UserEntity> findByUsername(String username);
}
