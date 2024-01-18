package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Role;
import org.oss.LibraryManagementSystem.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.username= :username")
    Optional<User> getUserByUsername(@Param("username") String username);

    Page<User> findAll(Pageable pageable);

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);

    List<User> findAllByRoles(Role role);


}
