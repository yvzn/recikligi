package net.ludeo.recikligi.service;

import java.nio.file.Path;
import java.util.Optional;

@FunctionalInterface
public interface VisualRecognitionService {

    Optional<ImageRecognitionInfo> classify(Path image) throws Exception;
}
