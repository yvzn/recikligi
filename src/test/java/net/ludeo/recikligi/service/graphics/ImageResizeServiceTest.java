package net.ludeo.recikligi.service.graphics;

import net.ludeo.recikligi.AppConfig;
import net.ludeo.recikligi.service.storage.ImageVersion;
import net.ludeo.recikligi.service.storage.StorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {AppConfig.class})
@ExtendWith(SpringExtension.class)
@DisplayName("When resizing image files")
class ImageResizeServiceTest {

    private static final String TEST_IMAGE = "/images/fluorescent-lamp.jpg";

    private final ImageResizeService imageResizeService;

    private final ImageControlService imageControlService;

    private final StorageService storageService;

    @Autowired
    ImageResizeServiceTest(ImageResizeService imageResizeService,
            ImageControlService imageControlService,
            StorageService storageService) {
        this.imageResizeService = imageResizeService;
        this.imageControlService = imageControlService;
        this.storageService = storageService;
    }

    @Test
    @DisplayName("create a valid image for recognition")
    void resizeForRecognition() throws Exception {
        imageResizeService.resizeForRecognition(testImage(), "jpg");
        assertTrue(isValidImage(resultImageForRecognition()));
    }

    @Test
    @DisplayName("create image for recognition and for display")
    void resize() throws Exception {
        imageResizeService.resize(testImage());

        assertAll(() -> assertTrue(resultImageForRecognition().toFile().exists())
                , () -> assertTrue(resultImageForDisplay().toFile().exists()));
    }

    private Path testImage() throws Exception {
        return Paths.get(getClass().getResource(TEST_IMAGE).toURI());
    }

    private boolean isValidImage(Path image) throws Exception {
        return imageControlService.findImageFormat(image) != ImageFormat.UNKNOWN;
    }

    private Path resultImageForRecognition() throws Exception {
        return storageService.read(testImage().getFileName().toString(), ImageVersion.RECOGNITION);
    }

    private Path resultImageForDisplay() throws Exception {
        return storageService.read(testImage().getFileName().toString(), ImageVersion.DISPLAY);
    }
}