package net.ludeo.recikligi.model;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RecyclableStatusRepository extends CrudRepository<RecyclableStatus, UUID> {

    RecyclableStatus findByName(String recyclableStatusForUnknownObjects);
}
