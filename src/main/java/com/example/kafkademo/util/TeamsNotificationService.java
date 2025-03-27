package com.example.kafkademo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author paul
 */
@Service
@Slf4j
public class TeamsNotificationService {

    @Value("https://clearexpress812.webhook.office.com/webhookb2/f5d6c342-e613-4e00-b8cf-fd4951bb056f@3c4a200c-7230-4b9d-8997-23519381df84/IncomingWebhook/cfd2ffb2ff26489f91b7f9a9fd74ae6d/226a7d7a-4487-4035-b17e-7a9e0d891c10/V24YGRomAU9QqyRSS7Z0BlOZ-c1AcgS2nKkgQMr-BtXqo1")
    private String webhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        Map<String, String> messagePayload = new HashMap<>();
        messagePayload.put("text", message);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(messagePayload, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                webhookUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Message sent successfully: {}", response.getStatusCode());
        } else {
            log.error("Error sending message: {}", response.getStatusCode());
        }
    }
}
