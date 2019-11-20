
#include <Arduino.h>
#include <FastLED.h>


#define LED_PIN     7
#define NUM_LEDS    120

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


CRGB leds[NUM_LEDS];
void setup() {
  FastLED.addLeds<WS2812, LED_PIN, GRB>(leds, NUM_LEDS);
}

void lightUp(int row, int col, int r, int g, int b){
  leds[Matrix[row][col]] = CRGB (r,g,b);
  FastLED.show();
  delay(40);
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
