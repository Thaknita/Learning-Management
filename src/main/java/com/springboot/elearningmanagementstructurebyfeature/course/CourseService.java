package com.springboot.elearningmanagementstructurebyfeature.course;

import java.util.List;

public interface CourseService {
    List<CourseDto> findList();

    void creatNew (CourseCreationDto courseCreationDto);

    void editCourseById(Long id, CourseEditionDto courseEditionDto);

    CourseDto findCourseById(Long id);

    void deleteCourseById(Long id);

    void disableCourseById(Long id);



    List<CourseDto> searchCourse(String title, String description);

}
