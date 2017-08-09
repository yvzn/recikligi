package net.ludeo.recikligi.model;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface VisualClassRepository extends CrudRepository<VisualClass, UUID> {

    VisualClass findByName(final String name);
}
