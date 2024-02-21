package com.springboot.elearningmanagementstructurebyfeature.files;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {

    List<FileDto> toFileListDto(List<FileDto> files);
}
