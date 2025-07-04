#include <iostream>
#include <fstream>
#include <cstdlib>
#include <ctime>
#include <thread>
#include <chrono>

using namespace std;

float generateRandom(float min, float max) {
    return min + static_cast<float>(rand()) / (static_cast<float>(RAND_MAX / (max - min)));
}

int main() {
    srand(time(0));  // Seed random number

    ofstream outFile("sensor_data.csv");
    outFile << "Timestamp,Temperature,Fuel_Level,Engine_Vibration\n";

    for (int i = 0; i < 10; i++) {
        time_t now = time(0);
        float temp = generateRandom(200.0, 300.0);       // e.g., °C
        float fuel = generateRandom(20.0, 100.0);        // Percentage
        float vibration = generateRandom(0.5, 3.0);      // G-force

        outFile << now << "," << temp << "," << fuel << "," << vibration << "\n";

        this_thread::sleep_for(chrono::seconds(1));
        cout << "Sensor data written at time: " << now << endl;
    }

    outFile.close();
    return 0;
}
