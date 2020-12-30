package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

// Service class to manage Note services:
@Service
public class NotesService {
    // use MyBatis mapper to access to database:
    private NotesMapper notesMapper;

    // constructor:
    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    /** methods: */

    @PostConstruct
    public void postConstruct() {
        System.out.println("Creating NotesService bean");
    }

    // get all notes from Notes db:
    // use in HomeController:
    public List<Note> getAllNotes(int userId) {
        return notesMapper.findNotesByUser(userId);
    }

    // add or update a note from Notes db:
    // use in NotesController:
    public int addNote(Note note) {
        // check if a noteId is null, then add new note by noteId:
        if (note.getNoteId() == null) {
            return this.notesMapper.addNoteByNoteId(note);
        }
        // if noteId is NOT null, meaning it exists in Notes db
        // then update note with new title and description:
        else {
            return this.notesMapper.updateNoteById(note);
        }
    }

    // delete note:
    public int deleteNote(int noteId) {
        return notesMapper.deleteNoteById(noteId);
    }
}
