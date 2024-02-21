package com.springboot.elearningmanagementstructurebyfeature.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    List<Category> categories(){return categoryService.categories();}

   @PostMapping
    void createNewCategory(@RequestBody CategoryCreationDto categoryCreationDto){

        categoryService.createCategory(categoryCreationDto);

   }
   @PutMapping("/{id}")
   void editCategoryNameById(@PathVariable Integer id, @RequestBody String categoryEditionDto ){
        categoryService.editCategoryNameById(id, categoryEditionDto);
   }
   @GetMapping("/{id}")
    Category category(@PathVariable Integer id){return  categoryService.getCategoryById(id);}

    @DeleteMapping("/{id}")
    void deleteCategory(@PathVariable Integer id) {categoryService.deleteCategoryById(id);}

    @PutMapping("/{id}/disable")
    void disableCategory(@PathVariable Integer id) {
        categoryService.disableCategory(id);
    }

}

