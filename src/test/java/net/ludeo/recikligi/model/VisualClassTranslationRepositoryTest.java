package net.ludeo.recikligi.model;

import net.ludeo.recikligi.AppConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AppConfig.class})
@ExtendWith(SpringExtension.class)
@DisplayName("When searching for visual class translations")
class VisualClassTranslationRepositoryTest {

    private final VisualClassRepository visualClassRepository;

    private final TranslationLocaleRepository translationLocaleRepository;

    private final RecyclableStatusTranslationRepository recyclableStatusTranslationRepository;

    private final VisualClassTranslationRepository visualClassTranslationRepository;

    @Autowired
    VisualClassTranslationRepositoryTest(VisualClassRepository visualClassRepository,
            TranslationLocaleRepository translationLocaleRepository,
            RecyclableStatusTranslationRepository recyclableStatusTranslationRepository,
            VisualClassTranslationRepository visualClassTranslationRepository) {
        this.visualClassRepository = visualClassRepository;
        this.translationLocaleRepository = translationLocaleRepository;
        this.recyclableStatusTranslationRepository = recyclableStatusTranslationRepository;
        this.visualClassTranslationRepository = visualClassTranslationRepository;
    }

    @AfterEach
    void tearDown() {
        visualClassTranslationRepository.deleteAll();
        recyclableStatusTranslationRepository.deleteAll();
        translationLocaleRepository.deleteAll();
        visualClassRepository.deleteAll();
    }

    @Test
    @DisplayName("Find translations by visual class")
    void findByVisualClassName() {
        VisualClass visualClass = createVisualClass("test");
        TranslationLocale translationLocale = createLocale("fr");

        String translationText = "essai";
        VisualClassTranslation translation = new VisualClassTranslation();
        translation.setVisualClass(visualClass);
        translation.setTranslationLocale(translationLocale);
        translation.setText(translationText);
        visualClassTranslationRepository.save(translation);

        List<VisualClassTranslation> visualClassTranslations = visualClassTranslationRepository.findByVisualClassName("test");

        assertEquals(1, visualClassTranslations.size());
        assertEquals("essai", visualClassTranslations.get(0).getText());
    }

    @Test
    @DisplayName("Find translations by visual class and locale")
    void findByvisualClassNameAndLocaleName() {
        VisualClass visualClass = createVisualClass("test2");

        {
            TranslationLocale translationLocale = createLocale("fr");
            String translationText = "essai";
            VisualClassTranslation translation = new VisualClassTranslation();
            translation.setVisualClass(visualClass);
            translation.setTranslationLocale(translationLocale);
            translation.setText(translationText);
            visualClassTranslationRepository.save(translation);
        }

        {
            TranslationLocale translationLocale = createLocale("en");
            String translationText = "test";
            VisualClassTranslation translation = new VisualClassTranslation();
            translation.setVisualClass(visualClass);
            translation.setTranslationLocale(translationLocale);
            translation.setText(translationText);
            visualClassTranslationRepository.save(translation);
        }

        List<VisualClassTranslation> visualClassTranslations = visualClassTranslationRepository.findByVisualClassNameAndTranslationLocaleName("test2", "fr");

        assertEquals(1, visualClassTranslations.size());
        assertEquals("essai", visualClassTranslations.get(0).getText());
    }

    private TranslationLocale createLocale(final String localeName) {
        TranslationLocale translationLocale = new TranslationLocale();
        translationLocale.setName(localeName);
        translationLocaleRepository.save(translationLocale);
        return translationLocale;
    }

    private VisualClass createVisualClass(final String visualClassName) {
        VisualClass visualClass = new VisualClass();
        visualClass.setName(visualClassName);
        visualClassRepository.save(visualClass);
        return visualClass;
    }
}
