package com.aircraft.fleet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorData {
    private String timestamp;
    
    private double temperature;
    private double fuelLevel;
    private double engineVibration;
}
