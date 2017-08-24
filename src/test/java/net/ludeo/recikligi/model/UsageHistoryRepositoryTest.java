package net.ludeo.recikligi.model;

import net.ludeo.recikligi.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {AppConfig.class})
@ExtendWith(SpringExtension.class)
@DisplayName("When searching for usage history")
class UsageHistoryRepositoryTest {

    private final UsageHistoryRepository usageHistoryRepository;

    @Autowired
    public UsageHistoryRepositoryTest(UsageHistoryRepository usageHistoryRepository) {
        this.usageHistoryRepository = usageHistoryRepository;
    }

    @BeforeEach
    void setUp() {
        this.usageHistoryRepository.deleteAll();
    }

    @Test
    void countTodayUsage() {
        addUsageHistory(yesterday());
        addUsageHistory(today());
        addUsageHistory(today());

        Long count = this.usageHistoryRepository.countByDateOfRequest(today());

        assertEquals(Long.valueOf(2), count);
    }

    @Test
    void countYesterdayUsage() {
        addUsageHistory(yesterday());
        addUsageHistory(yesterday());
        addUsageHistory(yesterday());
        addUsageHistory(today());

        Long count = this.usageHistoryRepository.countByDateOfRequest(yesterday());

        assertEquals(Long.valueOf(3), count);
    }

    private void addUsageHistory(LocalDate localDate) {
        UsageHistory usageHistory = new UsageHistory();
        usageHistory.setDateOfRequest(localDate);
        usageHistoryRepository.save(usageHistory);
    }

    private LocalDate today() {
        return LocalDate.now();
    }

    private LocalDate yesterday() {
        return LocalDate.now().minusDays(1L);
    }
}