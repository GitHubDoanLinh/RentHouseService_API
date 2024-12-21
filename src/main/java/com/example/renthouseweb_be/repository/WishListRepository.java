package com.example.renthouseweb_be.repository;

import com.example.renthouseweb_be.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList,Long> {
    List<WishList> findAllByUserIdAndDeleteFlag(Long userId,boolean deleteFlag);
}
