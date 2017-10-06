package net.ludeo.recikligi.service.storage;

import java.io.IOException;
import java.nio.file.Path;

@FunctionalInterface
public interface StorageFileWriter {

    void writeTo(Path path) throws IOException, StorageFailedException;
}
