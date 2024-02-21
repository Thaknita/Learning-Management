package com.springboot.elearningmanagementstructurebyfeature.base;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasedError<T> {
    private String message;
    private LocalDateTime timestamp;
    private Integer code;
    private  T error;
}
