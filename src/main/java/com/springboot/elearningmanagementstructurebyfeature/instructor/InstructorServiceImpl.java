package com.springboot.elearningmanagementstructurebyfeature.instructor;

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
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;

    private<T> Predicate<T> distictByKey(

            Function<? super T, ?> keyExtractor){
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t),
                Boolean.TRUE)== null;
    }

    public List<InstructorDto> search(String familyName, String givenName, String biography){
        List<Instructor> instructors = new ArrayList<>();
        if (!familyName.isBlank()){
            List<Instructor> instructorByFamilyName = instructorRepository.findByFamilyNameIgnoreCase(familyName);
            instructors.addAll(instructorByFamilyName);
        }

        if (!givenName.isBlank()){
            List<Instructor> instructorByGivenName = instructorRepository.findByGivenNameIgnoreCase(givenName);
            instructors.addAll(instructorByGivenName);
        }

        if (!biography.isBlank()){
            List<Instructor> instructorByBiography = instructorRepository.findByBiographyContainsIgnoreCase(biography);
            instructors.addAll(instructorByBiography);
        }
        instructors = instructors.stream()
                .filter(distictByKey(Instructor::getId))
                .toList();
        return instructorMapper.toInstructorListDto(instructors);
    }
    @Override
    public InstructorDto findByIdAndNationalIdCard(Integer id, String nationalIdCard) {

        Instructor instructor = instructorRepository
                .findByIdAndNationalIdCard(id,nationalIdCard)
                .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No ID has been found")
                );
        return instructorMapper.toInstructorDto(instructor);

    }

    @Override
    public InstructorDto findById(Integer id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Instructor has not been found"
                ));

        return instructorMapper.toInstructorDto(instructor);
    }

    @Override
    public void deleteById(Integer id) {
        if(!instructorRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID NOT FOUND");
        }
        instructorRepository.deleteById(id);

    }

    @Override
    public InstructorDto editById(Integer id, InstructorEditionDto instructorEditionDto) {
      Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No ID has been found"));

         instructorMapper.fromInstrucorEditionDto(instructor, instructorEditionDto);
         instructorRepository.save(instructor);
         return this.findById(id);
    }

    @Override
    public void createNew(InstructorCreationDto instructorCreationDto) {

        Instructor instructor = instructorMapper.fromInstructorCreationDto(instructorCreationDto);
        instructorRepository.save(instructor);
    }

    @Override
    public InstructorDto findbyIdandNationalidCard(Integer id, String nationalId) {
        Instructor instructor = instructorRepository
                .findByIdAndNationalIdCard(id,nationalId)
                .orElseThrow(
                        ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No ID has been found")
                );
        return instructorMapper.toInstructorDto(instructor);
    }
    @Transactional
    @Override
    public List<InstructorDto> findList(String q) {

         List<Instructor> instructors = instructorRepository.findAll();
         return instructorMapper.toInstructorListDto(instructors);

    }

}

