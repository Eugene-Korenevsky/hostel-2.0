package com.example.hostel1;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:global.properties")
@ConfigurationProperties
@Data
public class AppProperties {
    private String defaultRole;
    private String adminRole;
    private String arriveTime;
}
