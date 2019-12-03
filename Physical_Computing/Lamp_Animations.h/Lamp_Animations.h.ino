#include <Arduino.h>
#include <FastLED.h>
#include "AdjGraph.h"

#define LED_PIN     7
#define NUM_LEDS    120
CRGB leds[NUM_LEDS];
AdjGraph G; 
 int fadeAmount = 5;  
 int brightness = 0;

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
            
int Matrix[][13] = { {-1,-1,-1,0 ,1 ,2 , 3, 4 ,5 ,6 ,7,-1,-1},
                     {-1,18,17,16,15,14,13,12,11,10, 9, 8,-1},                   
                     {19,20,21,22,23,24,25,26,27,28,29,30,-1},
                     {43,42,41,40,39,38,37,36,35,34,33,32,31},
                     {44,45,46,47,48,49,50,51,52,53,54,55,56},
                     {69,68,67,66,65,64,63,62,61,60,59,58,57},
                     {70,71,72,73,74,75,76,77,78,79,80,81,82},
                     {-1,94,93,92,91,90,89,88,87,86,85,84,83},
                     {-1,95,96,97,98,99,100,101,102,103,104,105,-1},
                     {-1,-1,115,114,113,112,111,110,109,108,107,106,-1},
                     {-1,-1,116,117,118,118,-1,-1,-1,-1,-1,-1,-1} };

int Blue[][3] = { {0,0,255},
                  {25,25,112},
                  {135,206,235}};
                                       
int blue = 0;

void setup() {
  FastLED.addLeds<WS2812, LED_PIN, GRB>(leds, NUM_LEDS);
  FastLED.setBrightness(50);
  Serial.begin(9600);  

}

void fade(){
     
      brightness = brightness + fadeAmount;
      // reverse the direction of the fading at the ends of the fade: 
      if(brightness == 0 || brightness == 240)
      {
        fadeAmount = -fadeAmount ; 
      }    
      delay(80);  // This delay sets speed of the fade. I usually do from 5-75 but you can always go higher.
    
}

void iterate1(int s, int r, int g, int b){
  for(int i = 0; i < 7; i++){
     int led = G.getLED(s,i);
     Serial.print("1");
     Serial.println(led);
     leds[led] = CRGB (r,g,b);
   
  }
   delay(1000);
}


void iterate2(int s, int r, int g, int b){

  int next[12];
  int led = G.getLED(s,0);
  leds[led] = CRGB (r,g,b);
  int indx = 0; 
  for(int i = 1; i < 7; i++){
    int led = G.getLED(s,i); 
    Serial.print("2 ");
    Serial.println(led);
    next[indx] = G.getLED(led,i);
    next[indx+1] = G.getLED(led,i+1);
    if(i == 6){
      next[indx+1] = G.getLED(led, 1); 
    }
    indx += 2;
    
    leds[led] = CRGB (r,g,b);
    leds[led].fadeLightBy(brightness);
    FastLED.show();

    
    
  }
  for(int i = 0; i < 12; i++){
    Serial.print("2 ");
    Serial.println(next[i]);
    leds[next[i]] = CRGB (r,g,b); 
     leds[Ld[i]].fadeLightBy(brightness);
      FastLED.show();
  
  }

  delay(1000);
  
}

void start(int s){
  int count = 1; 
  while(count < 7){
    s = random(0,119);
    if(count % 2 == 0){
      int row = random(0,2);
      iterate1(s,Blue[row][0],Blue[row][1],Blue[row][2]);
    }else{
      int row = random(0,2);
      iterate2(s,Blue[row][0],Blue[row][1],Blue[row][2]);
    }
    count++;
  }
}

void animate(){
  for(int i = 0; i<120;i++){
    leds[Ld[i]] = CRGB(100,100,55);
    leds[Ld[i]].fadeLightBy(brightness);
    FastLED.show();
 
  }
}

void reverse(){
  for(int i = 119; i>=0;i--){
    leds[Ld[i]] = CRGB(55,100,100);
    leds[Ld[i]].fadeLightBy(brightness);
      FastLED.show();
  
  }
}



void loop() {
 //Serial.println("nieuw iteration");
 //animate();

 //for(int i = 255; i > 0;i--){
 // FastLED.setBrightness(i);
 //}
 //fade();
/*int s= 63;
 start(s);
 
 int count = 0;
 while(count < 3){
   
   s = random(0,255);
   start(s);
 }

//reverse();*/


for(int i = 0; i < 120; i++ )
   {  int row = random(0,1);
      leds[i].setRGB(Blue[row][0],Blue[row][1],Blue[row][2]);  // Set Color HERE!!!
      leds[i].fadeLightBy(brightness);
      
     }
      FastLED.show();
      fade();


    
      
}