package com.udacity.jwdnd.course1.cloudstorage.dto;

public class NoteDTO {

    private Integer noteID;
    private String noteTitle;
    private String noteDescription;

    // constructor
    public NoteDTO() {}

    // getters and setters:
    public Integer getNoteID() {
        return noteID;
    }
    public void setNoteID(Integer noteID) {
        this.noteID = noteID;
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