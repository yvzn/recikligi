package net.ludeo.recikligi.service.recognition;

import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassifier;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageRecognitionInfo {

    private final String name;

    private final Double score;

    ImageRecognitionInfo(final VisualClassifier.VisualClass visualClass) {
        this.name = visualClass.getName();
        this.score = visualClass.getScore();
    }
}
