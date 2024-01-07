package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.models.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileService {
    File store(MultipartFile file) throws IOException;

    byte[] downloadImage(UUID id);

    void deleteFile(UUID id);
    File getFile(UUID id);
}
