package com.springboot.elearningmanagementstructurebyfeature.instructor;

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
