package com.aircraft.fleet.controller;

import com.aircraft.fleet.model.SensorData;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api")  // Changed from "/api/sensors" to "/api"
public class SensorDataController {

    // Reads sensor_data.csv and returns all sensor data
    @GetMapping("/sensors")
    public List<SensorData> getAllSensors() {
        List<SensorData> dataList = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("sensor_data.csv"))) {
            br.readLine(); // skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                SensorData sd = new SensorData(
                    parts[0],            // timestamp
                    
                    Double.parseDouble(parts[1]),  // temperature
                    Double.parseDouble(parts[2]),  // fuelLevel
                    Double.parseDouble(parts[3])   // engineVibration
                );
                dataList.add(sd);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // Other example endpoints if needed, e.g., anomalies, alerts, etc.
    @GetMapping("/anomalies/local")
    public List<SensorData> getAnomalies() {
        List<SensorData> anomalies = new ArrayList<>();
        for (SensorData sd : getAllSensors()) {
            if (sd.getTemperature() > 290 || sd.getFuelLevel() < 30 || sd.getEngineVibration() > 2.5) {
                anomalies.add(sd);
            }
        }
        return anomalies;
    }

    // You can add other endpoints similarly...
}
