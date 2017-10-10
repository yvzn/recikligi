package net.ludeo.recikligi.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Feedback {
    private UUID imageId;
    private boolean success;
    private String name;
    private String score;
    private String scoreLabel;
    private String statusName;
    private String statusText;
    private String statusDescription;
}
