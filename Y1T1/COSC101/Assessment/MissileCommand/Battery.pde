// This class represents the battery which fires counter-missiles to intercept incoming missiles
class Battery {
  // An array list to store counter-missiles that are fired
  ArrayList<CounterMissile> counterMissiles;
  
  // Constructor to create a new Battery
  Battery() {
    counterMissiles = new ArrayList<CounterMissile>();
  }
  
  // Method to update the position and status of each counter-missile and check for collisions with missiles
  void update() {
    // Create lists to store counter-missiles and missiles that need to be removed
    ArrayList<Missile> counterMissilesToRemove = new ArrayList<Missile>();
    ArrayList<Missile> missilesToRemove = new ArrayList<Missile>();
    
    // Loop over each counter-missile
    for (Missile missile : counterMissiles) {
      // Update the missile's position
      missile.update();
      // If the missile is at the edge of the screen, change its direction
      if (missile.x < 0 || missile.x > width) {
        missile.dx = -missile.dx;
      }
      
      // Loop over each incoming missile
      for (Missile m : missiles) {
        // If a counter-missile collides with an incoming missile, add both to the removal lists
        if (collidesWithMissile(missile, m)) {
          missilesToRemove.add(m);
          counterMissilesToRemove.add(missile);
          // Increase the score and play a collision sound
          score += 10;
          missileCollisionSound.play();
          break;
        }
      }
    }
    
    // Remove any collided missiles from their respective lists
    counterMissiles.removeAll(counterMissilesToRemove);
    missiles.removeAll(missilesToRemove);
  }
  
  // Method to fire a counter-missile towards a target
  void fire(float targetX, float targetY) {
    // Calculate the direction of the counter-missile
    float dx = (targetX - width / 2) / 50;
    float dy = (targetY - height + 10) / 50;
    // Play a firing sound and add the new counter-missile to the list
    fireSound.play();
    counterMissiles.add(new CounterMissile(width / 2, height - 10, dx, dy));
  }
  
  // Method to draw the battery and all counter-missiles
  void draw() {
    // Draw the battery
    fill(0, 0, 255);
    ellipseMode(CENTER);
    ellipse(width / 2, height - 10, 20, 20);
    // Draw each counter-missile
    for (CounterMissile counterMissile : counterMissiles) {
      counterMissile.draw();
    }
  }
  
  // Method to check if a counter-missile has collided with an incoming missile
  boolean collidesWithMissile(Missile counterMissile, Missile missile) {
    // Calculate the distance between the two missiles
    float distance = dist(counterMissile.x, counterMissile.y, missile.x, missile.y);
    // Return true if the distance is less than or equal to 10 (indicating a collision)
    return distance <= 10;
  }
}
