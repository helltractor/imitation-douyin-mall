package com.helltractor.mall.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "helltractor.jwt")
public class JwtProperties {
    
    private String secretKey;
    
    private long ttl;
    
    private String tokenName;
    
}