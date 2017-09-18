package net.ludeo.recikligi.service;

import lombok.Setter;
import net.ludeo.recikligi.model.VisualClass;
import net.ludeo.recikligi.model.VisualClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

@Setter
@Service
@Profile("mock")
public class VisualRecognitionServiceMock implements VisualRecognitionService {

    @Value("${recikligi.mock.failure-rate:50}")
    private Integer failureRate;

    private final VisualClassRepository visualClassRepository;

    @Autowired
    public VisualRecognitionServiceMock(VisualClassRepository visualClassRepository) {
        this.visualClassRepository = visualClassRepository;
    }

    @Override
    public Optional<ImageRecognitionInfo> classify(final Path image) throws Exception {
        double score = new Random().nextDouble() * 100;
        if (score < failureRate)
            return Optional.empty();
        return Optional.of(new ImageRecognitionInfo(findRandomVisualClassName(), score));
    }

    private String findRandomVisualClassName() {
        long count = visualClassRepository.count();
        int index = new Random().nextInt((int) count);
        for (VisualClass visualClass : visualClassRepository.findAll()) {
            if (index == 0) {
                return visualClass.getName();
            }
            --index;
        }
        throw new IllegalStateException("Cannot get here");
    }
}
