package net.ludeo.recikligi.service;

import java.io.IOException;

class InvalidImageFormatException extends Exception {
    InvalidImageFormatException(final String s) {
        super(s);
    }

    InvalidImageFormatException(final String s, final IOException ex) {
        super(s, ex);
    }
}
