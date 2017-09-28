package net.ludeo.recikligi.service.storage;

import org.springframework.stereotype.Service;

@Service
class ImageNamingService {

    String buildImageName(final String imageId, final ImageVersion imageVersion) {
        return buildImageNameWithPrefix(imageId, imageVersion.getPrefix());
    }

    private String buildImageNameWithPrefix(String imageId, String prefix) {
        return prefix + imageId;
    }
}
