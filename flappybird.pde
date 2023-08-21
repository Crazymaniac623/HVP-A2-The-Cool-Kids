//many code stolen from my past processing projects :)

float x5;
float y5;
float velocity = 0;
float jumpStrength = -15;
float gravity = 0.981;
int score = 0;
int delay = 5;
boolean isGameOver = false; 
boolean passed = false;

Birb playerx;
Brick[] bricks;

int brickCount = 4; // number of visible bricks (i don't think it works rn tho lol)

void setup() {
  fullScreen();
  playerx = new Birb(width - width + 50, height / 2, 50);
  bricks = new Brick[brickCount];
  
  for (int i = 0; i < brickCount; i++) {
    bricks[i] = new Brick(width + i * width * 3);
  }
}

void draw() {
  background(200);
  fill(155, 50, 10);
  textSize(100);
  text(score, width-100, 100); 
  if (!isGameOver) {
    playerx.display();
    updateBirdPosition();

    for (Brick brick : bricks) {
      brick.update();
      brick.display();

      if (playerx.isTouching(brick)) { //detection
        gameOver(); 
      }
      
      if (brick.x + brick.width < playerx.x && !brick.passed) {
        score++; 
        brick.passed = true;
      } 
    }
  } else {
    displayGameOver();
  }
}

void keyPressed() {
  if(key == ' ')
  {
    delay++;
  }
  if (key == ' ' && !isGameOver && delay > 10000) {
    velocity = jumpStrength; // apply jump strength
    delay = 0;
  } else if (key == ' ' && isGameOver) {
    resetGame();
  }
} 

void updateBirdPosition() { 
  velocity += gravity;
  y5 += velocity;

  if (y5 > height - playerx.r/2) { //playerx.r/2 cus half radius of circle
    y5 = height - playerx.r/2;
    velocity = 0;
    gameOver();
  } else if (y5 < playerx.r/2) {
    y5 = playerx.r/2;
    velocity = 0;
  }

  playerx.move(0, velocity);
  playerx.y = y5;
}

void gameOver() {
  isGameOver = true;
}

void resetGame() {
  isGameOver = false;
  y5 = height / 2;
  velocity = 0;
  score = 0;
  for (Brick brick : bricks) {
    brick.reset();
  }
}

void displayGameOver() {
  textAlign(CENTER, CENTER);
  textSize(100);
  fill(125, 55, 100);
  text("You Lost", width / 2, height / 2 - 50);
  textSize(24);
  fill(0);
  text("Press Space to Retry", width / 2, height / 2 + 50);
}
