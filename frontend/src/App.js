import React, { useEffect, useState } from "react";
import axios from "axios";
import './App.css';

function App() {
  const [sensorData, setSensorData] = useState([]);
  const [anomalies, setAnomalies] = useState([]);

  useEffect(() => {
    // Correct endpoint for all sensor data
    axios.get("http://localhost:8080/api/sensors")
      .then(res => setSensorData(res.data))
      .catch(err => console.error(err));
  
    // Correct endpoint for anomalies
    axios.get("http://localhost:8080/api/anomalies")
      .then(res => setAnomalies(res.data))
      .catch(err => console.error(err));
  }, []);

  // Helper to check if a sensor record is anomalous by timestamp and other properties (no sensorId now)
  const isAnomaly = (record) => {
    return record.temperature > 290 || record.fuelLevel < 30 || record.engineVibration > 2.5;
  };
  

  return (
    <div style={{ padding: "20px" }}>
      <h1>Aircraft Sensor Data</h1>
      <table border="1" cellPadding="8" cellSpacing="0">
        <thead>
          <tr>
            <th>Timestamp</th>
            <th>Temperature</th>
            <th>Fuel Level</th>
            <th>Engine Vibration</th>
            <th>Anomaly</th>
          </tr>
        </thead>
        <tbody>
          {sensorData.map((record, idx) => (
            <tr key={idx} style={{ backgroundColor: isAnomaly(record) ? '#f99' : 'transparent' }}>
              <td>{record.timestamp}</td>
              <td>{record.temperature}</td>
              <td>{record.fuelLevel}</td>
              <td>{record.engineVibration}</td>
              <td>{isAnomaly(record) ? "⚠️" : "✔️"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default App;
