package com.udacity.jwdnd.course1.cloudstorage.dto;

import org.springframework.web.multipart.MultipartFile;

// A class that follows DTO pattern
// transport data from Controller layer to View layer
// serve to transport frontend data (file) sent by user
// through <form /> to backend
// type MultipartFile, or enctype="multipart/form-data"
public class FileDTO {

    // field:
    private MultipartFile file;

    // constructor:
    public FileDTO () {}

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}