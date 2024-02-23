package com.springboot.elearningmanagementstructurebyfeature.instructor;

import com.springboot.elearningmanagementstructurebyfeature.instructor.dto.InstructorCreationDto;
import com.springboot.elearningmanagementstructurebyfeature.instructor.dto.InstructorDto;
import com.springboot.elearningmanagementstructurebyfeature.instructor.dto.InstructorEditionDto;

import java.util.List;

public interface InstructorService {

    InstructorDto findByIdAndNationalIdCard(Integer id, String nationalIdCard);

    List<InstructorDto> search(String familyName, String givenName, String biography);

    InstructorDto findById(Integer id);

    void deleteById(Integer id);

    InstructorDto editById(Integer id, InstructorEditionDto instructorEditionDto);
    void createNew (InstructorCreationDto instructorCreationDto);
    InstructorDto findbyIdandNationalidCard(Integer id, String nationalid);
    List<InstructorDto> findList(String q);
}
