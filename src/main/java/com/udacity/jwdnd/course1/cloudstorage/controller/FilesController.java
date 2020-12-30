package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FilesController {
    // fields, use FileService, UserService:
    private FilesService filesService;
    private UserService userService;

    // constructor:
    public FilesController(FilesService fService, UserService uService) {
        this.filesService = fService;
        this.userService = uService;
    }

    // create a method getFileDTO() responsible for including FileDTO in home.html
    // so we can use it in the <form /> to get fileDTO object:
    @ModelAttribute("fileDTO")
    public FileDTO getFileDTO() {
        return new FileDTO();
    }

    // use @ModelAttribute("fileDTO") for MultipartFile file property
    // to map incoming data from <form />:
    // POST to upload new file:
    @PostMapping("/home/file/newFile")
    public String uploadNewFile(Authentication auth, Model model,
                                @ModelAttribute("fileDTO") MultipartFile file,
                                RedirectAttributes redirectAttributes) throws IOException {

        String errorMsg = null;

        int currentUserId = this.userService.getUserById(auth.getName());

        System.out.println("Name: " + file.getName());

        // handle edge cases:
        // check when empty file is uploaded:
        if (file.isEmpty()) {
            errorMsg = "File should not be empty!";
        }

        // check if a file name already exists in File DB or not:
        if (!this.filesService.isFileNameAvailable(currentUserId, file.getOriginalFilename())) {
            errorMsg = "This file name already exists. Choose a another name.";
        }

        // check if file size is greater than 128KB, user cannot upload it:
        System.out.println("File Size: " + file.getContentType() + " " + file.getSize());
        if (file.getSize() > 1000000) { // compare file size to bytes value
            errorMsg = "File cannot be greater than 1MB. Choose a lower file.";
        }

        if (errorMsg == null) {
            // upload file to Files DB by fileId:
            // return current fileId if success:
            int currentFileId = this.filesService.uploadFile(file, currentUserId);

            // if there is error, no currentFileId being returned:
            if (currentFileId < 0) {
                errorMsg = "There was error uploading this file!";
            }
        }

        if (errorMsg == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFail", errorMsg);
        }

        return "result";
    }

    // DELETE a file by its fileId:
    @GetMapping("/home/file/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") int fileId, Authentication auth, Model model) {

        String errorMsg = null;

        if (errorMsg == null) {
            // delete a file by its fileId:
            // return successfully deleted fileId:
            int deletedFileId = this.filesService.deleteFile(fileId);

            // if deletedFileId is invalid, display invalid message:
            if (deletedFileId < 1) {
                errorMsg = "There was error deleting this file!";
            }
        }

        if (errorMsg == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFail", errorMsg);
        }

        return "result";
    }

    // VIEW/DOWNLOAD a file by its fileId:
    // using ResponseEntity class to return file by downloading it
    /**
     * Reference: https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/
     * Reference: https://www.baeldung.com/spring-response-entity
     * */
    @GetMapping("/home/file/download/{fileName}")
    public @ResponseBody ResponseEntity viewFile(@PathVariable("fileName") String fileName, Authentication auth) throws IOException {

        // get current userId using Authentication and UserService:
        int currentUserId = this.userService.getUserById(auth.getName());

        // get file from Files DB using userId and fileName via FileService:
        File file = this.filesService.getFileByName(currentUserId, fileName);

        // get filename:
        String fName = file.getFileName();

        // if a file is uploaded (not null):
        if (file != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + fName + "\"")
                    .body(file.getFileData());
        }

        // download the file so no need to return "home" view template:
        return null;
    }
}