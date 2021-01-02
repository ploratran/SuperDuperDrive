package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.dto.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    // fields include all services:
    private UserService userService;
    private NotesService notesService;
    private FilesService fileService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    // constructor:
    public HomeController(UserService uService, NotesService nService, FilesService fService,
                          CredentialService cService, EncryptionService eService) {
        this.userService = uService;
        this.notesService = nService;
        this.fileService = fService;
        this.credentialService = cService;
        this.encryptionService = eService;
    }

    // initialize variables:
    private List<Note> noteList = new ArrayList<>();
    private List<File> fileList = new ArrayList<>();
    private List<Credential> credentialList = new ArrayList<>();

    // include getFileDTO() in FilesController to avoid BindingResultError error:
    @ModelAttribute("fileDTO")
    public FileDTO getFileDTO() {
        return new FileDTO();
    }

    // include NoteDTO()
    @ModelAttribute("noteDTO")
    public NoteDTO getNoteDTO() {
        return new NoteDTO();
    }

    @GetMapping
    public String getHomePage(Authentication auth, Model model) {

        // get userId using UserService and Authentication by username:
        int userId = this.userService.getUserById(auth.getName());

        // use NotesService to get all notes from Notes db by userId:
        noteList = this.notesService.getAllNotes(userId);
        // use FilesService to get all files from Files db by userId:
        fileList = this.fileService.getAllFiles(userId);
        // use CredentialService to get all credentials from Credential db by userId:
        credentialList = this.credentialService.getAllCredentials(userId);

        // display to View layer where th:object=${notes}
        model.addAttribute("notes", noteList);
        model.addAttribute("files", fileList);
        model.addAttribute("creds", credentialList);
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }
}