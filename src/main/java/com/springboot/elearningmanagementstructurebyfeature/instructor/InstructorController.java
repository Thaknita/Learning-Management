package com.springboot.elearningmanagementstructurebyfeature.instructor;

import com.springboot.elearningmanagementstructurebyfeature.instructor.dto.InstructorCreationDto;
import com.springboot.elearningmanagementstructurebyfeature.instructor.dto.InstructorDto;
import com.springboot.elearningmanagementstructurebyfeature.instructor.dto.InstructorEditionDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instructors")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;


    @GetMapping("/search")
    List<InstructorDto> search(@RequestParam (required = false, defaultValue = "") String familyName,
                               @RequestParam (required = false, defaultValue = "") String givenName,
                               @RequestParam (required = false, defaultValue = "") String biography){

        return instructorService.search(familyName, givenName, biography);
    }

    @PostMapping
    void createNew(@Valid @RequestBody InstructorCreationDto instructorCreationDto){
        System.out.println("REQUEST BODY: " + instructorCreationDto);
        instructorService.createNew(instructorCreationDto);
    }

    @GetMapping("/{id}/{nationalIdCard}")
    InstructorDto findById(@PathVariable  Integer id, @PathVariable  String nationalIdCard){
        System.out.println("path variable ID: " + id);
        return instructorService.findbyIdandNationalidCard(id, nationalIdCard);
    }

    @GetMapping
    List<InstructorDto> findList(@RequestParam(required = false, defaultValue = "") String q){
        System.out.println("Request Param:" + q);
        return instructorService.findList(q);

    }
    @PatchMapping("/{id}")
    void editById(@PathVariable Integer id, @RequestBody InstructorEditionDto instructorEditionDto){

        System.out.println("id and data"+instructorEditionDto);
        instructorService.editById(id, instructorEditionDto);
    }
    @GetMapping("/{id}")
    InstructorDto findById(@PathVariable Integer id){
        return instructorService.findById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id){
        instructorService.deleteById(id);
    }

}


