package net.ludeo.recikligi.model;

import java.time.LocalDate;

public interface UsageHistoryCount {

    long getUsageCount();

    void setUsageCount(long usageCount);

    LocalDate getDateOfRequest();

    void setDateOfRequest(LocalDate dateOfRequest);
}
