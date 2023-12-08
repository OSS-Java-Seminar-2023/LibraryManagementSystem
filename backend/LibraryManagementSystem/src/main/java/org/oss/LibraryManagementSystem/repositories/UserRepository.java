package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.username= :username")
    public Optional<User> getUserByUsername(@Param("username") String username);

    public Optional<User> findByEmail(String email);

    public Optional<User> findById(UUID id);
}
