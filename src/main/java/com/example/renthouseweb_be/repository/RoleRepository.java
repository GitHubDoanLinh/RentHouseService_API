package com.example.renthouseweb_be.repository;

import com.example.renthouseweb_be.model.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}
