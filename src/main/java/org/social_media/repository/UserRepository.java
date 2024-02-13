package org.social_media.repository;

import org.social_media.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select u from User u where u.firstName like %:query% or u.lastName like %:query% or u.email like %:query%")
    Optional<User> searchUser(String query);
}
