package com.example.kafkademo.controller;

import com.example.kafkademo.client.ClientDTO;
import com.example.kafkademo.client.ClientService;
import com.example.kafkademo.kafka.producer.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    @Autowired
    private KafkaProducer kafkaMessageProducer;
    @Autowired
    private ClientService clientService;

    @PostMapping("/send")
    public void sendDeclarationMsg(@RequestBody ClientDTO clientDTO) throws JsonProcessingException {
        clientService.processTenantMessage(clientDTO);
    }
    @PostMapping("/return")
    public void returnResultMsg(@RequestBody ClientDTO clientDTO) throws JsonProcessingException {
        clientService.processServerMessage(clientDTO);
    }
}
