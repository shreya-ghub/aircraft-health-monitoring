const express = require('express');
const fs = require('fs');
const csv = require('csv-parser');

const app = express();
const PORT = 3001;
const FILE = '../fleet-api-service/sensor_data.csv';

app.get('/api/anomalies', (req, res) => {
    const anomalies = [];
    const dataRows = [];
  
    fs.createReadStream(FILE)
      .pipe(csv())
      .on('data', (row) => {
        dataRows.push({
          timestamp: row.Timestamp,
          temperature: parseFloat(row.Temperature),
          fuelLevel: parseFloat(row.Fuel_Level),
          engineVibration: parseFloat(row.Engine_Vibration),
        });
      })
      .on('end', () => {
        for (let i = 1; i < dataRows.length; i++) {
          const prev = dataRows[i - 1];
          const curr = dataRows[i];
  
          // Detect spikes: sudden temp increase > 5 degrees
          const tempSpike = (curr.temperature - prev.temperature) > 5;
          // Sudden drop in fuel > 10 units
          const fuelDrop = (prev.fuelLevel - curr.fuelLevel) > 10;
          // Vibration spike > 1 g
          const vibrationSpike = (curr.engineVibration - prev.engineVibration) > 1;
  
          // Or existing threshold conditions
          const thresholdExceeded = curr.temperature > 295 || curr.fuelLevel < 25 || curr.engineVibration > 3;
  
          if (tempSpike || fuelDrop || vibrationSpike || thresholdExceeded) {
            anomalies.push(curr);
          }
        }
        res.json(anomalies);
      });
  });
  

app.listen(PORT, () => {
  console.log(`Anomaly Detector running at http://localhost:${PORT}`);
});
