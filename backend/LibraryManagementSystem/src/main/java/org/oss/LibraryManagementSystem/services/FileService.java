package org.oss.LibraryManagementSystem.services;

import org.oss.LibraryManagementSystem.models.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileService {
    File store(MultipartFile file);

    Resource downloadFile(UUID id);
    File getFile(UUID id);

    void deleteFile(UUID id);
}
