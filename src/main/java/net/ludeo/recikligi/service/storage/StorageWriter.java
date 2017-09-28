package net.ludeo.recikligi.service.storage;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface StorageWriter {

    void accept(OutputStream outputStream) throws IOException;
}
