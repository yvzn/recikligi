package net.ludeo.recikligi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Feedback extends AbstractEntityWithId {
    private String rating;
    private String focus;
    private String suggestedName;
    private String suggestedStatus;
    private String comment;
    private String email;

    private UUID imageId;
    private boolean success;
    private String name;
    private String score;
    private String statusName;
}
