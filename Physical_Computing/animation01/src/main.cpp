#include <Arduino.h>
#include <FastLED.h>
#include "AdjGraph.h"

#define LED_PIN     7
#define NUM_LEDS    120
CRGB leds[NUM_LEDS];

AdjGraph G; 

CRGB leds[NUM_LEDS];
void setup() {
  FastLED.addLeds<WS2812, LED_PIN, GRB>(leds, NUM_LEDS);
}


void lightUp(int row, int col, int r, int g, int b){
  leds[G.getLED[row][col]] = CRGB (r,g,b);
  FastLED.show();
  delay(30);
}


void goMinus(int srow, int scol, int r, int g, int b){
    int row = srow; 
    int col = scol; 
    int iter = 0; 
    while(iter < 6){

     
    for(int i = col; i >=0; i--){
      lightUp(row,col,r,g,b);
    }
     
    for(int i = col ; i<13; i++){
      lightUp(row,i,r,g,b);
    }
      for(int i = row; i >= 0; i--){
      lightUp(i,col,r,g,b);
    } 

      for(int i = row; i < 11; i++){
      lightUp(i,col,r,g,b);
    }  

    col--;
    row--;
    iter++;
  }
}

void goPlus(int srow, int scol, int r, int g, int b){
  int row = srow; 
  int col = scol; 

  int iter = 0; 
  while(iter < 6){

     for(int i = row; i < 11; i++){
      lightUp(i,col,r,g,b);
    }  
   
    for(int i = row; i >= 0; i--){
      lightUp(i,col,r,g,b);
    } 
    for(int i = col ; i<13; i++){
      lightUp(row,i,r,g,b);
    }

    for(int i = col; i >=0; i--){
      lightUp(row,col,r,g,b);
    }
    col++;
    row++;
    iter++;
  }
}


void animate01(int startRow, int startColumn, int r, int g, int b){
  int row = startRow; 
  int column = startColumn;
  leds[Matrix[row][column]] = CRGB (r,g,b);
  FastLED.show();
  delay(40);
  goPlus(row, column, r,g,b);
  goMinus(row,column, r,g,b);
  
}

void animate02(int startRow, int startColumn, int r, int g, int b){
  int row = startRow; 
  int column = startColumn;
  leds[Matrix[row][column]] = CRGB (r,g,b);
  FastLED.show();
  delay(40);
  goMinus(row,column, r,g,b);
  goPlus(row, column, r,g,b);
  
  
}

void loop() {

 int x = random(0,12);
 int y = random(0,12);
 int r = random(0,255);
 int g = random(0,255);
 int b = random(0,255);
 animate01(x,y,r,g,b);
 int x2 = random(0,12);
 int y2 = random(0,12);
 int r2 = random(0,255);
 int g2 = random(0,255);
 int b2 = random(0,255);
 animate02(x2,y2,r2,g2,b2);
}

