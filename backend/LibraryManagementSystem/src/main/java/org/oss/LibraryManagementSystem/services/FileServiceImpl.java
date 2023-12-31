package org.oss.LibraryManagementSystem.services;

import lombok.AllArgsConstructor;
import org.oss.LibraryManagementSystem.models.File;
import org.oss.LibraryManagementSystem.repositories.FileRepository;
import org.oss.LibraryManagementSystem.utils.ImageUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService{
    private final FileRepository fileRepository;

    @Override
    public File store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileDb = new File(fileName, file.getContentType(), ImageUtils.compressImage(file.getBytes()));
        return fileRepository.save(fileDb);
    }

    public byte[] downloadImage(UUID id){
        File dbImageData = fileRepository.findById(id).get();
        byte[] images=ImageUtils.decompressImage(dbImageData.getData());
        return images;
    }

    @Override
    public void deleteFile(UUID id) {
        File file = fileRepository.findById(id).orElseThrow(() -> new RuntimeException("File not found"));
        fileRepository.delete(file);
    }

    @Override
    public File getFile(UUID id) {
        return fileRepository.findById(id).get();
    }
}
