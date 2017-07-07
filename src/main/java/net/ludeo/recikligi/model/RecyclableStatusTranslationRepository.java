package net.ludeo.recikligi.model;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface RecyclableStatusTranslationRepository extends CrudRepository<RecyclableStatusTranslation, UUID> {

}

