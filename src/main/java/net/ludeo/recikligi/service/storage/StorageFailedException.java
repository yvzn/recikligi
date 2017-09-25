package net.ludeo.recikligi.service.storage;

class StorageFailedException extends Exception {

    StorageFailedException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
