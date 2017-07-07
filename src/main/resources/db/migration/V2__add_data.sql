INSERT INTO translation_locale (id, name)
VALUES (UUID(), 'fr');

INSERT INTO recyclable_status (id, name)
VALUES (UUID(), 'recyclable');

INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description)
SELECT UUID(), r.id, t.id, 'recyclable', 'cet objet peut être recyclé (sac ou bac jaunes)'
FROM translation_locale t, recyclable_status r
WHERE t.name = 'fr' AND r.name = 'recyclable';

INSERT INTO visual_class (id, name, recyclable_status_id)
SELECT UUID(), 'tin-box', r.id
FROM recyclable_status r
WHERE r.name = 'recyclable';

INSERT INTO visual_class_translation(id, translation_locale_id, visual_class_id, text)
SELECT UUID(), t.id, v.id, 'boîte de conserve'
FROM translation_locale t, visual_class v
WHERE t.name = 'fr' AND v.name = 'tin-box';

