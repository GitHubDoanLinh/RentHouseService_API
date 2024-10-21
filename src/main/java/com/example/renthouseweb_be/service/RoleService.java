package com.example.renthouseweb_be.service;

import com.example.renthouseweb_be.model.account.Role;

public interface RoleService {
    Iterable<Role> findAll();

    void save(Role role);

    Role findByName(String name);
}
