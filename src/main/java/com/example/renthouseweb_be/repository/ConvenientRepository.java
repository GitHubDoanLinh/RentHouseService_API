package com.example.renthouseweb_be.repository;

import com.example.renthouseweb_be.model.Convenient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvenientRepository extends JpaRepository<Convenient, Long> {
}
