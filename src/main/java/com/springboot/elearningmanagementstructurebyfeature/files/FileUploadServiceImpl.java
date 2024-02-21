package com.springboot.elearningmanagementstructurebyfeature.files;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file-upload.server-path}")
    private String serverPath;
    @Value("${file-upload.base-uri}")
    private String baseUri;
    @Value("${file-upload.client-path}")
    private String clientPath;
    private final FileMapper fileMapper;
    @Override
    public FileDto uploadSingle(MultipartFile file) {
        //extract file extension
        //get last index of .
        String extension = this.extractExtension(Objects.requireNonNull(file.getOriginalFilename()));
        System.out.println(file.getOriginalFilename());
        //create unique filename
        String newFileName = UUID.randomUUID() +"."+ extension;
        System.out.println(newFileName);
        System.out.println(file.getContentType());
        System.out.println(extension);
        String absolutePath = serverPath + newFileName;
        Path path = Paths.get(absolutePath);

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return FileDto.builder()
                .name(newFileName)
                .extension(extension)
                .size(file.getSize())
                .uri(baseUri + newFileName)
                .build();
    }
    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {

        List<FileDto> fileListDto = new ArrayList<>();

        files.forEach(file -> {
            fileListDto.add(this.uploadSingle(file));
        });

        return fileListDto;
    }

    @Override
    public FileDto findFileByName(String name) throws IOException {
        Path path = Paths.get(serverPath + name);
        Resource res  = UrlResource.from(path.toUri());


        return FileDto.builder()
                .name(res.getFilename())
                .size(res.contentLength())
                .extension(this.extractExtension(Objects.requireNonNull(res.getFilename())))
                .uri(baseUri + res.getFilename())
                .build();
    }

    @Override
    public List<FileDto> listAllFiles() {
        File directory = new File(serverPath);
        File[] files = directory.listFiles();
        assert files != null;

        return Arrays.stream(files)
                 .map(file -> FileDto.builder()
                         .name(file.getName())
                         .extension(this.extractExtension(file.getName()))
                         .size(file.length())
                         .uri(baseUri + file.getName())
                         .build()).toList();

    }

    @Override
    public void deleteFilesByName(String name) throws IOException {
        if (this.findFileByName(name) != null){
            Path path = Paths.get(serverPath + name);
            Files.delete( path);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "FIle not found");
    }

    @Override
    public void deleteAllFile() throws IOException {
        Path path = Paths.get(serverPath);
        FileUtils.cleanDirectory(new File(serverPath));
    }

    @Override
    public ResponseEntity<Resource> downloadByName(String name) throws IOException {

        URL url = new URL(this.findFileByName(name).uri());
        Path tempfile = Files.createTempFile("downloaded", name);
        Files.copy(url.openStream(), tempfile, StandardCopyOption.REPLACE_EXISTING);
        Resource resource = new InputStreamResource(Files.newInputStream(tempfile));

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" +name + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }

    private String extractExtension(String fileName){
        int lastIndexOfDot = fileName.lastIndexOf(".");
        return  fileName.substring(lastIndexOfDot + 1);
    }

}
