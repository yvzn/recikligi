package net.ludeo.recikligi.service.recognition;

import com.ibm.cloud.sdk.core.service.security.IamOptions;
import com.ibm.watson.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImage;
import com.ibm.watson.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.visual_recognition.v3.model.ClassifierResult;
import com.ibm.watson.visual_recognition.v3.model.ClassifyOptions;
import lombok.Setter;
import net.ludeo.recikligi.service.graphics.ImageControlService;
import net.ludeo.recikligi.service.graphics.ImageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@Setter
@Service
@Profile({"watson", "default"})
public class WatsonVisualRecognitionService implements VisualRecognitionService {

    private static final Logger logger = LoggerFactory.getLogger(WatsonVisualRecognitionService.class);

    @Value("${recikligi.watson.apikey}")
    private String watsonApiKey;

    private final ImageControlService imageControlService;

    @Autowired
    public WatsonVisualRecognitionService(final ImageControlService imageControlService) {
        this.imageControlService = imageControlService;
    }

    @Override
    public Optional<ImageRecognitionInfo> classify(final Path image) throws Exception {
        logger.debug("watsonApiKey={}", buildObfuscatedApikey());

        IamOptions iamOptions = new IamOptions
                .Builder()
                .apiKey(watsonApiKey).build();

        VisualRecognition service = new VisualRecognition("2018-03-19", iamOptions);

        ImageFormat imageFormat = imageControlService.findImageFormat(image);
        String imageName = String.format("%s.%s", image.getFileName(), imageFormat.toString().toLowerCase());

        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .imagesFile(image.toFile())
                .imagesFilename(imageName)
                .build();
        ClassifiedImages result = service.classify(classifyOptions).execute().getResult();

        logger.debug(result.toString());

        return result.getImages().stream().findFirst()
                .map(ClassifiedImage::getClassifiers)
                .map(Collection::stream)
                .flatMap(Stream::findFirst)
                .map(ClassifierResult::getClasses)
                .map(Collection::stream)
                .flatMap(Stream::findFirst)
                .map(ImageRecognitionInfo::new);
    }

    private String buildObfuscatedApikey() {
        return Optional.ofNullable(this.watsonApiKey)
                .map(String::length)
                .map(WatsonVisualRecognitionService::repeatStarNtimes)
                .orElse("null");
    }

    private static String repeatStarNtimes(int n) {
        return new String(new char[n]).replace("\0", "*");
    }
}
