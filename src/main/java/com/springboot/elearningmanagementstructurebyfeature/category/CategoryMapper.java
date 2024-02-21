package com.springboot.elearningmanagementstructurebyfeature.category;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    List<Category> toCategoryListDto (List<Category> categories);

    Category fromCategoryDto (CategoryCreationDto category);

    Category toCategoryDto ( Category category);


}
