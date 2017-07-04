package net.ludeo.recikligi.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface VisualClassTranslationRepository extends CrudRepository<VisualClassTranslation, UUID> {

    List<VisualClassTranslation> findByVisualClassName(final String visualClassName);

    List<VisualClassTranslation> findByVisualClassNameAndTranslationLocaleName(final String visualClassName, final String translationLocaleName);
}
