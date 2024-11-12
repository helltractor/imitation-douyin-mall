package com.helltractor.mall.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class SentinelRuleConfiguration {
    
    private static final Logger logger = LoggerFactory.getLogger(SentinelRuleConfiguration.class);
    
    @PostConstruct
    public void init() {
        logger.info("Load Sentinel Rules start!");
        // TODO: Load Sentinel Rules
        logger.info("Load Sentinel Rules end!");
    }
}
