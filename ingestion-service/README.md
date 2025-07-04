# Aircraft Sensor Data Ingestion (C++)

This module simulates real-time aircraft sensor data generation and writes it to a CSV file.

## 💡 Features

    - Generates random values for:
    - Temperature (200°C - 300°C)
    - Fuel Level (20% - 100%)
    - Engine Vibration (0.5g - 3.0g)
    - Writes data to `sensor_data.csv` with timestamp
    - Simulates data every second for 10 iterations

## 📁 Files

    - `sensor_simulator.cpp` – Main C++ file for data generation
    - `sensor_data.csv` – Generated output file (if run)
    - `README.md` – Module documentation

## ⚙️ How to Compile & Run

### Step 1: Compile
```bash
g++ sensor_simulator.cpp -o sensor_simulator
