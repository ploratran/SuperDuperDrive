package com.udacity.jwdnd.course1.cloudstorage.dto;

public class NoteDTO {

    // fields:
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;

    // constructor:
    public NoteDTO() {}

    // getters and setters:

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }
}
