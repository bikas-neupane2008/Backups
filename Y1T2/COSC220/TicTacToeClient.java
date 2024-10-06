package minigames.client.tictactoe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import io.vertx.core.json.JsonObject;
import minigames.client.GameClient;
import minigames.client.MinigameNetworkClient;
import minigames.commands.CommandPackage;
import minigames.rendering.GameMetadata;

public class TicTacToeClient implements GameClient {
    private static final Logger logger = LogManager.getLogger(TicTacToeClient.class);
    MinigameNetworkClient mnClient;
    GameMetadata gm;
    String player;
    JPanel mainPanel, gridPanel, scorePanel, footerPanel, menuPanel;
    JButton[][] buttons;
    char currentPlayer = 'X';
    char[][] board;
    JLabel footerLabel, playerXScoreLabel, playerOScoreLabel;
    int playerXScore = 0, playerOScore = 0;

    public TicTacToeClient() {
        mainPanel = new JPanel(new BorderLayout());
        gridPanel = createGridPanel();
        scorePanel = createScorePanel();
        menuPanel = createMenuPanel();
        footerPanel = createFooterPanel();
        mainPanel.add(scorePanel, BorderLayout.NORTH);
        mainPanel.add(gridPanel, BorderLayout.CENTER);
    }

    public JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Options");
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(this::confirmAndResetGame);
        JMenuItem exitGameItem = new JMenuItem("Exit Game");
        exitGameItem.addActionListener(e -> confirmAndExitGame());
        gameMenu.add(newGameItem);
        gameMenu.add(exitGameItem);
        menuBar.add(gameMenu);
        menuPanel.add(menuBar);
        return menuPanel;
    }

    public JPanel createGridPanel() {
        gridPanel = new JPanel(new GridLayout(3, 3));
        gridPanel.setPreferredSize(new Dimension(500, 500));
        initializeGame();
        return gridPanel;
    }

    public JPanel createScorePanel() {
        scorePanel = new JPanel(new GridLayout(1, 2));
        playerXScoreLabel = new JLabel("Player X score: " + playerXScore);
        playerOScoreLabel = new JLabel("Player O score: " + playerOScore, SwingConstants.RIGHT);
        playerXScoreLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        playerOScoreLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        scorePanel.add(playerXScoreLabel);
        scorePanel.add(playerOScoreLabel);
        return scorePanel;
    }

    public JPanel createFooterPanel() {
        footerPanel = new JPanel();
        footerLabel = new JLabel("Player " + currentPlayer + "'s turn", SwingConstants.CENTER);
        footerLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        footerPanel.add(footerLabel);
        return footerPanel;
    }

    public void initializeGame() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++)
            Arrays.fill(board[i], ' ');
        buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i;
                final int col = j;
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 12));
                buttons[row][col].addActionListener(e -> handleButtonClick(e, row, col));
                gridPanel.add(buttons[row][col]);
            }
        }
    }

    public void handleButtonClick(ActionEvent e, int i, int j) {
        JButton clickedButton = (JButton) e.getSource();
        clickedButton.setText(String.valueOf(currentPlayer));
        clickedButton.setFont(new Font("Arial", Font.BOLD, 40));
        clickedButton.setEnabled(false);
        board[i][j] = currentPlayer;

        Point[] winningPoints = hasContestantWon(i, j);

        if (winningPoints != null) {
            updateScoreAndReset("Player " + currentPlayer + " wins!", winningPoints);
            resetGame();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(mainPanel, "It's a tie!");
            resetGame();
        } else {
            switchPlayer();
        }
    }

    public void drawBlink(Point[] winningPoints) {
        Timer timer = new Timer(500, null);
        ActionListener blinkAction = new ActionListener() {
            boolean visible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                for (Point p : winningPoints) {
                    buttons[p.x][p.y].setText(visible ? String.valueOf(currentPlayer) : " ");
                }
                visible = !visible;
                timer.stop();
            }
        };
        timer.addActionListener(blinkAction);
        timer.start();
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        footerLabel.setText("Player " + currentPlayer + "'s turn");
    }

    public void confirmAndResetGame(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to start a new game?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            resetGame();
        }
    }

    public void confirmAndExitGame() {
        int result = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to exit?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION)
            closeGame();
    }

    public Point[] hasContestantWon(int i, int j) {
        char symbol = board[i][j];
        boolean win = true;
        for (int col = 0; col < 3; col++) {
            if (board[i][col] != symbol) {
                win = false;
                break;
            }
        }
        if (win)
            return new Point[] { new Point(i, 0), new Point(i, 1), new Point(i, 2) };
        win = true;
        for (int row = 0; row < 3; row++) {
            if (board[row][j] != symbol) {
                win = false;
                break;
            }
        }
        if (win)
            return new Point[] { new Point(0, j), new Point(1, j), new Point(2, j) };
        if (i == j) {
            win = true;
            for (int idx = 0; idx < 3; idx++) {
                if (board[idx][idx] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win)
                return new Point[] { new Point(0, 0), new Point(1, 1), new Point(2, 2) };
        }
        if (i + j == 2) {
            win = true;
            for (int idx = 0; idx < 3; idx++) {
                if (board[idx][2 - idx] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win)
                return new Point[] { new Point(0, 2), new Point(1, 1), new Point(2, 0) };
        }
        return null;
    }

    public void updateScoreAndReset(String message, Point[] winningPoints) {
        Timer timer = new Timer(500, null);
        ActionListener blinkAction = new ActionListener() {
            boolean visible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                for (Point p : winningPoints) {
                    buttons[p.x][p.y].setText(visible ? String.valueOf(currentPlayer) : " ");
                }
                visible = !visible;
            }
        };
        timer.addActionListener(blinkAction);
        timer.start();

        drawBlink(winningPoints);
        JOptionPane.showMessageDialog(mainPanel, message);
        timer.stop();
        for (Point p : winningPoints) {
            buttons[p.x][p.y].setText(String.valueOf(currentPlayer));
        }

        if (currentPlayer == 'X')
            playerXScore++;
        else
            playerOScore++;
        playerXScoreLabel.setText("Player X score: " + playerXScore);
        playerOScoreLabel.setText("Player O score: " + playerOScore);
        resetGame();
    }

    public void resetGame() {
        gridPanel.removeAll();
        initializeGame();
        gridPanel.revalidate();
        gridPanel.repaint();
        currentPlayer = 'X';
        footerLabel.setText("Player " + currentPlayer + "'s turn");
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ')
                    return false;
            }
        }
        return true;
    }

    public void sendCommand(String command) {
        JsonObject json = new JsonObject().put("command", command);
        // Collections.singletonList() is a quick way of getting a "list of one item"
        mnClient.send(new CommandPackage(gm.gameServer(), gm.name(), player, Collections.singletonList(json)));
    }

    @Override
    public void load(MinigameNetworkClient mnClient, GameMetadata game, String player) {
        this.mnClient = mnClient;
        this.gm = game;
        this.player = player;
        mnClient.getMainWindow().clearAll();
        mnClient.getMainWindow().addCenter(mainPanel);
        mnClient.getMainWindow().addSouth(footerPanel);
        mnClient.getMainWindow().addNorth(menuPanel);
        mnClient.getMainWindow().pack();
    }

    @Override
    public void execute(GameMetadata game, JsonObject command) {
        this.gm = game;
        logger.info("my command: {}", command.getString("command"));

    }

    @Override
    public void closeGame() {
        mnClient.runMainMenuSequence();
    }
}