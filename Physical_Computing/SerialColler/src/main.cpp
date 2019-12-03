#include <Arduino.h>
#include <FastLED.h>

#define LED_PIN     7
#define NUM_LEDS    120
CRGB leds[NUM_LEDS];

int value; 

int Ld[] = {63,64,76,77,62,50,49,48,65,75,90,
            89,88,78,61,51,36,37,38,39,47,66,
            74,91,99,100,101,102,87,79,60,52,
            35,26,25,24,23,22,40,46,67,73,
            92,98,113,112,111,110,109,103,86,80,
            59,53,34,27,11,12,13,14,15,16,21,41,
            45,68,72,93,97,114,116,117,118,119,
            108,104,85,81,58,54,33,28,10,6,
            5,4,3,2,1,17,20,42,44,69,71,
            94,96,115,107,105,84,82,57,55,
            23,29,9,7,0,18,19,43,70,106,
            83,56,31,31,30,8,9,95};

// Variables que almacenarán el ultimo color de cada uno de los leds
 
int green = 0; 

void setup() {
  FastLED.addLeds<WS2812, LED_PIN, GRB>(leds, NUM_LEDS);
  // Iniciar el puerto serie.
  Serial.begin(9600);
  // Marcamos como salidas los pines utilizados.

  Serial.println("¡Preparado!");

 
}


void animate(int r, int g, int b){
 
  for(int i = 0; i<120;i++){
    leds[Ld[i]] = CRGB(r,g,b);
    FastLED.show();
    delay(30); 
    }
}

void animateRev(int r, int g, int b){
  for(int i = 119; i>= 0;i--){
    leds[Ld[i]] = CRGB(r,g,b);
    FastLED.show();
    delay(30); 
    }
}

void loop() {

  // Si hay informacion disponible en el puerto serie la utilizamos:
  while (Serial.available() > 0) {
    
     value = Serial.parseInt();

  }

  Serial.println(value);
22
    if(value == 1){
      animate(random(3,250),random(244,252),random(3,160));
      animateRev(random(3,250),random(244,252),random(3,160));
   
    }else if(value == 2){
      animate(random(3,1211),random(3,252),random(157,262));
      animateRev(random(3,111),random(3,252),random(157,262));
    }

}







