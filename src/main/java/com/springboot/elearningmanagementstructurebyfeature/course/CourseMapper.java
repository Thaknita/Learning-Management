package com.springboot.elearningmanagementstructurebyfeature.course;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    List<CourseDto> toCourseListDto(List<Course> courses);

    CourseDto toCourseDto (Course course);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "instructorId", target = "instructor.id")
    Course fromCourseCreationDto(CourseCreationDto  courseCreationDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromCourseEditionDto (@MappingTarget Course course, CourseEditionDto courseEditionDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromCourseDisableDto (@MappingTarget Course course, CourseDisableDto courseDisableDto);


}

