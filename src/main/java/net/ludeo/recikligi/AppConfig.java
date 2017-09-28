package net.ludeo.recikligi;

import net.ludeo.recikligi.controller.DailyUsageLimitFilter;
import net.ludeo.recikligi.service.recognition.WatsonVisualRecognitionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = "net.ludeo.recikligi")
@PropertySource("classpath:application.yaml")
@EnableAutoConfiguration
public class AppConfig {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppConfig.class, args);
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }

    @Bean
    @ConditionalOnBean(WatsonVisualRecognitionService.class)
    public FilterRegistrationBean<DailyUsageLimitFilter> registerDailyUsageLimitIfWatsonServiceIsEnabled(DailyUsageLimitFilter filter) {
        FilterRegistrationBean<DailyUsageLimitFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setUrlPatterns(Arrays.asList("/camera", "/recyclable/*"));
        return registration;
    }
}
