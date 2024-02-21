package com.springboot.elearningmanagementstructurebyfeature.instructor;

import com.springboot.elearningmanagementstructurebyfeature.course.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "instructors" )
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String familyName;
    private String givenName;

    private Boolean isDeleted;

    @Column(length = 200, unique = true, nullable = false)
    private String nationalIdCard;

    @Column(columnDefinition = "TEXT")
    private String biography;


    @OneToMany(mappedBy = "instructor")
    private List<Course> courses;

}

