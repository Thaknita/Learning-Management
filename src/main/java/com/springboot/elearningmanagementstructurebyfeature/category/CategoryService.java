package com.springboot.elearningmanagementstructurebyfeature.category;

import java.util.List;


public interface CategoryService {

    List<Category> categories ();

    void createCategory (CategoryCreationDto categoryCreationDto);

    Category getCategoryById(Integer id);


    void editCategoryNameById(Integer id, String categoryEditionDto );

    void deleteCategoryById(Integer id);


    void disableCategory(Integer id);
}
