package net.ludeo.recikligi.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface UsageHistoryRepository extends CrudRepository<UsageHistory, UUID> {

    Long countByDateOfRequest(LocalDate dateOfRequest);

    @Query(value = "select count(u) as usageCount, u.dateOfRequest as dateOfRequest" +
            " from UsageHistory u" +
            " group by u.dateOfRequest" +
            " order by u.dateOfRequest desc")
    List<UsageHistoryCount> findUsageHistoryCount();
}
