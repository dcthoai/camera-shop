package com.shop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class MediaFileService {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.dir}")
    private String uploadDir;

    public boolean isUploadFile(MultipartFile file) {
        return file != null && !file.isEmpty();
    }

    public boolean isUploadFile(MultipartFile[] files) {
        return files != null && files.length > 0;
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + extension;
    }

    public String saveFile(MultipartFile file) throws Exception {
        // Check that the file from the client request is not empty
        if (isUploadFile(file) && file.getOriginalFilename() != null) {
            try {
                String fileName = generateUniqueFileName(file.getOriginalFilename());
                String relativePath = uploadDir + File.separator + fileName;
                String absolutePath = uploadPath + File.separator + relativePath;

                // Save file to path
                File newFile = new File(absolutePath);
                file.transferTo(newFile);

                return relativePath;
            } catch (IOException e) {
                throw new IOException("IOException: " + e.getMessage());
            } catch (RuntimeException e) {
                throw new RuntimeException("RuntimeException: " + e.getMessage());
            } catch (Exception e) {
                throw new Exception("Exception: " + e.getMessage());
            }
        }

        return null;
    }

    public boolean deleteFile(String filePath) {
        if (filePath == null || filePath.isEmpty())
            return false;

        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String path = uploadPath + File.separator + fileName;
        File file = new File(path);

        if (file.exists())
            return file.delete();   // True if delete success

        return false;
    }
}