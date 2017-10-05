package net.ludeo.recikligi.service.storage;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface StorageStreamWriter {

    void writeTo(OutputStream outputStream) throws IOException;
}
