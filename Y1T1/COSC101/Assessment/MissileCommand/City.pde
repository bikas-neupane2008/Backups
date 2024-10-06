// This class represents a city that is to be protected from missiles
class City {
  // Variables to hold the position of the city and whether it has been destroyed
  float x, y;
  boolean destroyed;

  // Constants for the dimensions of the city
  static final int WIDTH = 50;
  static final int HEIGHT = 20;

  // Constructor for a new City at a given position
  City(float x, float y) {
    this.x = x;
    this.y = y;
    this.destroyed = false;
  }

  // Method to update the city's status by checking for collisions with missiles
  void update() {
    for (int i = missiles.size() - 1; i >= 0; i--) {
      Missile missile = missiles.get(i);
      // If a collision occurs, mark the city as destroyed and remove the missile
      if (collidesWithMissile(missile)) {
        destroyed = true;
        missiles.remove(i);
        break;
      }
    }
  }

  // Method to draw the city
  void draw() {
    // Only draw the city if it is not destroyed
    if (!destroyed) {
      fill(0, 255, 0);
      rectMode(CENTER);
      rect(x, y, WIDTH, HEIGHT);
    }
  }

  // Method to check if a missile collides with the city
  boolean collidesWithMissile(Missile missile) {
    // Calculate the distance from the missile to the city
    float distanceX = Math.abs(missile.x - x);
    float distanceY = Math.abs(missile.y - y);

    // If the missile is outside the bounding box of the city plus some padding, there is no collision
    if (distanceX > (WIDTH / 2 + 5)) {
      return false;
    }
    if (distanceY > (HEIGHT / 2 + 5)) {
      return false;
    }

    // If the missile is inside the bounding box of the city, there is a collision
    if (distanceX <= (WIDTH / 2)) {
      return true;
    }
    if (distanceY <= (HEIGHT / 2)) {
      return true;
    }

    // If the missile is in the corner region of the bounding box, check for a collision based on distance to the corner
    float cornerDistance_sq = (float)(Math.pow((distanceX - WIDTH / 2), 2) + Math.pow((distanceY - HEIGHT / 2), 2));
    return (cornerDistance_sq <= (Math.pow(5, 2)));
  }
}
