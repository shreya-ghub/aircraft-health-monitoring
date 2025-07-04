package com.aircraft.fleet.controller;

import com.aircraft.fleet.model.SensorData;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api/sensors")
public class SensorDataController {

    @GetMapping("/all")
    public List<SensorData> getAllData() {
        List<SensorData> dataList = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("sensor_data.csv"))) {
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                SensorData sd = new SensorData(parts[0],
                        Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2]),
                        Double.parseDouble(parts[3]));
                dataList.add(sd);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @GetMapping("/alerts")
    public List<SensorData> getAlerts() {
        List<SensorData> alerts = new ArrayList<>();
        for (SensorData sd : getAllData()) {
            if (sd.getTemperature() > 290 || sd.getFuelLevel() < 30 || sd.getEngineVibration() > 2.5) {
                alerts.add(sd);
            }
        }
        return alerts;
    }
}
