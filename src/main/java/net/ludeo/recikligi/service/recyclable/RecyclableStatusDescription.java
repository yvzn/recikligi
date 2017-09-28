package net.ludeo.recikligi.service.recyclable;

import lombok.Getter;

@Getter
public class RecyclableStatusDescription {

    final String statusName;

    final String text;

    final String description;

    RecyclableStatusDescription(String statusName, String text, String description) {
        this.statusName = statusName;
        this.text = text;
        this.description = description;
    }
}
