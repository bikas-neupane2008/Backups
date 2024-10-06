package minigames.client.ponggame;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GamePanel extends JPanel implements Runnable {
    private static final Logger logger = LogManager.getLogger(GamePanel.class);

    static final int GAME_WIDTH = 800;
    static final int GAME_HEIGHT = 600;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    private boolean isGameRunning = false; // Added for game start/pause

    public GamePanel() {
        newPaddles();
        newBall();


        score = new Score(GAME_WIDTH, GAME_HEIGHT);

        this.setPreferredSize(SCREEN_SIZE);
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                logger.info("Panel gained focus.");
            }

            @Override
            public void focusLost(FocusEvent e) {
                logger.info("Panel lost focus.");
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) { // Toggle game start/pause on spacebar
                    toggleGamePause();
                }
                paddle1.keyPressed(e);
                paddle2.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                paddle1.keyReleased(e);
                paddle2.keyReleased(e);
            }
        });

        gameThread = new Thread(this);
        gameThread.start();
    }

    // Method to toggle game pause
    private void toggleGamePause() {
        isGameRunning = !isGameRunning;
    }

    public void newBall() {
        random = new Random();
        ball =
            new Ball(
                (GAME_WIDTH / 2) - (BALL_DIAMETER / 2),
                random.nextInt(GAME_HEIGHT - BALL_DIAMETER),
                BALL_DIAMETER,
                BALL_DIAMETER
            );
    }

    public void newPaddles() {
        paddle1 =
            new Paddle(
                0,
                (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2),
                PADDLE_WIDTH,
                PADDLE_HEIGHT,
                1
            );
        paddle2 =
            new Paddle(
                GAME_WIDTH - PADDLE_WIDTH,
                (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2),
                PADDLE_WIDTH,
                PADDLE_HEIGHT,
                2
            );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method first
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();

        // Set the background color to black
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        Toolkit.getDefaultToolkit().sync();
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision() {
        // ball walls
        if (ball.y <= 0) {
            ball.setVerticalSpeed(-ball.verticalSpeed);
        }
        if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball.setVerticalSpeed(-ball.verticalSpeed);
        }


        // ball paddles
        if (ball.intersects(paddle1)) {
            ball.horizontalSpeed = Math.abs(ball.horizontalSpeed);
            ball.horizontalSpeed++;
            if (ball.verticalSpeed > 0) ball.verticalSpeed++;
            else ball.verticalSpeed--;
            ball.setHorizontalSpeed(ball.horizontalSpeed);
            ball.setVerticalSpeed(ball.verticalSpeed);
        }
        if (ball.intersects(paddle2)) {
            ball.horizontalSpeed = Math.abs(ball.horizontalSpeed);
            ball.horizontalSpeed++;
            if (ball.verticalSpeed > 0) ball.verticalSpeed++;
            else ball.verticalSpeed--;
            ball.setHorizontalSpeed(-ball.horizontalSpeed);
            ball.setVerticalSpeed(ball.verticalSpeed);
        }
        // paddles walls
        if (paddle1.y <= 0) paddle1.y = 0;
        if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) paddle1.y =
            GAME_HEIGHT - PADDLE_HEIGHT;
        if (paddle2.y <= 0) paddle2.y = 0;
        if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT)) paddle2.y =
            GAME_HEIGHT - PADDLE_HEIGHT;
        // point given
        if (ball.x <= 0) {
            score.player2Score++;
            newPaddles();
            newBall();
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            score.player1Score++;
            newPaddles();
            newBall();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                if (isGameRunning) {  // Only move and check collision if the game is running
                    move();
                    checkCollision();
                }
                repaint();
                delta--;
            }
        }
    }

    public void requestFocus() {
        super.requestFocus();
    }

    public class AL extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
