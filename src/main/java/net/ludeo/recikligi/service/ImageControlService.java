package net.ludeo.recikligi.service;

import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ImageControlService {

    static final byte[] PNG_HEADER = {(byte) 0x89, (byte) 0x50, (byte) 0x4E, (byte) 0x47, (byte) 0x0D, (byte) 0x0A, (byte) 0x1A, (byte) 0x0A};
    private static final byte[] JPEG_HEADER = {(byte) 0xFF, (byte) 0xD8};

    public void controlImage(final Path imageFile) throws InvalidImageFormatException {
        String errorMessage = null;
        if (!Files.isRegularFile(imageFile)) {
            errorMessage = String.format("Could not read file %s", imageFile.getFileName());
        } else if (!isJpegOrPng(imageFile)) {
            errorMessage = "Only valid PNG and JPEG files are accepted";
        }
        if (errorMessage != null) {
            throw new InvalidImageFormatException(errorMessage);
        }
    }

    boolean isJpegOrPng(final Path imageFile) throws InvalidImageFormatException {
        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(imageFile.toFile()))) {
            byte[] firstBytes = new byte[8];
            int readBytesCount = inputStream.read(firstBytes);
            return isJpegHeader(firstBytes, readBytesCount) || isPngHeader(firstBytes, readBytesCount);
        } catch (final IOException ex) {
            throw new InvalidImageFormatException(
                    String.format("Could not read file %s", imageFile.getFileName()), ex);
        }
    }

    private boolean isPngHeader(byte[] firstBytes, int readBytesCount) {
        return readBytesCount >= PNG_HEADER.length && areFirstBytesEqual(PNG_HEADER, firstBytes);
    }

    private boolean isJpegHeader(byte[] firstBytes, int readBytesCount) {
        return readBytesCount >= JPEG_HEADER.length && areFirstBytesEqual(JPEG_HEADER, firstBytes);
    }

    static boolean areFirstBytesEqual(byte[] header, byte[] tested) {
        for (int i = 0; i < header.length; i++) {
            if (i < tested.length) {
                if (header[i] != tested[i]) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
