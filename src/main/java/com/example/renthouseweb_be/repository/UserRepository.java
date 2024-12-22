package com.example.renthouseweb_be.repository;

import com.example.renthouseweb_be.model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Iterable<User> findAllByIdNotAndDeleteFlag(Long id, boolean deleteFlag);
    User findByUsername(String username);
    User findByEmail(String email);
    User findByVerificationToken(String email);
    Iterable<User> findByUsernameContaining(String name);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
