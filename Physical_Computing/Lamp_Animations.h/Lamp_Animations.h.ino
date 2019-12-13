#include <Arduino.h>
#include <FastLED.h>
#include "AdjGraph.h"

#define LED_PIN     7
#define NUM_LEDS    120
CRGB leds[NUM_LEDS];
AdjGraph G;


int sensorLEDS[][2] = {   {82, 84},
  {107, 86},
  {117, 99},
  {97, 91},
  {67, 65},
  {19, 20},
  {0, 15},
  {4, 25},
  {9, 28},
  {30, 34}
};

float sensorAverages[10];
float sensorCurrent[10];

int sensorsAnalog[] = {A0, A1, A2, A3, A4, A5, A6, A7, A10, A11};
int sensors[] = {22, 24, 26, 28, 30, 32, 34, 36, 40, 42};
int sensor = 0; // index of sensor to activate
int serialVal;
int brightness = 70;
int period = 100;
unsigned long time_now = 0;
boolean on = false;
int indx = 0;


void setup() {
  delay(3000);
  FastLED.addLeds<WS2812, LED_PIN, GRB>(leds, NUM_LEDS);
  FastLED.setBrightness(50);
  Serial.begin(115200);

  pinMode(22, OUTPUT);
  pinMode(24, OUTPUT);
  pinMode(26, OUTPUT);
  pinMode(28, OUTPUT);
  pinMode(30, OUTPUT);
  pinMode(32, OUTPUT);
  pinMode(34, OUTPUT);
  pinMode(36, OUTPUT);
  pinMode(40, OUTPUT);
  pinMode(42, OUTPUT);

  // Activate the sensors
  for (int i = 0; i < 10; i++ ) {
    digitalWrite(sensors[i], HIGH);
  }

  //Serial.println("Establishing baseline values");
  // Establish ground value for each sensor
  int startTime = millis();

  while ( millis() - startTime < 5000 ) {
    for (int i = 0; i < 10; i++ ) {
      int x = analogRead(sensorsAnalog[i]);
      sensorAverages[i] = (sensorAverages[i] * 0.9) + (x * 0.1);
    }
  }

  for (int i = 0; i < 10; i++ ) {
    Serial.println(sensorAverages[i]);
  }

  serialVal = 0;
}


void iterate1(int s, int r, int g, int b) {

  for (int i = 0; i < 7; i++) {
    int led = G.getLED(s, i);
    leds[led] = CRGB (r, g, b);
  }
}

int reactToTouch(int sens, int v) {
  int val = map(v, 100, sensorAverages[sens], 0, 100);

  if ( val < 50 ) {
    return sensorLEDS[sens][0];
  } else if ( val > 50 && val < 90 ) {
    return sensorLEDS[sens][1];
  } else {
    return -1;
  }

}

int activateSensor(int r, int g, int b) {
  if (sensor == 10) sensor = 0;
  int x = analogRead(sensorsAnalog[sensor]);
  int led = reactToTouch(sensor, x);
  if (led != -1)iterate1(led, r, g, b);
  sensor++;

}
//code translated from a python script 
//Author: Tony DiCola (tony@tonydicola.com)
void rainBowWheel(int led, int pos) {

  if (pos < 85) {
    leds[led].setRGB(pos * 3, 255 - pos * 3, 0);


  } else if (pos < 170) {
    pos -= 85;
    leds[led].setRGB(255 - pos * 3, 0, pos * 3);


  } else {
    pos -= 170;
    leds[led].setRGB(0, pos * 3, 255 - pos * 3);

  }
}

void rainbow() {


  for (int j = 0; j < 256; j++) {
    for (int i = 0; i < 120; i++) {
      activateSensor(255, 0, 0);
      rainbowWheel(i, (i + j) & 255);
    }
  }
}

void oceanWheel(int led, int pos) {
  if (pos < 85) {
    leds[led].setRGB(0, 255 - pos * 3, 255);

  } else if (pos < 128) {
    pos -= 85;
    leds[led].setRGB(pos * 2, 0, 255);

  } else if (pos < 170) {
    pos -= 128;
    leds[led].setRGB(85 - pos * 2, 0, 255);

  } else {
    pos -= 170;
    leds[led].setRGB(0, pos * 3, 255);

  }
}


void forestWheel(int led, int pos) {

  if (pos < 85) {
    leds[led].setRGB(255 - pos * 3, 200, 20);

  } else if (pos < 128) {
    pos -= 85;
    leds[led].setRGB(pos * 6, 200, 20);

  } else if (pos < 170) {
    pos -= 128;

    leds[led].setRGB(255 - pos * 6, 200, 20);
  } else {
    pos -= 170;
    leds[led].setRGB(pos * 3, 200, 20);
  }
}



void theme(int t, int j) {
  
    for(int i = 0; i < 120; i++){
    if(t == 1){
    oceanWheel(i,(i+j) & 255);
    }else if(t == 3){
    forestWheel(i,(i+j) & 255);
    }
  }


  if (t == 1) {
    activateSensor(255, 145, 200);
  } else if (t == 3) {
    activateSensor(247, 118, 62);
  }

  if (!on) fadeIn();
}

void fadeIn() {
  for (int i = 0; i <= brightness; i++) {
    FastLED.setBrightness(i);
    delay(20);
    FastLED.show();
  }
  on = true;
 
}

void fadeOut() {

  if (on) {

    for (int i = brightness; i >= 0; i--) {
      FastLED.setBrightness(i);
      delay(20);
      FastLED.show();
    }
  }
  on = false;

}


void playTheme(int t) {
  if(millis() > time_now + period){
   time_now = millis();
  if (indx < 256) {
    theme(t, indx);
    indx++;
  } else {
    indx = 0;
   }
  }
}

void loop() {

  Serial.setTimeout(10);
  int currentVal = Serial.parseInt();

  if (currentVal >= 1 && currentVal <= 204) {
    if (serialVal != currentVal) {
      serialVal = currentVal;
    }
  }

  if (serialVal == 1) {
    int start = millis();
    playTheme(1);
      
  } else if (serialVal == 3 ) {
    playTheme(3);

  } else if (serialVal == 2) {
    fadeOut();
    brightness = 60;
    
  } else if(serialVal > 3 && serialVal <= 204){
    int b = serialVal -4;
    FastLED.setBrightness(b);
    brightness = b;
  }
   FastLED.show();
}
