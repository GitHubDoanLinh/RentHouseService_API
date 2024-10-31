package com.example.renthouseweb_be.service;

import java.util.Optional;

public interface IGenerateService<T>{
    Iterable<T> findAll();
    Iterable<T> findAllByDeleteFlag(boolean deleteFlag);
    Optional<T> findOneById(Long id);
    T save(T t);
    void delete(Long id);
}
