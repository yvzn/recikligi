package net.ludeo.recikligi.service.graphics;

import lombok.Setter;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.Rendering;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;
import net.ludeo.recikligi.service.LocalizedMessagesService;
import net.ludeo.recikligi.service.storage.ImageVersion;
import net.ludeo.recikligi.service.storage.StorageFailedException;
import net.ludeo.recikligi.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        storageService.store(
                outputStream ->
                        Thumbnails.of(pathToImage.toFile())
                                .scalingMode(ScalingMode.BICUBIC)
                                .rendering(Rendering.QUALITY)
                                .size(recognitionMaxWidthOrHeight, recognitionMaxWidthOrHeight)
                                .outputFormat(outputFormat)
                                .toOutputStream(outputStream),
                pathToImage.getFileName().toString(),
                ImageVersion.RECOGNITION);
    }

    private void resizeForDisplay(Path pathToImage,
            String outputFormat) throws InvalidImageFormatException, StorageFailedException {
        storageService.store(
                outputStream ->
                        Thumbnails.of(pathToImage.toFile())
                                .scalingMode(ScalingMode.BICUBIC)
                                .rendering(Rendering.QUALITY)
                                .width(displayMaxWidth)
                                .outputFormat(outputFormat)
                                .toOutputStream(outputStream),
                pathToImage.getFileName().toString(),
                ImageVersion.DISPLAY);
    }

    private String findOutputFormat(Path pathToImage) throws InvalidImageFormatException {
        return imageControlService.findImageFormat(pathToImage).name().toLowerCase();
    }
}
