package net.ludeo.recikligi.service.recognition;

import com.ibm.watson.visual_recognition.v3.model.ClassResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageRecognitionInfo {

    private final String name;

    private final Double score;

    ImageRecognitionInfo(final ClassResult classResult) {
        this.name = classResult.getXClass();
        this.score = classResult.getScore().doubleValue();
    }
}
