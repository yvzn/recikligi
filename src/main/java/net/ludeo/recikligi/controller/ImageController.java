package net.ludeo.recikligi.controller;

import net.ludeo.recikligi.service.graphics.ImageControlService;
import net.ludeo.recikligi.service.graphics.ImageFormat;
import net.ludeo.recikligi.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class ImageController {

    private final StorageService storageService;

    private final ImageControlService imageControlService;

    @Autowired
    public ImageController(final StorageService storageService, final ImageControlService imageControlService) {
        this.storageService = storageService;
        this.imageControlService = imageControlService;
    }

    @GetMapping(value = "/image/{imageId:.+}")
    @ResponseBody
    public HttpEntity<byte[]> showImage(@PathVariable("imageId") final String imageId) throws Exception {
        Path image = storageService.read(imageId);
        ImageFormat imageFormat = imageControlService.findImageFormat(image);
        byte[] bytes = Files.readAllBytes(image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(imageFormat == ImageFormat.PNG ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG);
        headers.setContentLength(bytes.length);
        return new HttpEntity<>(bytes, headers);
    }
}
