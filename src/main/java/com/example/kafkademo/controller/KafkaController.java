package com.example.kafkademo.controller;

import com.example.kafkademo.kafka.comsumer.dto.ClientDTO;
import com.example.kafkademo.client.ClientService;
import com.example.kafkademo.kafka.producer.KafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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

    @PostMapping("/send/test/{count}")
    public void sendDeclarationMsgTest(@PathVariable Integer count) throws JsonProcessingException {
        for (int i = 0; i < count; i++) {

            ClientDTO clientDTO = new ClientDTO();
            if (i < count / 2) {
                clientDTO.setTenantId("tenantA");
            } else {
                clientDTO.setTenantId("tenantB");
            }
            clientDTO.setMessage("[" + i + "]" + LocalDateTime.now());
            clientService.processTenantMessage(clientDTO);
        }
//        for (int i = 0; i < 5; i++) {
//            ClientDTO clientDTO = new ClientDTO();
//            clientDTO.setTenantId("tenantB");
//            clientDTO.setMessage("[" + i + "]" + LocalDateTime.now());
//            clientService.processTenantMessage(clientDTO);
//        }
    }

    @PostMapping("/return")
    public void returnResultMsg(@RequestBody ClientDTO clientDTO) throws JsonProcessingException {
        clientService.processServerMessage(clientDTO);
    }
}
