package net.ludeo.recikligi.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

@Setter
@Service
@Qualifier("mock")
public class VisualRecognitionServiceMock implements VisualRecognitionService {

    @Override
    public Optional<ImageRecognitionInfo> classify(final Path image) throws Exception {
        double score = new Random().nextDouble() * 100;
        if (score < 50)
            return Optional.empty();
        return Optional.of(new ImageRecognitionInfo("tin-box", score));
    }
}
