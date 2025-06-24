package com.sportgearrental.app.service;

import com.sportgearrental.app.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
    CategoryDTO findCategoryById(Long id);
    List<CategoryDTO> findAllCategories();
}