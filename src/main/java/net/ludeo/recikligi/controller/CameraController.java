package net.ludeo.recikligi.controller;

import net.ludeo.recikligi.service.EmojiGeneratorService;
import net.ludeo.recikligi.service.graphics.ImageControlService;
import net.ludeo.recikligi.service.graphics.ImageResizeService;
import net.ludeo.recikligi.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    private final ImageResizeService imageResizeService;

    @Autowired
    public CameraController(final StorageService storageService, final ImageControlService imageControlService,
            ImageResizeService imageResizeService) {
        this.storageService = storageService;
        this.imageControlService = imageControlService;
        this.imageResizeService = imageResizeService;
    }

    @GetMapping("/camera")
    public String camera(final Model model) {
        model.addAttribute("sendButtonIcon", EmojiGeneratorService.randomTechnologist());
        return "camera";
    }

    @PostMapping("/camera-classic")
    public String handleClassicCameraImageUpload(@RequestParam("image") final MultipartFile imageFile,
            final RedirectAttributes redirectAttributes) throws Exception {
        UUID imageId = storageService.store(imageFile);
        controlImage(imageId);
        return redirectToImage(imageId, redirectAttributes);
    }

    @PostMapping("/camera-advanced")
    public String handleAdvancedCameraImageUpload(@RequestParam("image") final String base64image,
            final RedirectAttributes redirectAttributes) throws Exception {
        UUID imageId = storageService.store(base64image);
        controlImage(imageId);
        return redirectToImage(imageId, redirectAttributes);
    }

    private void controlImage(final UUID imageId) throws Exception {
        Path image = storageService.read(imageId);
        imageControlService.controlImage(image);
        imageResizeService.resize(image);
    }

    private String redirectToImage(UUID imageId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("imageId", imageId.toString());
        return "redirect:/recyclable/{imageId}";
    }
}
