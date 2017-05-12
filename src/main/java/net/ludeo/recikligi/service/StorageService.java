package net.ludeo.recikligi.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@Setter
@Service
public class StorageService {

    @Value("${recikligi.storageFolder}")
    private String storageFolder;

    public UUID store(final MultipartFile imageFile) throws StorageFailedException {
        try {
            UUID imageId = UUID.randomUUID();

            File folder = Paths.get(storageFolder).toFile().getAbsoluteFile();
            File file = new File(folder, imageId.toString());

            imageFile.transferTo(file);
            return imageId;
        } catch (final IOException ex) {
            throw new StorageFailedException("Could not store uploaded file " + imageFile.getOriginalFilename(), ex);
        }
    }
}
