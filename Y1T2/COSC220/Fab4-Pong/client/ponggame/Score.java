package minigames.client.ponggame;
import java.awt.*;

public class Score extends Rectangle {

  static int gameWidth;
  static int gameHeight;
  public int player1Score;
  public int player2Score;

  public Score(int gameWidth, int gameHeight) {
    Score.gameWidth = gameWidth;
    Score.gameHeight = gameHeight;
  }

  public void draw(Graphics g) {
    g.setColor(Color.white);
    g.setFont(new Font("Consolas", Font.PLAIN, 60));

    g.drawLine(gameWidth / 2, 0, gameWidth / 2, gameHeight);

    g.drawString(
      String.valueOf(player1Score / 10) + String.valueOf(player1Score % 10),
      (gameWidth / 2) - 85,
      50
    );
    g.drawString(
      String.valueOf(player2Score / 10) + String.valueOf(player2Score % 10),
      (gameWidth / 2) + 20,
      50
    );
  }
}
