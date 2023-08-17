float x, y, w;
float h, hb;
float s;
int gapsize = 150;
ArrayList<Pillar>pillars;
int count = 60;
int pspawntimer = 120;
int time = 0;
int starttime = 60;
Bird bird;

/*
Oh yea slight note before you play if you even read the code beforehand the game will force 
exit when you lose (going out of bounds or touching the pillars) because I am currently too 
lazy to code a restart screen and main menu states.

Also excuse the spaghetti code you see below i will hopefully not be too lazy to not clean it up
*/

void setup() {
  size(640, 360);
  s = 4; //speed
  y = 0;
  w = 50;
  x = width + w;
  h = random(1)*height/2;
  pillars = new ArrayList<Pillar>();
  bird = new Bird(width/6, height/2, 25, 0.15, 4); // last 2 variables change gravity and jump
  println(h);
}

void draw() {
  background(0);
  //troubleshooter vvv
  /*
  if (count == 0) {
    println(mouseX, mouseY);
    count = 60;
    time++;
    println(time);
  }
*/
  
  //bird player
  bird.display();
  if (starttime == 0){
    bird.movement();
  }
  
  //pillars
  
  if (pspawntimer == 0) {
    int updown = (int)(random(0,2)); //height difference deviation
    if (updown == 1){
      if (h-30 >= 0){
        h -= 30;
      } else {
        h += 30;
      }
    } else if (updown == 0){
      if (h+30+gapsize <= height){
        h += 30;
      } else {
        h -= 30;
      } 
    }
    hb = h + gapsize;
    pillars.add(new Pillar(x, y, w, h, s));
    pillars.add(new Pillar(x, hb, w, height, s));
    pspawntimer = 120;
    
    println(h);
  }

  for (int i = pillars.size()-1; i > 0; i--) {
    Pillar pillar = pillars.get(i);
    if (pillar.hitBoxDetect(bird.x, bird.y, bird.w)) { //mouseX/Y is a placeholder, replace with player coordinates
      //println("Detected");
      exit();
    }
    //moving pillar
    pillar.display();
    pillar.pillarMovement(); 
    
    if(pillar.offScreen()){
      pillars.remove(i);
      //println("removal");
    }
  }
  
  //outofbounds
  
  if(bird.y < 0 || bird.y > height){
    exit();
  }
  
  count--;
  pspawntimer--;
  if(starttime > 0){
    starttime--;
    text("Press Space to Flap (When this text disappears the game begins)", 100 , 20);
  }
}

class Pillar {

  float x, y, w, h, s;

  Pillar(float x, float y, float w, float h, float s) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.s = s;
  }

  boolean hitBoxDetect(float xpos, float ypos, float wid) {
    if (xpos+wid > x && xpos+wid < (x + w) && ypos > y && ypos < (y + h) || xpos+wid > x && xpos+wid < (x + w) && ypos+wid-5 > y && ypos+wid-5 < (y + h)) { //god damn this is way too long
      return true;
    }
    return false;
  }

  void pillarMovement() {
    x-=2; //placeholder
  }
  
  void display() {
      rect(x, y, w, h);
  }
  
  boolean offScreen(){
    if(x < (0 - w)){
      return true;
    }
    return false;
  }
}

class Bird {
  
  float x, y, w, gravity, jumpvelo;
  PVector pos;
  float g = 1;
  float vy = 0;
  
  Bird(float x, float y, float w, float gravity, float jumpvelo){
    this.x = x;
    this.y = y;
    this.w = w;
    this.gravity = gravity;
    this.jumpvelo = jumpvelo;
  }
  
  void movement(){
    vy += gravity;
    y += vy;
    if (keyPressed && key == ' '){
      vy = 0;
      vy -= jumpvelo;
    }
  }
  
  
  void display(){
    square(x, y, w);
  }
}
