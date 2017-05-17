package net.ludeo.recikligi.controller;

import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Setter
@Controller
public class RecyclableController {

    @GetMapping("/recyclable/{imageId:.+}")
    public String showRecyclableStatusForImage(@PathVariable("imageId") final String imageId, final Model model) {
        model.addAttribute("imageId", imageId);
        return "recyclable";
    }
}
