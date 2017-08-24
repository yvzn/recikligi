package net.ludeo.recikligi.model;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface UsageHistoryRepository extends CrudRepository<UsageHistory, UUID> {

    Long countByDateOfRequest(LocalDate dateOfRequest);
}
