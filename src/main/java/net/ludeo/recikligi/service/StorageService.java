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
public class StorageService extends LocalizedMessagesService {

    @Value("${recikligi.storageFolder}")
    private String storageFolder;

    public UUID store(final MultipartFile imageFile) throws StorageFailedException {
        try {
            UUID imageId = UUID.randomUUID();
            Path image = getPathToImage(imageId);
            imageFile.transferTo(image.toFile());
            return imageId;
        } catch (final IOException ex) {
            String msg = getMessage("error.msg.could.not.store.file", imageFile.getOriginalFilename());
            throw new StorageFailedException(msg, ex);
        }
    }

    public Path read(final UUID imageId) throws ImageNotFoundException {
        Path image = getPathToImage(imageId);
        if (!Files.isRegularFile(image)) {
            String msg = getMessage("error.msg.could.not.find.image", imageId.toString());
            throw new ImageNotFoundException(msg);
        }
        return image;
    }

    private Path getPathToImage(UUID imageId) {
        Path folder = Paths.get(storageFolder).toAbsolutePath();
        return folder.resolve(imageId.toString());
    }
}
