package net.ludeo.recikligi.model;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface TranslationLocaleRepository extends CrudRepository<TranslationLocale, UUID> {

}
