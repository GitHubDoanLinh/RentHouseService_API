package com.example.renthouseweb_be.repository;

import com.example.renthouseweb_be.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByHouseIdAndDeleteFlag(Long houseId,boolean deleteFlag);
}
