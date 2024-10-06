package minigames.client.ponggame;

import java.awt.*;
import java.util.*;

public class Ball extends Rectangle {

  Random random;
  int horizontalSpeed;
  int verticalSpeed;
  int initialSpeed = 2;

  Ball(int x, int y, int width, int height) {
    super(x, y, width, height);
    random = new Random();
    int randomXDirection = random.nextInt(2);
    if (randomXDirection == 0) randomXDirection--;
    setHorizontalSpeed(randomXDirection * initialSpeed);

    int randomYDirection = random.nextInt(2);
    if (randomYDirection == 0) randomYDirection--;
    setVerticalSpeed(randomYDirection * initialSpeed);
  }

  public void setHorizontalSpeed(int speed) {
    horizontalSpeed = speed;
  }

  public void setVerticalSpeed(int speed) {
    verticalSpeed = speed;
  }

  public void move() {
    x += horizontalSpeed;
    y += verticalSpeed;
  }

  public void draw(Graphics g) {
    g.setColor(Color.ORANGE);
    g.fillOval(x, y, height, width);
  }
}
