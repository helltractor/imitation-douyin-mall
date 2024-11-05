package com.helltractor.mall.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

@Configuration
public class ProtobufConfiguration {
    
    @Bean
    public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }
    
    @Bean
    public HttpMessageConverters protobufHttpMessageConverters() {
        return new HttpMessageConverters(protobufHttpMessageConverter());
    }
}
