package minigames.client.ponggame;
import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle {

  private int playerNumber;
  int verticalVelocity;
  static final int SPEED = 10;
  static final int PLAYER1_UP_KEY = KeyEvent.VK_W;
  static final int PLAYER1_DOWN_KEY = KeyEvent.VK_S;
  static final int PLAYER2_UP_KEY = KeyEvent.VK_UP;
  static final int PLAYER2_DOWN_KEY = KeyEvent.VK_DOWN;

  public Paddle(int x, int y, int width, int height, int playerNumber) {
    super(x, y, width, height);
    this.playerNumber = playerNumber;
  }

  public void keyPressed(KeyEvent e) {
    switch (playerNumber) {
      case 1:
        if (e.getKeyCode() == PLAYER1_UP_KEY) {
          setVerticalDirection(-SPEED);
        }
        if (e.getKeyCode() == PLAYER1_DOWN_KEY) {
          setVerticalDirection(SPEED);
        }
        break;
      case 2:
        if (e.getKeyCode() == PLAYER2_UP_KEY) {
          setVerticalDirection(-SPEED);
        }
        if (e.getKeyCode() == PLAYER2_DOWN_KEY) {
          setVerticalDirection(SPEED);
        }
        break;
    }
  }

  public void keyReleased(KeyEvent e) {
    switch (playerNumber) {
      case 1:
        if (e.getKeyCode() == PLAYER1_UP_KEY || e.getKeyCode() == PLAYER1_DOWN_KEY) {
          setVerticalDirection(0);
        }
        break;
      case 2:
        if (e.getKeyCode() == PLAYER2_UP_KEY || e.getKeyCode() == PLAYER2_DOWN_KEY) {
          setVerticalDirection(0);
        }
        break;
    }
  }

  public void setVerticalDirection(int verticalDirection) {
    verticalVelocity = verticalDirection;
  }

  public void move() {
    y += verticalVelocity;
  }

  public void draw(Graphics g) {
    if (playerNumber == 1) {
      g.setColor(Color.blue);
    } else {
      g.setColor(Color.red);
    }
    g.fillRect(x, y, width, height);
  }
}
