package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@Service
public class MyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyService.class);

    @Value("${baseUrl}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public MyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void foo() {
        LOGGER.info("restTemplate: {}", restTemplate);
        String url = format("%s%s", baseUrl, "/api/test");
        restTemplate.getForObject(url, Void.class);
    }
}
