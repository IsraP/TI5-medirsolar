#include "DHT.h"

#define DHTPin A1
#define DHTType DHT11
DHT sensor (DHTPin, DHTType);

void setup() {
  Serial.begin(9600);
  sensor.begin();

}

void loop() {
  float h = sensor.readHumidity();
  float t = sensor.readTemperature();
  
  if (isnan(t) || isnan(h)) 
  {
    Serial.print("F");
  } 
  else
  {
    Serial.print("S");
    Serial.print(" - ");
    Serial.print(h);
    Serial.print(" - ");
    Serial.print(t);
    Serial.print("\n");
  }
  delay(1000);
}
