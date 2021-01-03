package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    // GET all notes from Notes db by userId:
    @Results({
            @Result(id = true, property = "noteId", column = "noteid"),
            @Result(property = "title", column = "notetitle"),
            @Result(property = "description", column = "notedescription"),
            @Result(property = "userId", column = "userid")
    })
    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> findNotesByUser(int userId);

    // INSERT/ADD new note into Notes db by specific noteId:
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
    "VALUES(#{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNoteByNoteId(Note note);

    // EDIT/UPDATE note title and note description by noteId:
    @Update("UPDATE NOTES SET notetitle=#{title}, notedescription=#{description}" +
    "WHERE noteid=#{noteId}")
    int updateNoteById(Note note);

    // DELETE note by noteId:
    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId}")
    int deleteNoteById(int noteId);
}