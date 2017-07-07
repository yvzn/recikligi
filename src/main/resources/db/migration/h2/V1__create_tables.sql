CREATE TABLE translation_locale (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE recyclable_status (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE recyclable_status_translation (
    id UUID NOT NULL,
    recyclable_status_id UUID NOT NULL,
    translation_locale_id UUID NOT NULL,
    text VARCHAR(255) NOT NULL,
    description VARCHAR(1023) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (recyclable_status_id) REFERENCES recyclable_status(id),
    FOREIGN KEY (translation_locale_id) REFERENCES translation_locale(id)
);
CREATE TABLE visual_class (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    recyclable_status_id UUID NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (recyclable_status_id) REFERENCES recyclable_status(id)
);

CREATE TABLE visual_class_translation (
    id UUID NOT NULL,
    visual_class_id UUID NOT NULL,
    translation_locale_id UUID NOT NULL,
    text VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (visual_class_id) REFERENCES visual_class(id),
    FOREIGN KEY (translation_locale_id) REFERENCES translation_locale(id)
);
