package com.example.renthouseweb_be.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPaginationService<T> {
    Page<T> findAllWithPagination(boolean deleteFlag, Pageable pageable);
}
