package net.ludeo.recikligi.controller;

import net.ludeo.recikligi.service.StorageFailedException;
import net.ludeo.recikligi.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class CameraController extends AbstractController {

    private StorageService storageService;

    @Autowired
    public CameraController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/camera")
    public String camera(final Model model) {
        addCommonAttributes(model);
        return "camera";
    }

    @PostMapping("/camera")
    public String handleCameraImageUpload(@RequestParam("image") final MultipartFile imageFile, final RedirectAttributes redirectAttributes) throws StorageFailedException {
        UUID imageId = storageService.store(imageFile);
        redirectAttributes.addAttribute("imageId", imageId.toString());
        return "redirect:/recyclable/{imageId}";
    }
}
