package net.ludeo.recikligi.service;

import lombok.Getter;

@Getter
public class RecyclableStatusDescription {

    final String text;

    final String description;

    RecyclableStatusDescription(String text, String description) {
        this.text = text;
        this.description = description;
    }
}
