#include <Arduino.h>
#include <FastLED.h>


#define LED_PIN     7
#define NUM_LEDS    120


CRGB leds[NUM_LEDS];
void setup() {
  FastLED.addLeds<WS2812, LED_PIN, GRB>(leds, NUM_LEDS);
}
void loop() {
  delay(2000);
  int L[120];
  for(int i = 0; i <= 119;i++){
    L[i] = i; 
  }

  for(int i = 0; i <= 119;i++){  
    int r =0;
    if(i > 110){
      r = random(i,119);
    }else{
      r = random(i,i+10);
    }
    int temp = L[i];
    L[i] = L[r];
    L[r] = temp;
  }

  for (int i = 0; i <= 119; i++) {
    leds[L[i]] = CRGB ( 0, 0, 255);
    FastLED.show();
    delay(40);
  }
  for (int i = 119 ; i >= 0; i--) {
    leds[L[i]] = CRGB ( 255, 0, 0);
    FastLED.show();
    delay(40);
  }
}