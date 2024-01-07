package org.oss.LibraryManagementSystem.controllers;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.models.File;
import org.oss.LibraryManagementSystem.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FIleController {
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println(file);
        try {
            fileService.store(file);
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully: \" + file.getOriginalFilename()");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("\"Could not upload the file: \" + file.getOriginalFilename() + \"!\";");
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<?> downloadImage(@PathVariable UUID id){
        File imageFile = fileService.getFile(id);
        byte[] imageData=fileService.downloadImage(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageFile.getType()))
                .body(imageData);
    }
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable UUID id) {
        File fileDb = fileService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDb.getName() + "\"")
                .body(fileDb.getData());
    }
}
