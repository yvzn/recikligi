package net.ludeo.recikligi.service.storage;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class ImageNotFoundException extends Exception {
    ImageNotFoundException(String s) {
        super(s);
    }

    ImageNotFoundException(final String s, final Throwable throwable) {
        super(s, throwable);
    }
}
