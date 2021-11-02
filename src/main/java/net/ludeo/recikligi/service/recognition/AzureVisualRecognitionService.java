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

import com.ibm.cloud.sdk.core.util.GsonSingleton;
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionManager;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.DetectedObject;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.VisualFeatureTypes;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

@Setter
@Service
@Profile({"azure", "default"})
public class AzureVisualRecognitionService implements VisualRecognitionService {

	private static final Logger logger = LoggerFactory.getLogger(AzureVisualRecognitionService.class);

	@Value("${recikligi.azure.computervision.endpoint}")
	private String endpoint;

	@Value("${recikligi.azure.computervision.apikey}")
	private String apiKey;

	@Override
	public Optional<ImageRecognitionInfo> classify(Path image) throws Exception {
		logger.debug("endpoint={}", obfuscate(endpoint));
		logger.debug("apiKey={}", obfuscate(apiKey));

		var computerVisionClient = ComputerVisionManager
			.authenticate(apiKey)
			.withEndpoint(endpoint);
		var computerVision = computerVisionClient.computerVision();

		var featuresToExtractFromLocalImage = new ArrayList<VisualFeatureTypes>();
		featuresToExtractFromLocalImage.add(VisualFeatureTypes.OBJECTS);

		byte[] imageByteArray = Files.readAllBytes(image);

		var result = computerVision
			.analyzeImageInStream()
			.withImage(imageByteArray)
			.withVisualFeatures(featuresToExtractFromLocalImage)
			.execute();

		if (logger.isDebugEnabled()) {
			logger.debug(GsonSingleton.getGson().toJson(result));
		}

		return result.objects().stream()
			.sorted(Comparator.comparing(DetectedObject::confidence).reversed())
			.findFirst()
			.map(AzureVisualRecognitionService::toImageRecognitionInfo);
	}

	private static ImageRecognitionInfo toImageRecognitionInfo(DetectedObject detectedObject) {
		return new ImageRecognitionInfo(
			detectedObject.objectProperty(),
			detectedObject.confidence());
	}
}
