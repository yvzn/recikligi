package net.ludeo.recikligi.service;

import net.ludeo.recikligi.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {AppConfig.class})
@ExtendWith(SpringExtension.class)
@DisplayName("When controlling image files")
class ImageControlServiceTest {

    private final ImageControlService imageControlService;

    @Autowired
    ImageControlServiceTest(ImageControlService imageControlService) {
        this.imageControlService = imageControlService;
    }

    @Test
    @DisplayName("PNG image is valid")
    void testControlPngImage() throws Exception {
        String path = "/images/tin-box.png";
        imageControlService.controlImage(toPath(path));
    }

    @Test
    @DisplayName("JPEG image is valid")
    void testControlJpegImage() throws Exception {
        String path = "/images/tin-box.jpg";
        imageControlService.controlImage(toPath(path));
    }

    @Test
    @DisplayName("GIF image is not valid")
    void testControlGifImageShouldFail() throws Exception {
        String path = "/images/tin-box.gif";
        assertThrows(InvalidImageFormatException.class, () ->
                imageControlService.controlImage(toPath(path)));
    }

    @Test
    @DisplayName("Folder is not a valid image")
    void testControlFolderAsImageShouldFail() throws Exception {
        String path = "/images";
        assertThrows(InvalidImageFormatException.class, () ->
                imageControlService.controlImage(toPath(path)));
    }

    @Test
    @DisplayName("JPEG image is detected")
    void detectJpeg() throws Exception {
        String path = "/images/tin-box.jpg";
        assertTrue(imageControlService.isJpegOrPng(toPath(path)));
    }

    @Test
    @DisplayName("Non-existing file is detected")
    void detectInvalidImage() throws Exception {
        Path path = Paths.get("/not-an-existing-file-" + new Random().nextLong());
        assertThrows(InvalidImageFormatException.class, () ->
                imageControlService.isJpegOrPng(path));
    }

    @Test
    @DisplayName("First bytes array too small to be a valid image header")
    void smallByteArrayIsNotValidImageHeader() {
        byte[] sizeDoesMatter = new byte[]{(byte) 0x89, (byte) 0x50};
        assertFalse(ImageControlService.areFirstBytesEqual(ImageControlService.PNG_HEADER, sizeDoesMatter));
    }

    private Path toPath(String path) throws URISyntaxException {
        return Paths.get(getClass().getResource(path).toURI());
    }
}
