// ---------------------------------------------------------------------------
// Example NewPing library sketch that does a ping about 20 times per second.
// ---------------------------------------------------------------------------

#include <NewPing.h>
#include "Tlc5940.h"

int SENSOR1_TP = 4;
int SENSOR1_EP = 5;
int SENSOR2_TP = 7;
int SENSOR2_EP = 6;
int SENSOR3_TP = 8;
int SENSOR3_EP =  9;

int distance1 = 0;
int distance2 = 0;
int distance3 = 0;


#define MAX_DISTANCE 50 // Maximum distance we want to ping for (in centimeters). Maximum sensor distance is rated at 400-500cm.
//#define RED 7
//#define GREEN 6
//#define BLUE 5


//NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE); // NewPing setup of pins and maximum distance.

void setup() {
  Tlc.init();
  Tlc.clear();

  pinMode(SENSOR1_TP, OUTPUT);
  pinMode(SENSOR1_EP, INPUT);
  pinMode(SENSOR2_TP, OUTPUT);
  pinMode(SENSOR2_EP, INPUT);
  pinMode(SENSOR3_TP, OUTPUT);
  pinMode(SENSOR3_EP, INPUT);
  
  Serial.begin(250000); // Open serial monitor at 115200 baud to see ping results.
}

void loop() {

  for(int i = 0; i < 16; i++) {
    Tlc.set(i, 4000);
    Tlc.update();
    delay(750);

    //Tlc.set(i, 0);
    //Tlc.update();
    //delay(250);
  }
  
// delay(500); // Wait 50ms between pings (about 20 pings/sec). 29ms should be the shortest delay between pings.
//
//  //int distance = sonar.ping_cm();
//  
//  distance1 = getDistance(SENSOR1_TP, SENSOR1_EP);
//  printDistance(1, distance1);
//  delay(150);
//  
//  distance2 = getDistance(SENSOR2_TP, SENSOR2_EP);
//  printDistance(2, distance2);
//  delay(150);
//  
//  distance3 = getDistance(SENSOR3_TP, SENSOR3_EP);
//  printDistance(3, distance3);
//  delay(150);
}

int getDistance (int initPin, int echoPin){
  digitalWrite(initPin, HIGH);
  delayMicroseconds(10); 
  digitalWrite(initPin, LOW); 
  unsigned long pulseTime = pulseIn(echoPin, HIGH); 
  return pulseTime/58;
}

void printDistance(int id, int dist) {
  Serial.print(id);
  if (dist >= 120 || dist <= 0 ){
    Serial.println(" Out of range");
  } else {
    for (int i = 0; i <= dist; i++) { 
      Serial.print("-");
    }
    Serial.print(dist, DEC);
    Serial.println(" cm");
  }  
 }


