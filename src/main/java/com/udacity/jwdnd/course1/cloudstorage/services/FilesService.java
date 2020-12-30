package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class FilesService {

    // fields as FileMapper, UserService:
    private FileMapper fileMapper;

    // constructor:
    public FilesService(FileMapper fMapper) {
        this.fileMapper = fMapper;
    }

    @PostConstruct
    public void postConstruct() {
        System.out.print("Creating FileService bean");
    }

    // find all files from File db by userId:
    public List<File> getAllFiles(int userId) {
        return this.fileMapper.getFilesByUserId(userId);
    }

    // upload new file to use in FilesController:
    public int uploadFile(MultipartFile mFile, int userId) throws IOException {

        // Convert the uploaded file under MultipartFile into FileForm
        // before storing to db:
        // filename, contenttype, filesize, userid, filedata
        File file = new File(null, mFile.getOriginalFilename(),
                mFile.getContentType(), mFile.getSize(), userId, mFile.getBytes());

        return this.fileMapper.uploadFileById(file);
    }

    // delete a file:
    public int deleteFile(int fileId) {
        return this.fileMapper.deleteFileById(fileId);
    }

    // check if a file name already exists in Files DB or not:
    public boolean isFileNameAvailable(int userId, String fileName) {
        return this.fileMapper.getFileByName(userId, fileName) == null;
    }

    // GET a file by userId and fileName:
    public File getFileByName(int userId, String fileName) {
        return this.fileMapper.getFileByName(userId, fileName);
    }
}
