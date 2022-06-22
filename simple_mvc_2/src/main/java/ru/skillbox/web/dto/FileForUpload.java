package ru.skillbox.web.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

public class FileForUpload {

    @NotEmpty
    MultipartFile file;

    public FileForUpload(MultipartFile file) {
        this.file = file;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
