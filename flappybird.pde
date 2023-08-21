float x, y, w;
float h, hb;
float s;
int gapsize = 150;
ArrayList<Pillar>pillars;
int count = 60;
int pspawntimer = 120;
int time = 0;
int starttime = 60;
int score = 0;
Bird bird;
MenuButton startbutton;
MenuButton settingsbutton;
MenuButton resetbutton;
MenuButton settingstostartbutton;
MenuButton resettomenubutton;
Menu menu;

/*
scroll down for the classes (im too lazy to put them into seperate scripts)
 */

void setup() {
  size(640, 360);
  s = 4; //speed
  y = 0;
  w = 50;
  x = width + w;
  h = random(1)*height/2;
  pillars = new ArrayList<Pillar>();
  bird = new Bird(width/6, height/2, 25, 0.20, 4.5); // last 2 variables change gravity and jump
  menu = new Menu();
  startbutton = new MenuButton((width/2)-50, (height/2)-10, 100, 20);
  settingsbutton = new MenuButton((width/2)-50, (height-50)-10, 100, 20);
  resetbutton = new MenuButton((width/2)-50, (height/2)-10, 100, 20);
  settingstostartbutton = new MenuButton(width - 100, height - 40, 80, 20);
  resettomenubutton = new MenuButton((width/2)-50, (height-50)-30, 100, 20);
}

void draw() {
  background(0);
  if (menu.gameState == 0) {
    startbutton.display();
    settingsbutton.display();
    if (startbutton.buttonHitBox()) {
      menu.gameView();
    }
    if (settingsbutton.buttonHitBox()) {
      menu.settingsMenu();
    }
  }
  if (menu.gameState == 1) {
    text("settings here", width/2, height/2);
    settingstostartbutton.display();
    if (settingstostartbutton.buttonHitBox()) {
      menu.startMenu();
    }
  }
  if (menu.gameState == 2) {
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
    if (starttime == 0) {
      bird.movement();
    }

    //pillars

    if (pspawntimer == 0) {
      int updown = (int)(random(0, 2)); //height difference deviation
      float deviation = random(10, 30);
      if (updown == 1) {
        if (h-deviation >= 0) {
          h -= deviation;
        } else {
          h += deviation;
        }
      } else if (updown == 0) {
        if (h+30+gapsize <= height) {
          h += 30;
        } else {
          h -= 30;
        }
      }


      hb = h + gapsize;
      pillars.add(new Pillar(x, y, w, h, s));
      pillars.add(new Pillar(x, hb, w, height, s));
      pspawntimer = 120;
    }

    for (int i = pillars.size()-1; i > 0; i--) {
      Pillar pillar = pillars.get(i);
      if (pillar.hitBoxDetect(bird.x, bird.y, bird.w)) { //mouseX/Y is a placeholder, replace with player coordinates
        //println("Detected");
        menu.deathScreen();
      }
      //moving pillar
      pillar.display();
      pillar.pillarMovement();

      if (pillar.offScreen()) {
        pillars.remove(i);
        //println("removal");
      }

      if (pillar.score(bird.x)) {
        score++;
        //println(score/2);
      }
    }

    //outofbounds

    if (bird.y < 0 || bird.y > height) {
      menu.deathScreen();
    }

    count--;
    pspawntimer--;
    if (starttime > 0) {
      starttime--;
      text("Press Space or M1 to Flap (When this text disappears the game begins)", 100, 20);
    }

    text("Score: " + score/2, width - 100, height - 20);
  }
  if (menu.gameState == 3) {
    resetbutton.display();
    resettomenubutton.display();
    while (pillars.size() > 0) {
      pillars.remove(0);
    }
    if (resetbutton.buttonHitBox()) {
      menu.gameView();
      starttime = 60;
      bird.reset(width/6, height/2, 25, 0.20, 4.5);
      bird.vy = 0;
      score = 0;
    }
    if (resettomenubutton.buttonHitBox()) {
      menu.startMenu();
    }
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
    if (xpos+wid > x && xpos+wid < (x + w) && ypos > y && ypos < (y + h) || xpos+wid > x && xpos+wid < (x + w) && ypos+wid-5 > y && ypos+wid-5 < (y + h) || xpos> x && xpos < (x + w) && ypos+wid-5 > y && ypos+wid-5 < (y + h) || xpos > x && xpos < (x + w) && ypos > y && ypos < (y + h)) { //god damn this is way too long
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

  boolean offScreen() {
    if (x < (0 - w)) {
      return true;
    }
    return false;
  }

  boolean score(float xpos) {
    if (xpos == x) {
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

  Bird(float x, float y, float w, float gravity, float jumpvelo) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.gravity = gravity;
    this.jumpvelo = jumpvelo;
  }

  void movement() {
    vy += gravity;
    y += vy;
    if (keyPressed && key == ' ' || mousePressed) {
      vy = 0;
      vy -= jumpvelo;
    }
  }

  void reset(float x, float y, float w, float gravity, float jumpvelo) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.gravity = gravity;
    this.jumpvelo = jumpvelo;
  }


  void display() { //replace square with image
    square(x, y, w);
  }
}

class Menu { //i dont want to deal with this
  int gameState = 0;

  void startMenu() {
    gameState = 0;
  }

  void settingsMenu() {
    gameState = 1;
  }

  void gameView() {
    gameState = 2;
  }

  void deathScreen() {
    gameState = 3;
  }
}

class MenuButton {
  int x, y, w, h;

  MenuButton(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  boolean buttonHitBox() {
    if (mousePressed) {
      if (mouseX > x && mouseX < x+w && mouseY > y && mouseY < y+h) {
        return true;
      }
    }
    return false;
  }

  void display() {
    rect(x, y, w, h);
  }
}
