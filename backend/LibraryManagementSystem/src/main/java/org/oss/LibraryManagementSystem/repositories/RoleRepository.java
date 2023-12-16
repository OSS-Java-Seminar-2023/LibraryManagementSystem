package org.oss.LibraryManagementSystem.repositories;

import org.oss.LibraryManagementSystem.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query("SELECT r FROM Role r WHERE r.name='MEMBER'")
    public Role getDefaultRole();

    public Role findRoleByName(String name);
}
