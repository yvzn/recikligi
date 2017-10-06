package net.ludeo.recikligi.service.storage;

import net.ludeo.recikligi.service.LocalizedMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Component
public class Base64FileWriter {

    private final LocalizedMessagesService localizedMessagesService;

    @Autowired
    public Base64FileWriter(LocalizedMessagesService localizedMessagesService) {
        this.localizedMessagesService = localizedMessagesService;
    }

    void write(String stringToWrite, Path pathToFile) throws StorageFailedException, IOException {
        try {
            byte[] bytesToWrite = Base64.getDecoder().decode(stringToWrite);
            Files.write(pathToFile, bytesToWrite);
        } catch (final IllegalArgumentException ex) {
            String msg = localizedMessagesService.getMessage("error.msg.could.not.decode.base64");
            throw new StorageFailedException(msg, ex);
        }
    }
}
