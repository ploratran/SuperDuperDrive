package com.udacity.jwdnd.course1.cloudstorage.controller;

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

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NotesController bean");
    }

    @PostMapping("/home/note/newNote")
    public String postNewNote(@ModelAttribute("newNote") Note note, Authentication auth, Model model) {

        String errorMessage = null;

        // use userMapper to get a specific user by username:
        // using Spring Authentication to get username:
        // return current userId:
        int currentUserId = this.userService.getUserById(auth.getName());

        // if there are no error, add note based on currentUserId:
        if (errorMessage == null) {
            // set a specific note to current user by userId:
            note.setUserId(currentUserId);
            // add note to Notes db by noteId:
            int currentNoteId = this.noteService.addNote(note);

            // check if noteId has error, inform error:
            if (currentNoteId < 0) {
                errorMessage = "There was error adding new note!";
            }
        }

        // show result.html page with success/fail message:
        if (errorMessage == null) {
            model.addAttribute("updateSuccess", true);
        } else {
            model.addAttribute("updateFail", errorMessage);
        }

        return "result";
    }

    // delete a note based on noteId:
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