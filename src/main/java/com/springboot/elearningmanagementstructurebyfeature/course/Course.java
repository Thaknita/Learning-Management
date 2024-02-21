package com.springboot.elearningmanagementstructurebyfeature.course;

import com.springboot.elearningmanagementstructurebyfeature.category.Category;
import com.springboot.elearningmanagementstructurebyfeature.instructor.Instructor;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table( name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String thumbnail;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean isFree;
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "ins_id", referencedColumnName = "id")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "cat_id", referencedColumnName = "id")
    private Category category;
}
