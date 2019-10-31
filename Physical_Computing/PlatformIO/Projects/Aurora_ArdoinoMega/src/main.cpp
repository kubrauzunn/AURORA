#include <Arduino.h>
#include <FastLED.h>


#define LED_PIN     7
#define NUM_LEDS    60

CRGB leds[NUM_LEDS];
void setup() {
  FastLED.addLeds<WS2812, LED_PIN, GRB>(leds, NUM_LEDS);
  
}

void loop() {
  int n = 0;

  for (int i = 0; n <= NUM_LEDS; i++) {
    if (NUM_LEDS < i){
      leds[i] = CRGB ( 0, 0, 255);
      FastLED.show();
      delay(40);
    }
    
    if (NUM_LEDS/2 < i){
      int n = 0;
       leds[n] = CRGB ( 0, 0, 0);
    }

  }

