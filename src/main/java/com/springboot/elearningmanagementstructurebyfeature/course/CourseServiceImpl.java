package com.springboot.elearningmanagementstructurebyfeature.course;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    private<T> Predicate<T> distictByKey(

            Function<? super T, ?> keyExtractor){
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t),
                Boolean.TRUE)== null;
    }
    @Override
    public List<CourseDto> findList() {
        List<Course> courses = courseRepository.findAllByIsDeletedFalse();
        return courseMapper.toCourseListDto(courses);
    }

    @Override
    public void creatNew(CourseCreationDto courseCreationDto) {



        Course course = courseMapper.fromCourseCreationDto(courseCreationDto);

        course.setIsDeleted(false);

        courseRepository.save(course);

    }

    @Override
    public void editCourseById(Long id, CourseEditionDto courseEditionDto) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course ID not found")
        );
        courseMapper.fromCourseEditionDto(course, courseEditionDto);
        courseRepository.save(course);
        this.findCourseById(id);

    }


    @Override
    public CourseDto findCourseById(Long id) {

        Course course = courseRepository.findByIdAndIsDeletedFalse(id) .orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course has not been found"
                )
        );

        return courseMapper.toCourseDto(course);
    }

    @Override
    public void deleteCourseById(Long id) {
       if (!courseRepository.existsById(id))
           throw new ResponseStatusException(
                   HttpStatus.NOT_FOUND, "Course not Found"
           );
       courseRepository.deleteById(id);
    }
    @Transactional
    @Override
    public void disableCourseById(Long id){
        if (!courseRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"not found");
        }
        courseRepository.updateIsDeletedById(id);
    }


    @Override
    public List<CourseDto> searchCourse(String title, String description) {
        List<Course> courses = new ArrayList<>();

        if (!title.isBlank()){
            List<Course> coursesByTitle = courseRepository.findByTitleContainingIgnoreCase(title);
            courses.addAll(coursesByTitle);

        }

        if (!description.isBlank()){
            List<Course> coursesByDescription = courseRepository.findByDescriptionContainingIgnoreCase(description);
            courses.addAll(coursesByDescription);
        }

        courses = courses.stream()
                .filter(distictByKey(Course::getId)).toList();
        return courseMapper.toCourseListDto(courses);

    }


}


