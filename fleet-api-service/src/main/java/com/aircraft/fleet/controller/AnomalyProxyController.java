package com.aircraft.fleet.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AnomalyProxyController {

    @GetMapping("/api/anomalies")
    public ResponseEntity<String> getAnomalies() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("http://localhost:3001/api/anomalies", String.class);
        return ResponseEntity.ok(response);
    }
}
