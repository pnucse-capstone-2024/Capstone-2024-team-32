package ddalpi.backend.configuration;

import ddalpi.backend.filter.IPCountFilter;
import ddalpi.backend.service.DefenseInfoService;
import ddalpi.backend.service.RequestIPService;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfiguration {
    private final RequestIPService service;
    private final DefenseInfoService infoService;

    @Bean
    public FilterRegistrationBean<Filter> authFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new IPCountFilter(service, infoService));
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
