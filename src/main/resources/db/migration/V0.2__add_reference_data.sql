INSERT INTO translation_locale (id, name)
VALUES (UUID(), 'fr');

INSERT INTO recyclable_status (id, name)
VALUES (UUID(), 'recyclable');

INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description)
SELECT UUID(), r.id, t.id, 'recyclable', 'peut être recyclé (sac jaune ou bac de tri)'
FROM translation_locale t, recyclable_status r
WHERE t.name = 'fr' AND r.name = 'recyclable';

INSERT INTO recyclable_status (id, name)
VALUES (UUID(), 'not-recyclable');

INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description)
SELECT UUID(), r.id, t.id, 'non recyclable', 'ne peut pas être recyclé et doit être jeté'
FROM translation_locale t, recyclable_status r
WHERE t.name = 'fr' AND r.name = 'not-recyclable';

INSERT INTO recyclable_status (id, name)
VALUES (UUID(), 'container');

INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description)
SELECT UUID(), r.id, t.id, 'conteneur', 'doit être déposé dans un conteneur spécifique'
FROM translation_locale t, recyclable_status r
WHERE t.name = 'fr' AND r.name = 'container';

INSERT INTO recyclable_status (id, name)
VALUES (UUID(), 'unknown');

INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description)
SELECT UUID(), r.id, t.id, 'inconnu', 'pas assez d''informations sur son recyclage'
FROM translation_locale t, recyclable_status r
WHERE t.name = 'fr' AND r.name = 'unknown';


