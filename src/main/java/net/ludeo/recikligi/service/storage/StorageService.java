package net.ludeo.recikligi.service.storage;

import lombok.Setter;
import net.ludeo.recikligi.service.LocalizedMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final LocalizedMessagesService localizedMessagesService;

    @Autowired
    public StorageService(LocalizedMessagesService localizedMessagesService) {
        this.localizedMessagesService = localizedMessagesService;
    }

    public UUID store(final MultipartFile imageFile) throws StorageFailedException {
        try {
            UUID imageId = UUID.randomUUID();
            Path image = getPathToImage(imageId);
            imageFile.transferTo(image.toFile());
            return imageId;
        } catch (final IOException ex) {
            String msg = localizedMessagesService.getMessage("error.msg.could.not.store.file", imageFile.getOriginalFilename());
            throw new StorageFailedException(msg, ex);
        }
    }

    public Path read(final UUID imageId) throws ImageNotFoundException {
        Path image = getPathToImage(imageId);
        if (!Files.isRegularFile(image)) {
            String msg = localizedMessagesService.getMessage("error.msg.could.not.find.image", imageId.toString());
            throw new ImageNotFoundException(msg);
        }
        return image;
    }

    public Path read(final String imageId) throws ImageNotFoundException {
        try {
            return read(UUID.fromString(imageId));
        } catch (final IllegalArgumentException | UnsupportedOperationException ex) {
            String msg = localizedMessagesService.getMessage("error.msg.could.not.find.image", imageId);
            throw new ImageNotFoundException(msg, ex);
        }
    }

    private Path getPathToImage(UUID imageId) {
        Path folder = Paths.get(storageFolder).toAbsolutePath();
        return folder.resolve(imageId.toString());
    }
}
