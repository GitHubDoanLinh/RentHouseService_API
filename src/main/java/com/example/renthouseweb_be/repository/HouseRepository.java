package com.example.renthouseweb_be.repository;

import com.example.renthouseweb_be.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    Iterable<House> findAllByDeleteFlag(boolean deleteFlag);
    Page<House> findAllByCategoryId(Long categoriesId, Pageable pageable);
    Page<House> findAllByLocation(String location, Pageable pageable);
    Iterable<House> findAllByUserIdAndDeleteFlag(Long userId, boolean deleteFlag);
    Iterable<House> findByNameContainsIgnoreCaseAndAndDeleteFlag(String name, boolean deleteFlag);

    @Query("SELECT h FROM House h JOIN h.convenients c " +
            "WHERE (:name IS NULL OR :name = '' OR LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:location IS NULL OR :location = '' OR LOWER(h.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
            "(:categoryId IS NULL OR :categoryId = 0 OR h.category.id = :categoryId) AND " +
            "(:minPrice IS NULL OR :minPrice = 0.0 OR h.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR :maxPrice = 0.0 OR h.price <= :maxPrice) AND " +
            "(:convenientIds IS NULL OR (0 IN :convenientIds OR c.id IN :convenientIds)) AND " +
            "h.deleteFlag = :deleteFlag")
    List<House> findHousesByCriteria(
            @Param("name") String name,
            @Param("location") String location,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            @Param("convenientIds") List<Long> convenientIds,
            @Param("deleteFlag") boolean deleteFlag
    );
}
