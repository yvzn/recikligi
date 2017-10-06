package net.ludeo.recikligi.service.graphics;

import lombok.Setter;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.Rendering;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;
import net.ludeo.recikligi.service.LocalizedMessagesService;
import net.ludeo.recikligi.service.storage.ImageVersion;
import net.ludeo.recikligi.service.storage.StorageFailedException;
import net.ludeo.recikligi.service.storage.StorageService;
import net.ludeo.recikligi.service.storage.StorageStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Setter
@Service
public class ImageResizeService {

    @Value("${recikligi.image.resize.display.width}")
    private int displayMaxWidth;

    @Value("${recikligi.image.resize.recognition.width-or-height}")
    private int recognitionMaxWidthOrHeight;

    private final ImageControlService imageControlService;

    private final StorageService storageService;

    private final LocalizedMessagesService localizedMessagesService;

    @Autowired
    public ImageResizeService(ImageControlService imageControlService,
            StorageService storageService,
            LocalizedMessagesService localizedMessagesService) {
        this.imageControlService = imageControlService;
        this.storageService = storageService;
        this.localizedMessagesService = localizedMessagesService;
    }

    public void resize(Path pathToImage) throws ImageResizingException {
        try {
            String outputFormat = findOutputFormat(pathToImage);
            resizeForRecognition(pathToImage, outputFormat);
            resizeForDisplay(pathToImage, outputFormat);
        } catch (InvalidImageFormatException | StorageFailedException ex) {
            String msg = localizedMessagesService.getMessage("error.msg.could.not.resize.file", pathToImage.getFileName());
            throw new ImageResizingException(msg, ex);
        }
    }

    void resizeForRecognition(Path pathToImage,
            String outputFormat) throws InvalidImageFormatException, StorageFailedException {
        StorageStreamWriter storageStreamWriter =
                outputStream -> {
                    if (isImageLargerThan(pathToImage, recognitionMaxWidthOrHeight, recognitionMaxWidthOrHeight))
                        thumbnailBuilder(pathToImage)
                                .size(recognitionMaxWidthOrHeight, recognitionMaxWidthOrHeight)
                                .outputFormat(outputFormat)
                                .toOutputStream(outputStream);
                    else
                        copyFile(pathToImage, outputStream);
                };

        String imageId = pathToImage.getFileName().toString();
        storageService.store(storageStreamWriter, imageId, ImageVersion.RECOGNITION);
    }

    private void resizeForDisplay(Path pathToImage,
            String outputFormat) throws InvalidImageFormatException, StorageFailedException {
        StorageStreamWriter storageStreamWriter =
                outputStream -> {
                    if (isImageLargerThan(pathToImage, displayMaxWidth, -1))
                        thumbnailBuilder(pathToImage)
                                .width(displayMaxWidth)
                                .outputFormat(outputFormat)
                                .toOutputStream(outputStream);
                    else
                        copyFile(pathToImage, outputStream);
                };

        String imageId = pathToImage.getFileName().toString();
        storageService.store(storageStreamWriter, imageId, ImageVersion.DISPLAY);
    }

    private boolean isImageLargerThan(Path pathToImage, int width, int height) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(pathToImage.toFile());
        return (width > 0 && bufferedImage.getWidth() > width)
                ||
                (height > 0 && bufferedImage.getHeight() > height);
    }

    private Thumbnails.Builder<File> thumbnailBuilder(Path pathToImage) {
        return Thumbnails.of(pathToImage.toFile())
                .scalingMode(ScalingMode.BICUBIC)
                .rendering(Rendering.QUALITY);
    }

    private void copyFile(Path sourceImage, OutputStream destOutputStream) throws IOException {
        Files.copy(sourceImage, destOutputStream);
    }

    private String findOutputFormat(Path pathToImage) throws InvalidImageFormatException {
        return imageControlService.findImageFormat(pathToImage).name().toLowerCase();
    }
}
