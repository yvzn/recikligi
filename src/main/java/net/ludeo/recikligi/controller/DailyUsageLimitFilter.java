package net.ludeo.recikligi.controller;

import lombok.Setter;
import net.ludeo.recikligi.model.UsageHistory;
import net.ludeo.recikligi.model.UsageHistoryRepository;
import net.ludeo.recikligi.service.WatsonVisualRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@Setter
@Component
@ConditionalOnBean(WatsonVisualRecognitionService.class)
public class DailyUsageLimitFilter implements Filter {

    @Value("${recikligi.daily.usage.limit}")
    private int dailyUsageLimit;

    private final UsageHistoryRepository usageHistoryRepository;

    @Autowired
    public DailyUsageLimitFilter(UsageHistoryRepository usageHistoryRepository) {
        this.usageHistoryRepository = usageHistoryRepository;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // nothing specific
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        if (isRequestSubjectToLimits(servletRequest)) {
            updateUsageHistory();
        }

        long nbUsagesToday = findNumberOfUsagesToday();
        if (nbUsagesToday < dailyUsageLimit) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/limit");
        }
    }

    private void updateUsageHistory() {
        UsageHistory usageHistory = new UsageHistory();
        usageHistory.setDateOfRequest(today());
        usageHistoryRepository.save(usageHistory);
    }

    private long findNumberOfUsagesToday() {
        return usageHistoryRepository.countByDateOfRequest(today());
    }

    private LocalDate today() {
        return LocalDate.now();
    }

    private boolean isRequestSubjectToLimits(ServletRequest servletRequest) {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        return requestURI.contains("/recyclable");
    }

    @Override
    public void destroy() {
        // nothing specific
    }
}
