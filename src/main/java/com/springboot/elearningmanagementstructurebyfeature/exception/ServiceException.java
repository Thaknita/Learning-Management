package com.springboot.elearningmanagementstructurebyfeature.exception;

import com.springboot.elearningmanagementstructurebyfeature.base.BasedError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryNotEmptyException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ServiceException {
    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleService(ResponseStatusException e){
        BasedError<?> basedError = BasedError.builder()
                .code(777)
                .timestamp(LocalDateTime.now())
                .message("Something went wrong")
                .error(e.getMessage())
                .build();

        return ResponseEntity.status(e.getStatusCode())
                .body(basedError);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotFoundException.class)
    BasedError<?> handleFileNotFound(FileNotFoundException e) {
        return BasedError.builder()
                .code(740)
                .timestamp(LocalDateTime.now())
                .message("Something went wrong!")
                .error(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler(DirectoryNotEmptyException.class)
    BasedError<?> handleDirectoryNotEmpty(DirectoryNotEmptyException e){
        return BasedError.builder()
                .code(750)
                .timestamp(LocalDateTime.now())
                .message("Something went wrong!")
                .error(e.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler(MalformedURLException.class)
    BasedError<?> handleMalformURL(MalformedURLException e){
        return BasedError.builder()
                .code(760)
                .timestamp(LocalDateTime.now())
                .message("Something went wrong!")
                .error(e.getLocalizedMessage())
                .build();
    }

}
