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
    @GetMapping("/range")
    public List<SensorData> getDataInRange(@RequestParam long start, @RequestParam long end) {
     List<SensorData> result = new ArrayList<>();
        for (SensorData sd : getAllData()) {
        long ts = Long.parseLong(sd.getTimestamp());
        if (ts >= start && ts <= end) {
            result.add(sd);
        }
    }
    return result;
}
@GetMapping("/stats")
public Map<String, Double> getSummaryStats() {
    List<SensorData> data = getAllData();

    double avgTemp = data.stream().mapToDouble(SensorData::getTemperature).average().orElse(0);
    double minFuel = data.stream().mapToDouble(SensorData::getFuelLevel).min().orElse(0);
    double maxVibration = data.stream().mapToDouble(SensorData::getEngineVibration).max().orElse(0);

    Map<String, Double> stats = new HashMap<>();
    stats.put("averageTemperature", avgTemp);
    stats.put("minFuelLevel", minFuel);
    stats.put("maxVibration", maxVibration);

    return stats;
}
@GetMapping("/filter")
public List<SensorData> filterByParams(
        @RequestParam(required = false) Double tempMin,
        @RequestParam(required = false) Double tempMax,
        @RequestParam(required = false) Double fuelMin,
        @RequestParam(required = false) Double fuelMax,
        @RequestParam(required = false) Double vibrationMin,
        @RequestParam(required = false) Double vibrationMax) {

    List<SensorData> result = new ArrayList<>();

    for (SensorData sd : getAllData()) {
        if (tempMin != null && sd.getTemperature() < tempMin) continue;
        if (tempMax != null && sd.getTemperature() > tempMax) continue;
        if (fuelMin != null && sd.getFuelLevel() < fuelMin) continue;
        if (fuelMax != null && sd.getFuelLevel() > fuelMax) continue;
        if (vibrationMin != null && sd.getEngineVibration() < vibrationMin) continue;
        if (vibrationMax != null && sd.getEngineVibration() > vibrationMax) continue;

        result.add(sd);
    }

    return result;
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
