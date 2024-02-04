package org.oss.LibraryManagementSystem.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.oss.LibraryManagementSystem.models.File;
import org.oss.LibraryManagementSystem.repositories.FileRepository;
import org.oss.LibraryManagementSystem.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Value("${file.path}")
    private String filePath;

    @Override
    public File store(MultipartFile file) {
        String dir = System.getProperty("user.dir") + "/" + filePath;
        UUID uuid = UUID.randomUUID();
        try {
            String fileName = uuid.toString() + '.' + file.getContentType().split("/")[1];
            file.transferTo(new java.io.File(dir + "/" + fileName));

            File newFile = new File(fileName, file.getContentType(), dir + '/' + fileName);
            fileRepository.save(newFile);
            return newFile;
        } catch (IOException e) {
           throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Resource downloadFile(UUID id) {
        String dir = System.getProperty("user.dir") + "/" + filePath;
        File file = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
        Path path = Paths.get(dir + "/" + file.getName());
        try {
            Resource resource = new UrlResource(path.toUri());
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public File getFile(UUID id) {
        return fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
    }

    @Override
    public void deleteFile(UUID id) {
        File file = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));

        // Delete file from uploads
        String dir = System.getProperty("user.dir") + "/" + filePath;
        java.io.File fileToDelete = new java.io.File(dir + "/" + file.getName());
        fileToDelete.delete();

        // Delete from db
        fileRepository.delete(file);
    }
}
