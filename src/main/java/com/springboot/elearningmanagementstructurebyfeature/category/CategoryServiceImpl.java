package com.springboot.elearningmanagementstructurebyfeature.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> categories() {
       List<Category> categories = categoryRepository.selectAllCategories();
        return categoryMapper.toCategoryListDto(categories);
    }

    @Override
    public void createCategory(CategoryCreationDto categoryCreationDto) {

        Category category = categoryMapper.fromCategoryDto(categoryCreationDto);
        categoryRepository.save(category);

    }

    @Override
    public Category getCategoryById(Integer id) {

        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course has not been found"
                )
        );

        return categoryMapper.toCategoryDto(category);

    }

    @Override
    public void editCategoryNameById(Integer id, String categoryEditionDto) {
        if (!categoryRepository.existsById(id) ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category not found");
        }
               categoryRepository.updateCategoriesName(id, categoryEditionDto);
    }
    @Transactional
    @Override
    public void deleteCategoryById(Integer id) {
        if (!categoryRepository.existsById(id))
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Category not Found"
            );
        categoryRepository.removeById(id);
    }
    @Transactional
    @Override
    public void disableCategory(Integer id) {
        if (!categoryRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found");
        }
        categoryRepository.updateIsDeletedById(id);
    }

}
