/*
  'a' - motor A on
  'b' - motor A off
  'c' - motor B on
  'd' - motor B off
  'e' - motor C on
  'f' - motor C off
  'g' - motor D on
  'h' - motor D off

  modified 23 September 2015
  by Austin Nelson
 */

#include <MeetAndroid.h>

// declare MeetAndroid so that you can call functions with it
MeetAndroid meetAndroid;

const int AIA = 5;
const int AIB = 4;
const int BIA = 7;
const int BIB = 6;
const int CIA = 8;
const int CIB = 9;
const int DIA = 10;
const int DIB = 11;

const int red = 2;
const int green = 3;
const int blue = 12;
const int yellow = 13;

byte speed = 245;  // change this (0-255) to control the speed of the motors 

// the setup function runs once when you press reset or power the board
void setup() {
  Serial.begin(115200); 

  meetAndroid.registerFunction(messageForA, 'a');
  meetAndroid.registerFunction(messageForB, 'b');
  meetAndroid.registerFunction(messageForC, 'c');
  meetAndroid.registerFunction(messageForD, 'd');
  meetAndroid.registerFunction(simonSays, 's');
  
  // initialize digital pin 13 as an output.
  pinMode(AIA, OUTPUT); // set pins to output
  pinMode(AIB, OUTPUT);
  pinMode(BIA, OUTPUT); // set pins to output
  pinMode(BIB, OUTPUT);
  pinMode(CIA, OUTPUT); // set pins to output
  pinMode(CIB, OUTPUT);
  pinMode(DIA, OUTPUT); // set pins to output
  pinMode(DIB, OUTPUT);

  pinMode(red, OUTPUT);
  pinMode(green, OUTPUT);
  pinMode(blue, OUTPUT);
  pinMode(yellow, OUTPUT);

  digitalWrite(red, HIGH);
  digitalWrite(green, HIGH);
  digitalWrite(blue, HIGH);
  digitalWrite(yellow, LOW);
}

// the loop function runs over and over again forever
void loop() {
  meetAndroid.receive(); // you need to keep this in your loop() to receive events
}

void simonSays(byte flag, byte numOfValues) {
  // create an array where all event values should be stored
  // the number of values attached to this event is given by
  // a parameter(numOfValues)
  int data[numOfValues];
  
  // call the library function to fill the array with values
  meetAndroid.getIntValues(data);
  
  // access the values
  for (int i=0; i<numOfValues;i++) {
    switch(data[i]) {
      case 1:
        simonSaysA();
        break;
      case 2:
        simonSaysB();
        break;
      case 3:
        simonSaysC();
        break;
      case 4:
        simonSaysD();
        break;
    }

    delay(500);
  }
}

void messageForA(byte flag, byte numOfValues) {
  if(meetAndroid.getInt() == 1) {
    turnAOn();
    digitalWrite(blue, LOW);
  } else {
    turnAOff();
    digitalWrite(blue, HIGH);
  }
}

void messageForB(byte flag, byte numOfValues) {
  if(meetAndroid.getInt() == 1) {
    turnBOn();
    digitalWrite(red, LOW);
  } else {
    turnBOff();
    digitalWrite(red, HIGH);
  }
}

void messageForC(byte flag, byte numOfValues) {
  if(meetAndroid.getInt() == 1) {
    turnCOn();
    digitalWrite(yellow, LOW);
  } else {
    turnCOff();
    digitalWrite(yellow, HIGH);
  }
}

void messageForD(byte flag, byte numOfValues) {
  if(meetAndroid.getInt() == 1) {
    turnDOn();
    digitalWrite(green, HIGH);
  } else {
    turnDOff();
    digitalWrite(green, LOW);
  }
}

void simonSaysA() {
  analogWrite(AIA, 0);
  analogWrite(AIB, speed);
  digitalWrite(blue, LOW);
  delay(500);
  analogWrite(AIB, 0);
  digitalWrite(blue, HIGH);
}

void simonSaysB() {
  analogWrite(BIA, 0);
  analogWrite(BIB, speed);
  digitalWrite(red, LOW);
  delay(500);
  analogWrite(BIB, 0);
  digitalWrite(red, HIGH);
}

void simonSaysC() {
  analogWrite(CIA, 0);
  analogWrite(CIB, speed);
  digitalWrite(yellow, HIGH);
  delay(500);
  analogWrite(CIB, 0);
  digitalWrite(yellow, LOW);
}

void simonSaysD() {
  analogWrite(DIA, 0);
  analogWrite(DIB, speed);
  digitalWrite(green, LOW);
  delay(500);
  analogWrite(DIB, 0);
  digitalWrite(green, HIGH);
}

void turnAOff() {
  analogWrite(AIA, 0);
  analogWrite(AIB, 0); 
}

void turnAOn() {
  analogWrite(AIA, 0);
  analogWrite(AIB, speed);
  delay(400);
  analogWrite(AIB, 150);
}

void turnBOff() {
  analogWrite(BIA, 0);
  analogWrite(BIB, 0);
}

void turnBOn() {
  analogWrite(BIA, 0);
  analogWrite(BIB, speed);
  delay(400);
  analogWrite(BIB, 150); 
}

void turnCOff() {
  analogWrite(CIA, 0);
  analogWrite(CIB, 0); 
}

void turnCOn() {
  analogWrite(CIA, 0);
  analogWrite(CIB, speed);
  delay(400);
  analogWrite(CIB, 150);
}

void turnDOff() {
  analogWrite(DIA, 0);
  analogWrite(DIB, 0); 
}

void turnDOn() {
  analogWrite(DIA, 0);
  analogWrite(DIB, speed);
  delay(400);
  analogWrite(DIB, 150);
}

