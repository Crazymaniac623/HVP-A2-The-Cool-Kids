class Birb {
  float x, y, r;
  float fs;
  float ac;
  boolean moving;

  Birb(float startx, float starty, float rad) {
    x = startx; 
    y = starty; 
    r = rad; 
    fs = 0; // Start with zero initial velocity
    ac = 0.2; // Adjust this value for acceleration strength
    moving = true;
  }

  void display() {
    fill(255);
    ellipse(x, y, r, r); // Use ellipse instead of circle
  }

  void update() {
    if (moving) {
      fs += ac; // Increase the velocity
      y += fs; // Update the position using velocity
    }
  }

  void move(float cx, float cy) {
    x += cx;
    y += cy;
  }

  void moveUp() {
    fs = jumpStrength; // Apply jump strength to the velocity
  }
  
  void reset() {
  y = height / 2;
  fs = 0;
}
  
  boolean isTouching(Brick brick) { 
    float birdLeft = x - r / 2;
    float birdRight = x + r / 2;
    float birdTop = y - r / 2;
    float birdBottom = y + r / 2;

    float brickLeft = brick.x;
    float brickRight = brick.x + brick.width;
    //float brickTop = 0;
    //float brickBottom = height;

    boolean isTouchingTopBrick = birdRight > brickLeft && birdLeft < brickRight && birdTop < brick.yTop; //this took a while for some reason
    boolean isTouchingBottomBrick = birdRight > brickLeft && birdLeft < brickRight && birdBottom > brick.yBottom;

    return isTouchingTopBrick || isTouchingBottomBrick;
  }
}
