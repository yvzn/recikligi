package net.ludeo.recikligi.service.storage;

public class StorageFailedException extends Exception {

    StorageFailedException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
