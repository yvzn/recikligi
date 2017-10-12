package net.ludeo.recikligi.controller;

import net.ludeo.recikligi.model.Feedback;
import net.ludeo.recikligi.model.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Locale;

@Controller
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @PostMapping("/feedback")
    public String showFeedbackForm(final Feedback feedback, final Model model) {
        model.addAttribute("feedback", feedback);

        Locale locale = LocaleContextHolder.getLocale();
        return String.format("feedback_%s", locale.getLanguage());
    }

    @PostMapping("/user-feedback")
    public String submitFeedbackForm(final Feedback feedback) {
        feedbackRepository.save(feedback);
        return "thanks";
    }
}
