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

    private final Base64FileWriter base64FileWriter;

    @Autowired
    public StorageService(ImageNamingService imageNamingService,
            LocalizedMessagesService localizedMessagesService,
            Base64FileWriter base64FileWriter) {
        this.imageNamingService = imageNamingService;
        this.localizedMessagesService = localizedMessagesService;
        this.base64FileWriter = base64FileWriter;
    }

    public UUID store(final MultipartFile imageFile) throws StorageFailedException {
        return storeWithNewId(
                path -> imageFile.transferTo(path.toFile()));
    }

    public UUID store(String base64image) throws StorageFailedException {
        return storeWithNewId(
                path -> base64FileWriter.write(base64image, path));
    }

    private UUID storeWithNewId(StorageFileWriter storageFileWriter) throws StorageFailedException {
        UUID imageId = UUID.randomUUID();
        store(storageFileWriter, imageId);
        return imageId;
    }

    private void store(final StorageFileWriter storageFileWriter, UUID imageId) throws StorageFailedException {
        try {
            Path image = getPathToImage(imageId, ImageVersion.ORIGINAL);
            storageFileWriter.writeTo(image);
        } catch (final IOException ex) {
            String msg = localizedMessagesService.getMessage("error.msg.could.not.store.file");
            throw new StorageFailedException(msg, ex);
        }
    }

    public void store(final StorageStreamWriter storageStreamWriter, final String imageId,
            final ImageVersion imageVersion) throws StorageFailedException {
        try {
            Path pathToImage = getPathToImage(imageId, imageVersion);
            try (OutputStream outputStream = Files.newOutputStream(pathToImage)) {
                storageStreamWriter.writeTo(outputStream);
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
