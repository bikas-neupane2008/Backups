// This class represents a CounterMissile that is used to intercept and destroy incoming Missiles
class CounterMissile extends Missile {
  // Variables to hold the RGB values of the CounterMissile's color
  float r, g, b;

  // Constructor for a new CounterMissile at a given position with a given velocity
  CounterMissile(float x, float y, float dx, float dy) {
    // Call the super class (Missile) constructor
    super(x, y, dx, dy);
  }

  // Method to draw the CounterMissile
  void draw() {
    // Set the fill color to a random RGB value
    fill(random(255), random(255), random(255));
    ellipseMode(CENTER);
    
    // Update the position based on the velocity
    x += dx;
    y += dy;
    
    // If the CounterMissile hits the edge of the screen, reverse its horizontal direction
    if (x < 0 || x > width) {
      dx = -dx;
    }
    
    // Draw the CounterMissile as an ellipse at the current position
    ellipse(x, y, 10, 10);
  }
}
