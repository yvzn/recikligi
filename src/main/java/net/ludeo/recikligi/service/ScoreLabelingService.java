package net.ludeo.recikligi.service;

import lombok.Setter;
import net.ludeo.recikligi.model.VisualClassTranslation;
import net.ludeo.recikligi.model.VisualClassTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Setter
@Service
public class ScoreLabelingService extends LocalizedMessagesService {

    @Value("${recikligi.score.threshold.high}")
    private Double highScoreThreshold;

    @Value("${recikligi.score.threshold.medium}")
    private Double mediumScoreThreshold;

    @Autowired
    private VisualClassTranslationRepository visualClassTranslationRepository;

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
