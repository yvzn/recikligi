package net.ludeo.recikligi.controller;

import net.ludeo.recikligi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.util.Optional;

@Controller
public class RecyclableController {

    private final StorageService storageService;

    private final VisualRecognitionService visualRecognitionService;

    private final ScoreLabelingService scoreLabelingService;

    private final RecyclableStatusService recyclableStatusService;

    @Autowired
    public RecyclableController(final StorageService storageService,
            final VisualRecognitionService visualRecognitionService,
            final ScoreLabelingService scoreLabelingService,
            final RecyclableStatusService recyclableStatusService) {
        this.storageService = storageService;
        this.visualRecognitionService = visualRecognitionService;
        this.scoreLabelingService = scoreLabelingService;
        this.recyclableStatusService = recyclableStatusService;
    }

    @GetMapping("/recyclable/{imageId:.+}")
    public String showRecyclableStatusForImage(@PathVariable("imageId") final String imageId,
            final Model model) throws Exception {
        Path image = storageService.read(imageId);
        Optional<ImageRecognitionInfo> imageRecognitionInfo = visualRecognitionService.classify(image);

        model.addAttribute("imageId", imageId);

        if (imageRecognitionInfo.isPresent()) {
            model.addAttribute("success", true);
            model.addAttribute("name", scoreLabelingService.findUILabel(imageRecognitionInfo.get().getName()));
            model.addAttribute("score", scoreLabelingService.formatUIScore(imageRecognitionInfo.get().getScore()));
            model.addAttribute("scoreLabel", scoreLabelingService.findUILabel(imageRecognitionInfo.get().getScore()));
        } else {
            model.addAttribute("success", false);
        }

        RecyclableStatusDescription statusAndDescription = recyclableStatusService.findStatusAndDescription(
                imageRecognitionInfo.orElse(null));
        model.addAttribute("statusName", statusAndDescription.getStatusName());
        model.addAttribute("statusText", statusAndDescription.getText());
        model.addAttribute("statusDescription", statusAndDescription.getDescription());

        return "recyclable";
    }
}
