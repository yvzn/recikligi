package net.ludeo.recikligi.service.graphics;

import lombok.Setter;
import net.coobird.thumbnailator.Thumbnails;
import net.ludeo.recikligi.service.LocalizedMessagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Setter
@Service
public class ImageResizeService {

    public static final String DISPLAY_PREFIX = "display-";

    static final String RECOGNITION_PREFIX = "recognition-";

    private static final Logger logger = LoggerFactory.getLogger(ImageResizeService.class);

    @Value("${recikligi.image.resize.display.width}")
    private int displayMaxWidth;

    @Value("${recikligi.image.resize.display.height}")
    private int displayMaxHeight;

    @Value("${recikligi.image.resize.recognition.size}")
    private int recognitionMaxSize;

    private final LocalizedMessagesService localizedMessagesService;

    private final ImageControlService imageControlService;

    @Autowired
    public ImageResizeService(LocalizedMessagesService localizedMessagesService,
            ImageControlService imageControlService) {
        this.localizedMessagesService = localizedMessagesService;
        this.imageControlService = imageControlService;
    }

    public void resize(Path pathToImage) throws ImageResizingException, InvalidImageFormatException {
        try {
            String outputFormat = findOutputFormat(pathToImage);
            resizeForRecognition(pathToImage, outputFormat);
            resizeForDisplay(pathToImage, outputFormat);
        } catch (IOException ex) {
            String msg = localizedMessagesService.getMessage("error.msg.could.not.resize.file", pathToImage.getFileName());
            logger.error(msg, ex);
            throw new ImageResizingException(msg, ex);
        }
    }

    void resizeForRecognition(Path pathToImage, String outputFormat) throws IOException, InvalidImageFormatException {
        Path pathToImageForRecognition = buildPathToImageForRecognition(pathToImage);

        // Thumbnails library require an extension to process files:
        // the resized image is first created with that extension, then renamed
        Path pathWithExtension = buildPathWithExtension(pathToImageForRecognition, outputFormat);

        Thumbnails.of(pathToImage.toFile())
                .size(recognitionMaxSize, recognitionMaxSize)
                .toFile(pathWithExtension.toFile());

        renameFileRemoveExtension(pathWithExtension);
    }

    private void resizeForDisplay(Path pathToImage,
            String outputFormat) throws IOException, InvalidImageFormatException {
        Path pathToImageForDisplay = buildPathToImageForDisplay(pathToImage);

        // Thumbnails library require an extension to process files:
        // the resized image is first created with that extension, then renamed
        Path pathWithExtension = buildPathWithExtension(pathToImageForDisplay, outputFormat);

        Thumbnails.of(pathToImage.toFile())
                .size(displayMaxWidth, displayMaxHeight)
                .toFile(pathWithExtension.toFile());

        renameFileRemoveExtension(pathWithExtension);
    }

    Path buildPathToImageForRecognition(Path pathToImage) throws InvalidImageFormatException {
        return buildPathWithPrefix(pathToImage, RECOGNITION_PREFIX);
    }

    Path buildPathToImageForDisplay(Path pathToImage) throws InvalidImageFormatException {
        return buildPathWithPrefix(pathToImage, DISPLAY_PREFIX);
    }

    private Path buildPathWithPrefix(Path path, String prefix) throws InvalidImageFormatException {
        return path.resolveSibling(prefix + path.getFileName());
    }

    private Path buildPathWithExtension(Path pathWithoutExtension, String outputFormat) {
        String fileNameWithExtension = String.format("%s.%s", pathWithoutExtension.getFileName(), outputFormat);
        return pathWithoutExtension.resolveSibling(fileNameWithExtension);
    }

    private String findOutputFormat(Path pathToImage) throws InvalidImageFormatException {
        return imageControlService.findImageFormat(pathToImage).name().toLowerCase();
    }

    private static void renameFileRemoveExtension(Path path) throws IOException {
        String fileName = path.getFileName().toString();
        String newFileName = removeExtensionFromFileName(fileName);
        if (!newFileName.equals(fileName)) {
            Files.move(path, path.resolveSibling(newFileName), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static String removeExtensionFromFileName(String fileName) {
        String newFileName = fileName;
        if (newFileName.indexOf('.') > 0)
            newFileName = newFileName.substring(0, fileName.lastIndexOf('.'));
        return newFileName;
    }
}
