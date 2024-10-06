// This class represents a Missile that moves across the screen
class Missile {
  // Variables to store the current position of the Missile
  float x, y;
  // Variables to store the velocity of the Missile
  float dx, dy;
  
  // Constructor for a new Missile at a given position with a given velocity
  Missile(float x, float y, float dx, float dy) {
    this.x = x;
    this.y = y;
    this.dx = dx;
    this.dy = dy;
  }
  
  // Update the position of the Missile based on its velocity
  void update() {
    x += dx;
    y += dy;
    
    // If the Missile hits the edge of the screen, reverse its horizontal direction
    if (x < 0 || x > width) {
      dx = -dx;
    }
  }
  
  // Draw the Missile
  void draw() {
    // Set the color to red
    fill(255, 0, 0);
    
    // Set the ellipse drawing mode to CENTER (the default is CORNER)
    ellipseMode(CENTER);
    
    // Draw an ellipse at the current position to represent the Missile
    ellipse(x, y, 10, 10);
  }
}
