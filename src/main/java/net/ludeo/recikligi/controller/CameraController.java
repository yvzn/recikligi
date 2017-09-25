package net.ludeo.recikligi.controller;

import net.ludeo.recikligi.service.graphics.ImageControlService;
import net.ludeo.recikligi.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Path;
import java.util.UUID;

@Controller
public class CameraController {

    private final StorageService storageService;

    private final ImageControlService imageControlService;

    @Autowired
    public CameraController(final StorageService storageService, final ImageControlService imageControlService) {
        this.storageService = storageService;
        this.imageControlService = imageControlService;
    }

    @GetMapping("/camera")
    public String camera() {
        return "camera";
    }

    @PostMapping("/camera")
    public String handleCameraImageUpload(@RequestParam("image") final MultipartFile imageFile, final RedirectAttributes redirectAttributes) throws Exception {
        UUID imageId = storageService.store(imageFile);
        Path image = storageService.read(imageId);
        imageControlService.controlImage(image);

        redirectAttributes.addAttribute("imageId", imageId.toString());
        return "redirect:/recyclable/{imageId}";
    }
}
