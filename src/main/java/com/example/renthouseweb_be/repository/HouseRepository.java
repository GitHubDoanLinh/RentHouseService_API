package com.example.renthouseweb_be.repository;

import com.example.renthouseweb_be.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    Iterable<House> findAllByDeleteFlag(boolean deleteFlag);
    Page<House> findAllByCategoryId(Long categoriesId, Pageable pageable);
    Page<House> findAllByLocation(String location, Pageable pageable);
}
