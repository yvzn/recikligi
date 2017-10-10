package net.ludeo.recikligi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FeedbackController {

    @PostMapping("/feedback")
    public String showFeedbackForm(final Feedback feedback, final Model model) {
        model.addAttribute("feedback", feedback);
        return "feedback";
    }
}
