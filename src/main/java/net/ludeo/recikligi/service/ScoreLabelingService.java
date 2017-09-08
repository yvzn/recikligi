package net.ludeo.recikligi.service;

import net.ludeo.recikligi.message.LocalizedMessagesComponent;
import net.ludeo.recikligi.model.VisualClassTranslation;
import net.ludeo.recikligi.model.VisualClassTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class ScoreLabelingService extends LocalizedMessagesComponent {

    @Value("${recikligi.score.threshold.high}")
    private Double highScoreThreshold;

    @Value("${recikligi.score.threshold.medium}")
    private Double mediumScoreThreshold;

    private final VisualClassTranslationRepository visualClassTranslationRepository;

    @Autowired
    public ScoreLabelingService(VisualClassTranslationRepository visualClassTranslationRepository) {
        this.visualClassTranslationRepository = visualClassTranslationRepository;
    }

    public String findUILabel(final Double score) {
        String msg;
        if (score >= highScoreThreshold) {
            msg = "recyclable.image.score.high";
        } else if (score >= mediumScoreThreshold) {
            msg = "recyclable.image.score.medium";
        } else {
            msg = "recyclable.image.score.low";
        }
        return getMessage(msg);
    }

    public String findUILabel(final String visualClassName) {
        Locale locale = LocaleContextHolder.getLocale();

        List<VisualClassTranslation> translations = visualClassTranslationRepository.findByVisualClassNameAndTranslationLocaleName(
                visualClassName, locale.getLanguage());
        return translations.stream().findFirst().map(VisualClassTranslation::getText).orElse(visualClassName);
    }
}
