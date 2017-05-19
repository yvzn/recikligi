package net.ludeo.recikligi.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
            Path image = getPathToImage(imageId);
            imageFile.transferTo(image.toFile());
            return imageId;
        } catch (final IOException ex) {
            throw new StorageFailedException(
                    String.format("Could not store uploaded file %s", imageFile.getOriginalFilename()), ex);
        }
    }

    public Path read(final UUID imageId) throws ImageNotFoundException {
        Path image = getPathToImage(imageId);
        if (!Files.isRegularFile(image)) {
            throw new ImageNotFoundException(String.format("Could not find image %s", imageId.toString()));
        }
        return image;
    }

    private Path getPathToImage(UUID imageId) {
        Path folder = Paths.get(storageFolder).toAbsolutePath();
        return folder.resolve(imageId.toString());
    }
}
