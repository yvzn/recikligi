package net.ludeo.recikligi.service.storage;

import lombok.Getter;

@Getter
public enum ImageVersion {
    ORIGINAL(""), RECOGNITION("recognition-"), DISPLAY("display-");

    private String prefix;

    ImageVersion(String prefix) {
        this.prefix = prefix;
    }
}
