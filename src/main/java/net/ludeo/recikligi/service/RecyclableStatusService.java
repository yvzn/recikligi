package net.ludeo.recikligi.service;

import net.ludeo.recikligi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RecyclableStatusService {

    private static final String RECYCLABLE_STATUS_FOR_UNKNOWN_OBJECTS = "unknown";

    private final VisualClassRepository visualClassRepository;

    private final RecyclableStatusRepository recyclableStatusRepository;

    private final RecyclableStatusTranslationRepository recyclableStatusTranslationRepository;

    @Autowired
    public RecyclableStatusService(VisualClassRepository visualClassRepository,
            RecyclableStatusRepository recyclableStatusRepository,
            RecyclableStatusTranslationRepository recyclableStatusTranslationRepository) {
        this.visualClassRepository = visualClassRepository;
        this.recyclableStatusRepository = recyclableStatusRepository;
        this.recyclableStatusTranslationRepository = recyclableStatusTranslationRepository;
    }

    public RecyclableStatusDescription findStatusAndDescription(ImageRecognitionInfo imageRecognitionInfo) {
        String recyclableStatusName = findRecyclableStatusName(imageRecognitionInfo);
        List<RecyclableStatusTranslation> translations = findRecyclableStatusTranslations(recyclableStatusName);
        return buildDescriptionFromTranslationOrDefault(recyclableStatusName, translations);
    }

    private String findRecyclableStatusName(ImageRecognitionInfo imageRecognitionInfo) {
        RecyclableStatus recyclableStatus = findRecyclableStatus(imageRecognitionInfo);
        return recyclableStatus.getName();
    }

    private RecyclableStatus findRecyclableStatus(ImageRecognitionInfo imageRecognitionInfo) {
        return Optional.ofNullable(imageRecognitionInfo)
                .map(ImageRecognitionInfo::getName)
                .map(visualClassRepository::findByName)
                .map(VisualClass::getRecyclableStatus)
                .orElse(recyclableStatusRepository.findByName(RECYCLABLE_STATUS_FOR_UNKNOWN_OBJECTS));
    }

    private List<RecyclableStatusTranslation> findRecyclableStatusTranslations(String recyclableStatusName) {
        Locale locale = LocaleContextHolder.getLocale();
        return recyclableStatusTranslationRepository.findByRecyclableStatusNameAndTranslationLocaleName(
                recyclableStatusName, locale.getLanguage());
    }

    private RecyclableStatusDescription buildDescriptionFromTranslationOrDefault(String recyclableStatusName,
            List<RecyclableStatusTranslation> translations) {
        return translations.stream().findFirst().map(t ->
                new RecyclableStatusDescription(recyclableStatusName, t.getText(), t.getDescription()))
                .orElse(new RecyclableStatusDescription(recyclableStatusName, recyclableStatusName, recyclableStatusName));
    }
}
