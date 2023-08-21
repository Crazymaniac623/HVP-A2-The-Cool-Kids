class Brick {
  boolean passed = false;
  float x, yTop, yBottom;
  float speed = 8;
  float width = 70;
  float gap = 200; // Gap between top and bottom bricks
  float spacing = 500; // Adjust this value to change the distance between bricks

  Brick(float x_) {
    x = x_;
    reset();
  }

  void reset() {
  yTop = random(height * 0.3, height * 0.7);
  yBottom = yTop + gap;
  passed = false; // Reset the passed flag along with other properties
  }

  void update() {
    x -= speed;
    if (x + width < 0) {
      x = width + spacing * (brickCount - 1); // Reset on the right
      reset();
    }
  }

  void display() {
    fill(150);
    rect(x, 0, width, yTop);
    rect(x, yBottom, width, height - yBottom);
  }
}
