package net.ludeo.recikligi.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RecyclableStatusTranslationRepository extends CrudRepository<RecyclableStatusTranslation, UUID> {

    List<RecyclableStatusTranslation> findByRecyclableStatusNameAndTranslationLocaleName(final String recyclableStatusName, final String translationLocaleName);

}

