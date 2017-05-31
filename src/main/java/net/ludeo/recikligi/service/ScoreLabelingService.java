package net.ludeo.recikligi.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Setter
@Service
public class ScoreLabelingService extends LocalizedMessagesService {

    @Value("${recikligi.score.threshold.high}")
    Double highScoreThreshold;

    @Value("${recikligi.score.threshold.medium}")
    Double mediumScoreThreshold;

    public String findUserFriendlyLabel(final Double score) {
        String msg;
        if (score >= highScoreThreshold) {
            msg = "recyclable.image.score.high";
        } else if (score >= mediumScoreThreshold) {
            msg = "recyclable.image.score.medium";
        } else {
            msg = "recyclable.image.score.low";
        }
        return getMessage(msg);
    }
}
