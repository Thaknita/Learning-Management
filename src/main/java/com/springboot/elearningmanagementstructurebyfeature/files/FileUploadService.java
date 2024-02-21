package com.springboot.elearningmanagementstructurebyfeature.files;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



public interface FileUploadService {

    FileDto uploadSingle(MultipartFile file);

    List<FileDto> uploadMultiple(List<MultipartFile> files );

    FileDto findFileByName(String name) throws IOException;

    List<FileDto> listAllFiles();

    void deleteFilesByName(String name) throws IOException;

    void deleteAllFile() throws IOException;

    ResponseEntity<Resource> downloadByName(String name) throws IOException;
}
