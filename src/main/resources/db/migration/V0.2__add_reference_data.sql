INSERT INTO translation_locale (id, name) VALUES (UUID(), 'fr');
INSERT INTO translation_locale (id, name) VALUES (UUID(), 'en');

INSERT INTO recyclable_status (id, name) VALUES (UUID(), 'not-recyclable');
INSERT INTO recyclable_status (id, name) VALUES (UUID(), 'recyclable');
INSERT INTO recyclable_status (id, name) VALUES (UUID(), 'glass');
INSERT INTO recyclable_status (id, name) VALUES (UUID(), 'waste-collection-center');
INSERT INTO recyclable_status (id, name) VALUES (UUID(), 'compost-pile');
INSERT INTO recyclable_status (id, name) VALUES (UUID(), 'container');
INSERT INTO recyclable_status (id, name) VALUES (UUID(), 'unknown');

INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'à jeter', 'ne peut pas être recyclé et doit être jeté' FROM translation_locale t, recyclable_status r WHERE t.name = 'fr' AND r.name = 'not-recyclable';
INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'à recycler', 'peut être recyclé (sac jaune ou bac de tri)' FROM translation_locale t, recyclable_status r WHERE t.name = 'fr' AND r.name = 'recyclable';
INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'colonne à verre', 'à déposer dans un conteneur à verre' FROM translation_locale t, recyclable_status r WHERE t.name = 'fr' AND r.name = 'glass';
INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'déchèterie', 'à déposer en déchèterie dans un conteneur spécialisé' FROM translation_locale t, recyclable_status r WHERE t.name = 'fr' AND r.name = 'waste-collection-center';
INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'à composter', 'peut être composté' FROM translation_locale t, recyclable_status r WHERE t.name = 'fr' AND r.name = 'compost-pile';
INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'conteneur', 'à déposer dans un conteneur spécialisé (déchèterie, magasin de bricolage, ...)' FROM translation_locale t, recyclable_status r WHERE t.name = 'fr' AND r.name = 'container';
INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'inconnu', 'pas assez d''informations, à jeter par défaut' FROM translation_locale t, recyclable_status r WHERE t.name = 'fr' AND r.name = 'unknown';

INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'throw away', 'cannot be recycled, throw away accrodingly' FROM translation_locale t, recyclable_status r WHERE t.name = 'en' AND r.name = 'not-recyclable';
INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'recycle', 'can be recycled into appropriate container' FROM translation_locale t, recyclable_status r WHERE t.name = 'en' AND r.name = 'recyclable';
INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'collection center', 'should be disposed in local recycling collection center' FROM translation_locale t, recyclable_status r WHERE t.name = 'en' AND r.name = 'glass';
INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'collection center', 'should be disposed in local recycling collection center' FROM translation_locale t, recyclable_status r WHERE t.name = 'en' AND r.name = 'waste-collection-center';
INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'compost', 'to compost bin or compost pile' FROM translation_locale t, recyclable_status r WHERE t.name = 'en' AND r.name = 'compost-pile';
INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'collection center', 'should be disposed in local recycling collection center' FROM translation_locale t, recyclable_status r WHERE t.name = 'en' AND r.name = 'container';
INSERT INTO recyclable_status_translation(id, recyclable_status_id, translation_locale_id, text, description) SELECT UUID(), r.id, t.id, 'unknown', 'not enough information, throw away accrodingly' FROM translation_locale t, recyclable_status r WHERE t.name = 'en' AND r.name = 'unknown';
