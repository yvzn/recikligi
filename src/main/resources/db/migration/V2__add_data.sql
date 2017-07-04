INSERT INTO translation_locale (id, name)
VALUES (UUID(), 'fr');

INSERT INTO visual_class (id, name)
VALUES (UUID(), 'tin-box');

INSERT INTO visual_class_translation(id, translation_locale_id, visual_class_id, text)
SELECT UUID(), t.id, v.id, 'boîte de conserve'
FROM translation_locale t, visual_class v
WHERE t.name = 'fr' AND v.name = 'tin-box';

