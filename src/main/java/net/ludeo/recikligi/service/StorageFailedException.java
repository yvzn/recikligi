package net.ludeo.recikligi.service;

public class StorageFailedException extends Exception {

    StorageFailedException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
