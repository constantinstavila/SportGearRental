package com.sportgearrental.app.service;

import com.sportgearrental.app.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
    Category findCategoryById(Long id);
    Page<Category> findAllCategories(Pageable pageable);
    List<Category> findAllCategories();
}