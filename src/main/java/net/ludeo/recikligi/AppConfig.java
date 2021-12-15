/*
   Copyright 2017-2021 Yvan Razafindramanana

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package net.ludeo.recikligi;

import net.ludeo.recikligi.controller.DailyUsageLimitFilter;
import net.ludeo.recikligi.service.recognition.AzureVisualRecognitionService;
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

    public static void main(String[] args) {
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
    public FilterRegistrationBean<DailyUsageLimitFilter> registerDailyUsageLimitIfWatsonVisualRecognition(DailyUsageLimitFilter filter) {
        FilterRegistrationBean<DailyUsageLimitFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setUrlPatterns(Arrays.asList("/camera", "/recyclable/*"));
        return registration;
    }

	@Bean
	@ConditionalOnBean(AzureVisualRecognitionService.class)
	public FilterRegistrationBean<DailyUsageLimitFilter> registerDailyUsageLimitIfAzureVisualRecognitionService(DailyUsageLimitFilter filter) {
		FilterRegistrationBean<DailyUsageLimitFilter> registration = new FilterRegistrationBean<>(filter);
		registration.setUrlPatterns(Arrays.asList("/camera", "/recyclable/*"));
		return registration;
	}
}
