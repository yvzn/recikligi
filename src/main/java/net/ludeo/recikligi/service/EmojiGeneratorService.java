package net.ludeo.recikligi.service;

import java.util.Random;

public class EmojiGeneratorService {

    private static final String COMPUTER = htmlEntity("1F4BB");

    private static final String[] GENRE = {htmlEntity("1F469"), htmlEntity("1F468")};

    private static final String[] SKIN_TONE = {htmlEntity("1F3FB"), htmlEntity("1F3FC"),
            htmlEntity("1F3FD"), htmlEntity("1F3FE"), htmlEntity("1F3FF")};

    private static final String JOINER = htmlEntity("200D");

    private static String htmlEntity(final String codePoint) {
        return "&#x" + codePoint + ";";
    }

    public static String randomTechnologist() {
        return randomize(GENRE) + randomize(SKIN_TONE) + JOINER + COMPUTER;
    }

    private static String randomize(String[] strings) {
        return strings[new Random().nextInt(strings.length)];
    }
}
