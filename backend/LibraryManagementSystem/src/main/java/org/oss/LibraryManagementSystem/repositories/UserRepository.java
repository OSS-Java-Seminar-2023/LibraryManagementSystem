package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
