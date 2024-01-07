package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {
}
