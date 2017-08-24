package net.ludeo.recikligi.service;

import net.ludeo.recikligi.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AppConfig.class})
@ExtendWith(SpringExtension.class)
@DisplayName("When labeling recognition results")
class ScoreLabelingServiceTest {

    private final ScoreLabelingService scoreLabelingService;

    @Autowired
    ScoreLabelingServiceTest(ScoreLabelingService scoreLabelingService) {
        this.scoreLabelingService = scoreLabelingService;
    }

    @Test
    @DisplayName("If translation missing return raw class name")
    void findUILabelForMissingVisualClass() {
        String visualClassName = "this visual class name does not exist !!!%%";
        String uiLabel = scoreLabelingService.findUILabel(visualClassName);

        assertEquals(visualClassName, uiLabel);
    }
}
