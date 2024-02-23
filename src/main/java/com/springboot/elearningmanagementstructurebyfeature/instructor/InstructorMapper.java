package com.springboot.elearningmanagementstructurebyfeature.instructor;

import com.springboot.elearningmanagementstructurebyfeature.instructor.dto.InstructorCreationDto;
import com.springboot.elearningmanagementstructurebyfeature.instructor.dto.InstructorDto;
import com.springboot.elearningmanagementstructurebyfeature.instructor.dto.InstructorEditionDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InstructorMapper {
    List<InstructorDto> toInstructorListDto (List<Instructor> instructors);
    InstructorDto toInstructorDto (Instructor instructor);
    Instructor fromInstructorCreationDto(InstructorCreationDto instructorCreationDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromInstrucorEditionDto(@MappingTarget Instructor instructor, InstructorEditionDto instructorEditionDto);
    Instructor fromInstrucorEditionDto(InstructorEditionDto instructorEditionDto);

}
