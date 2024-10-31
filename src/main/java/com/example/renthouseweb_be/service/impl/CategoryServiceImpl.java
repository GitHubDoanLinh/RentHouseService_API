package com.example.renthouseweb_be.service.impl;

import com.example.renthouseweb_be.model.Category;
import com.example.renthouseweb_be.repository.CategoryRepository;
import com.example.renthouseweb_be.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Iterable<Category> findAllByDeleteFlag(boolean deleteFlag) {
        return null;
    }

    @Override
    public Optional<Category> findOneById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(value -> value.setDeleteFlag(true));
        categoryRepository.save(category.get());
    }
}
