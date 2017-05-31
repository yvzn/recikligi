package net.ludeo.recikligi.controller;

import lombok.Setter;
import net.ludeo.recikligi.service.ImageRecognitionInfo;
import net.ludeo.recikligi.service.ScoreLabelingService;
import net.ludeo.recikligi.service.StorageService;
import net.ludeo.recikligi.service.VisualRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.util.Optional;

@Setter
@Controller
public class RecyclableController {

    private final StorageService storageService;

    private final VisualRecognitionService visualRecognitionService;

    private final ScoreLabelingService scoreLabelingService;

    @Autowired
    public RecyclableController(final StorageService storageService,
                                final VisualRecognitionService visualRecognitionService,
                                final ScoreLabelingService scoreLabelingService) {
        this.storageService = storageService;
        this.visualRecognitionService = visualRecognitionService;
        this.scoreLabelingService = scoreLabelingService;
    }

    @GetMapping("/recyclable/{imageId:.+}")
    public String showRecyclableStatusForImage(@PathVariable("imageId") final String imageId, final Model model) throws Exception {
        Path image = storageService.read(imageId);
        Optional<ImageRecognitionInfo> imageRecognitionInfo = visualRecognitionService.classify(image);

        model.addAttribute("imageId", imageId);

        if (imageRecognitionInfo.isPresent()) {
            model.addAttribute("success", true);
            model.addAttribute("name", imageRecognitionInfo.get().getName());
            model.addAttribute("score", scoreLabelingService.findUserFriendlyLabel(imageRecognitionInfo.get().getScore()));
        } else {
            model.addAttribute("success", false);
        }

        return "recyclable";
    }
}
