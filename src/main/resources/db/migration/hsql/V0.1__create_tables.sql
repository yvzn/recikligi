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

CREATE TABLE usage_history (
    id UUID NOT NULL,
    date_of_request DATE,
    PRIMARY KEY (id)
);

CREATE TABLE unknown_visual_class (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE feedback (
    id UUID NOT NULL,
    user_name VARCHAR(255),
    email VARCHAR(255),
    rating VARCHAR(31),
    focus VARCHAR(31),
    suggested_name VARCHAR(255),
    suggested_status VARCHAR(31),
    comment VARCHAR(255),
    image_id UUID,
    success BOOLEAN ,
    name VARCHAR(255),
    score VARCHAR(7),
    status_name VARCHAR(31),
    PRIMARY KEY (id)
);