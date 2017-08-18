package net.ludeo.recikligi.controller;

import lombok.Setter;
import net.ludeo.recikligi.service.WatsonVisualRecognitionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Setter
@Component
@ConditionalOnBean(WatsonVisualRecognitionService.class)
public class DailyUsageLimitFilter implements Filter {

    @Value("${recikligi.daily.usage.limit}")
    private int dailyUsageLimit;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // nothing specific
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        if (isRequestSubjectToLimits(servletRequest)) {
            --dailyUsageLimit;
        }

        if (dailyUsageLimit > 0) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect("/limit");
        }
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
