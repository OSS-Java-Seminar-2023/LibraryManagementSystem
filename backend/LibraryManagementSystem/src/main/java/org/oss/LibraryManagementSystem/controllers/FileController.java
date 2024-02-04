package org.oss.LibraryManagementSystem.controllers;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.models.File;
import org.oss.LibraryManagementSystem.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileService.store(file);
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully:");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file");
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<?> downloadImage(@PathVariable UUID id) throws IOException {
        File imageFile = fileService.getFile(id);
        Resource file = fileService.downloadFile(id);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageFile.getType()))
                .body(file.getContentAsByteArray());
    }

    @GetMapping("/{id}")
    public File getFile(@PathVariable UUID id) {
        return fileService.getFile(id);
    }

    @GetMapping("/{id}/delete")
    public void deleteFile(@PathVariable UUID id) {
        fileService.deleteFile(id);
    }
}
