package com.springboot.elearningmanagementstructurebyfeature.files;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FilesController {

    private  final FileUploadService fileUploadService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/single", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FileDto singleFileUpload(@RequestPart MultipartFile file) {

     return   fileUploadService.uploadSingle(file);

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/multi", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<FileDto> uploadMultiple(@RequestPart List<MultipartFile> files) {
        return  fileUploadService.uploadMultiple(files);
    }

    @GetMapping("/{name}")
    FileDto findFileByName(@PathVariable String name) throws IOException {
        return fileUploadService.findFileByName(name);
    }


    @GetMapping
    List<FileDto> getFilesList(){
        return fileUploadService.listAllFiles();
    }


    @DeleteMapping("/{name}")
    void deleteFileByName(@PathVariable String name) throws IOException {

        fileUploadService.deleteFilesByName(name);

    }

    @DeleteMapping
    void deleteAllFiles() throws IOException {
        fileUploadService.deleteAllFile();
    }

    @GetMapping("/{name}/download")
    ResponseEntity<Resource> downloadByName(@PathVariable String name) throws IOException {
        return fileUploadService.downloadByName(name);
    }


}




