/*
   Copyright 2017-2021 Yvan Razafindramanana

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package net.ludeo.recikligi.service.recognition;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.visual_recognition.v3.model.*;
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
@Profile({"watson"})
@Deprecated
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
		logger.debug("watsonApiKey={}", obfuscate(watsonApiKey));

		Authenticator authenticator = new IamAuthenticator.Builder()
			.apikey(watsonApiKey)
			.build();

		VisualRecognition service = new VisualRecognition("2018-03-19", authenticator);

		ImageFormat imageFormat = imageControlService.findImageFormat(image);
		String imageName = String.format("%s.%s", image.getFileName(), imageFormat.toString().toLowerCase());

		ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
			.imagesFile(image.toFile())
			.imagesFilename(imageName)
			.build();
		ClassifiedImages result = service.classify(classifyOptions).execute().getResult();

		logger.debug(result.toString());

		return result.getImages().stream()
			.findFirst()
			.map(ClassifiedImage::getClassifiers)
			.map(Collection::stream)
			.flatMap(Stream::findFirst)
			.map(ClassifierResult::getClasses)
			.map(Collection::stream)
			.flatMap(Stream::findFirst)
			.map(WatsonVisualRecognitionService::toImageRecognitionInfo);
	}

	private static ImageRecognitionInfo toImageRecognitionInfo(ClassResult classResult) {
		return new ImageRecognitionInfo(
			classResult.getXClass(),
			classResult.getScore().doubleValue());
	}
}
