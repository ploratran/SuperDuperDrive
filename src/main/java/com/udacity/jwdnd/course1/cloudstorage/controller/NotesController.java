package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.NoteDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;

@Controller
public class NotesController {
    // fields:
    // use UserService, NoteService:
    private UserService userService;
    private NotesService noteService;

    // constructor:
    public NotesController(UserService uService, NotesService nService) {
        this.userService = uService;
        this.noteService = nService;
    }

    // bind noteDTO in frontend:
    @ModelAttribute("noteDTO")
    public NoteDTO getNoteDTO() {
        return new NoteDTO();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NotesController bean");
    }

    @PostMapping("/home/note/newNote")
    public String postNewNote(@ModelAttribute("noteDTO") NoteDTO noteDTO, Authentication auth, Model model) {

        String errMsg = null;

        // use userMapper to get a specific user by username:
        // using Spring Authentication to get username:
        // return current userId:
        int currentUserId = this.userService.getUserById(auth.getName());

        // set note data from Note DTO in thymeleaf object to Note Entity:
        Note note = new Note();

        note.setNoteId(noteDTO.getNoteID());
        note.setTitle(noteDTO.getNoteTitle());
        note.setDescription(noteDTO.getNoteDescription());

        // if there are no error, add new note based on currentUserId:
        if (errMsg == null) {
            // set a current user by userId:
            note.setUserId(currentUserId);
            // add note to Notes db by noteId:
            int currentNoteId = this.noteService.addNote(note);

            // check if noteId has error, inform error:
            if (currentNoteId < 0) {
                errMsg = "There was error adding new note!";
            }
        }

        // show result.html page with success/fail message:
        if (errMsg == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFail", errMsg);
        }

        return "result";
    }

    // delete a note based on noteId path parameter:
    @GetMapping("/home/note/delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") int noteId, Authentication auth, Model model) {

        String errorMessage = null;

        if (errorMessage == null) {
            int deletedNoteId = this.noteService.deleteNote(noteId);

            if (deletedNoteId < 1) {
                errorMessage = "There was error deleting this note!";
            }
        }

        if (errorMessage == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFail", errorMessage);
        }

        return "result";
    }
}