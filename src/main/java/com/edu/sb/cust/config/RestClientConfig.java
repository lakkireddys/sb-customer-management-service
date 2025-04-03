package com.edu.sb.cust.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestClientConfig {

    @Value("${app.address.url}")
    private String addressUrl;

    @Bean
    public RestClient restClient(){
        return RestClient.builder().baseUrl(addressUrl).build();
    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder().baseUrl(addressUrl).build();
    }

}
