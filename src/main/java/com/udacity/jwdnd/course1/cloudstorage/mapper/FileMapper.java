package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface FileMapper {

    // Get/Display all files from Files db by userId:
    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFilesByUserId(int userId);

    // Add/Upload new file into Files db by fileId:
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFileById(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    int deleteFileById(int fileId);

    // GET a file by its filename and userId:
    @Select("SELECT * FROM FILES WHERE userId = #{userId} and filename = #{fileName}")
    File getFileByName(int userId, String fileName);
}