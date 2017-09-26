package net.ludeo.recikligi.service.graphics;

import java.io.IOException;

class ImageResizingException extends Exception {
    ImageResizingException(String msg, IOException ex) {
        super(msg, ex);
    }
}
