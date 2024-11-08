package com.example.renthouseweb_be.repository;

import com.example.renthouseweb_be.model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
    Iterable<User> findByUsernameContaining(String name);
}
