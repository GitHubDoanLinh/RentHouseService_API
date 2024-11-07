package com.example.renthouseweb_be.service;

import com.example.renthouseweb_be.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HouseService extends IGenerateService<House> {
    Page<House> findAll(Pageable pageable);
    Page<House> getAllHousesSortedByPriceUp(int page, int size);
    Page<House> findAllByCategoryId(Long categoriesId,Pageable pageable);
    Page<House> findAllByLocation(String location, Pageable pageable);
    Iterable<House> findAllByUserIdAndDeleteFlag(Long userId, boolean deleteFlag);
    Iterable<House> findByNameContainsIgnoreCaseAndDeleteFlag(String name, boolean deleteFlag);
}
