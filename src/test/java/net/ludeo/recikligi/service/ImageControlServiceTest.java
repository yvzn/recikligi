package net.ludeo.recikligi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Control image files")
class ImageControlServiceTest {

    private ImageControlService imageControlService;

    @BeforeEach
    void setUp() {
        imageControlService = new ImageControlService();
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
    @DisplayName("JPEG image detected")
    void detectJpeg() throws Exception {
        String path = "/images/tin-box.jpg";
        assertTrue(imageControlService.isJpegOrPng(toPath(path)));
    }

    @Test
    @DisplayName("Non-existing file detected")
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