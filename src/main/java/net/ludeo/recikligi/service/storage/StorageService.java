package net.ludeo.recikligi.service.storage;

import lombok.Setter;
import net.ludeo.recikligi.service.LocalizedMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Setter
@Service
public class StorageService {

    @Value("${recikligi.storageFolder}")
    private String storageFolder;

    private final ImageNamingService imageNamingService;

    private final LocalizedMessagesService localizedMessagesService;

    @Autowired
    public StorageService(ImageNamingService imageNamingService,
            LocalizedMessagesService localizedMessagesService) {
        this.imageNamingService = imageNamingService;
        this.localizedMessagesService = localizedMessagesService;
    }

    public UUID store(final MultipartFile imageFile) throws StorageFailedException {
        UUID imageId = UUID.randomUUID();
        store(imageFile, imageId);
        return imageId;
    }

    private void store(final MultipartFile imageFile, UUID imageId) throws StorageFailedException {
        try {
            Path image = getPathToImage(imageId, ImageVersion.ORIGINAL);
            imageFile.transferTo(image.toFile());
        } catch (final IOException ex) {
            String msg = localizedMessagesService.getMessage("error.msg.could.not.store.file", imageFile.getOriginalFilename());
            throw new StorageFailedException(msg, ex);
        }
    }

    public void store(final StorageWriter writeToStorage, final String imageId,
            final ImageVersion imageVersion) throws StorageFailedException {
        try {
            Path pathToImage = getPathToImage(imageId, imageVersion);
            try (OutputStream outputStream = Files.newOutputStream(pathToImage)) {
                writeToStorage.accept(outputStream);
            }
        } catch (final IOException ex) {
            String msg = localizedMessagesService.getMessage("error.msg.could.not.store.file", imageVersion.name());
            throw new StorageFailedException(msg, ex);
        }
    }

    public Path read(final UUID imageId) throws ImageNotFoundException {
        return read(imageId, ImageVersion.ORIGINAL);
    }

    private Path read(final UUID imageId, final ImageVersion imageVersion) throws ImageNotFoundException {
        return read(imageId.toString(), imageVersion);
    }

    public Path read(final String imageId, final ImageVersion imageVersion) throws ImageNotFoundException {
        Path image = getPathToImage(imageId, imageVersion);
        if (!Files.isRegularFile(image)) {
            String msg = localizedMessagesService.getMessage("error.msg.could.not.find.image", imageId, imageVersion.name());
            throw new ImageNotFoundException(msg);
        }
        return image;
    }

    private Path getPathToImage(final UUID imageId, final ImageVersion imageVersion) {
        return getPathToImage(imageId.toString(), imageVersion);
    }

    private Path getPathToImage(final String imageId, final ImageVersion imageVersion) {
        Path folder = Paths.get(storageFolder).toAbsolutePath();
        String imageName = imageNamingService.buildImageName(imageId, imageVersion);
        return folder.resolve(imageName);
    }
}
