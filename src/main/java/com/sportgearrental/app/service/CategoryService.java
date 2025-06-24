package com.sportgearrental.app.service;


import com.sportgearrental.app.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory (Category category);
    Category updateCategory (Long id, Category category);
    void deleteCategory (Long id);
    Category findCategoryById (Long id);
    List<Category> findAllCategories();



}
