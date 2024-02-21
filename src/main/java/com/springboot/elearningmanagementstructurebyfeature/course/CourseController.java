package com.springboot.elearningmanagementstructurebyfeature.course;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/v1/courses")
    @RequiredArgsConstructor
    public class CourseController {

        private final CourseService courseService;

        @GetMapping
        List<CourseDto> findList() {
            return courseService.findList();
        }

        @PostMapping
        void createNew(@Valid @RequestBody CourseCreationDto courseCreationDto){
            System.out.println("REQUEST BODY: " + courseCreationDto);
            courseService.creatNew(courseCreationDto);
        }
        @GetMapping("/{id}")
        CourseDto findCourseById(@PathVariable Long id) {
            return courseService.findCourseById(id);
        }

        @PutMapping("/{id}")
        void editCourseById (@PathVariable Long id, @RequestBody CourseEditionDto courseEditionDto){

            courseService.editCourseById(id, courseEditionDto);

        }

        @DeleteMapping("/{id}")
        void deletedById (@PathVariable Long id){
            courseService.deleteCourseById(id);
        }

        @GetMapping("/search")
        List<CourseDto> search(@RequestParam (required = false, defaultValue = "") String title,
                               @RequestParam (required = false, defaultValue = "") String description)
        {

            return courseService.searchCourse(title, description);
        }

        @PutMapping("/{id}/disable")
        void disableCourseById(@PathVariable Long id){
            courseService.disableCourseById(id);
        }


    }


