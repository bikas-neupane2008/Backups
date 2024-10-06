package minigames.client.tictactoe;

import io.vertx.core.json.JsonObject;
import minigames.client.GameClient;
import minigames.client.MinigameNetworkClient;
import minigames.rendering.GameMetadata;

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
import minigames.rendering.GameMetadata;
import minigames.commands.CommandPackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TicTacToeClient implements GameClient {
    private MinigameNetworkClient mnClient;
    private GameMetadata gm;
    private String player;
    private JPanel mainPanel, gridPanel, scorePanel, footerPanel;
    private JButton[][] buttons;
    private char currentPlayer = 'X';
    private char[][] board;
    private JLabel footerLabel, playerXScoreLabel, playerOScoreLabel;
    private int playerXScore = 0, playerOScore = 0;

    public static void main(String[] args) {
        TicTacToeClient ticTacToeClient = new TicTacToeClient();
    }

    public TicTacToeClient() {
        mainPanel = new JPanel(new BorderLayout());

        setupMenu();
        JPanel bodyPanel = createGridPanel();
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        footerPanel = new JPanel();

        footerLabel = new JLabel("Player " + currentPlayer + "'s turn", SwingConstants.CENTER);
        footerLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        footerPanel.add(footerLabel);

        mainPanel.add(createScorePanel(), BorderLayout.NORTH);
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(ticTacToeClient.setupMenu());
        frame.setContentPane(ticTacToeClient.mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private JMenuBar setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Options");

        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(this::confirmAndResetGame);

        JMenuItem exitGameItem = new JMenuItem("Exit Game");
        exitGameItem.addActionListener(e -> confirmAndExitGame());

        gameMenu.add(newGameItem);
        gameMenu.add(exitGameItem);
        menuBar.add(gameMenu);
        return menuBar;
    }

    private JPanel createGridPanel() {
        gridPanel = new JPanel(new GridLayout(3, 3));
        gridPanel.setPreferredSize(new Dimension(500, 500));
        initializeGame();
        return gridPanel;
    }

    private void initializeGame() {
        board = new char[3][3];
        // Initializing the board with space characters
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

    private void handleButtonClick(ActionEvent e, int i, int j) {
        JButton clickedButton = (JButton) e.getSource();
        clickedButton.setText(String.valueOf(currentPlayer));
        clickedButton.setFont(new Font("Arial", Font.BOLD, 40));
        clickedButton.setEnabled(false);
        board[i][j] = currentPlayer;

        Point[] winningPoints = hasContestantWon(i, j);

        if (winningPoints != null) {
            updateScoreAndReset("Player " + currentPlayer + " wins!", winningPoints);
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(mainPanel, "It's a tie!");
            resetGame();
        } else {
            switchPlayer();
        }
    }

    private void drawWinningLine(Point[] winningPoints) {
        Timer timer = new Timer(500, null); // Timer to toggle the visibility of text in buttons every 500 milliseconds
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

        // Stop the blinking after a few seconds (adjust the delay as needed)
        Timer stopBlinkTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop(); // Stop the blinking timer
                for (Point p : winningPoints) {
                    buttons[p.x][p.y].setText(String.valueOf(currentPlayer)); // Set the text back to the winning symbol
                }
            }
        });
        stopBlinkTimer.setRepeats(false); // Only execute once
        stopBlinkTimer.start();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        footerLabel.setText("Player " + currentPlayer + "'s turn");
    }

    private void confirmAndResetGame(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to start a new game?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION)
            resetGame();
    }

    private void confirmAndExitGame() {
        int result = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to exit?", "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION)
            System.exit(0);
    }

    private JPanel createScorePanel() {
        scorePanel = new JPanel(new GridLayout(1, 2));
        playerXScoreLabel = new JLabel("Player X score: " + playerXScore);
        playerOScoreLabel = new JLabel("Player O score: " + playerOScore, SwingConstants.RIGHT);
        playerXScoreLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        playerOScoreLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        scorePanel.add(playerXScoreLabel);
        scorePanel.add(playerOScoreLabel);
        return scorePanel;
    }

    private Point[] hasContestantWon(int i, int j) {
        char symbol = board[i][j];
        boolean win = true;
        // Check Row
        for (int col = 0; col < 3; col++) {
            if (board[i][col] != symbol) {
                win = false;
                break;
            }
        }
        if (win)
            return new Point[] { new Point(i, 0), new Point(i, 1), new Point(i, 2) };
        // Check Column
        win = true;
        for (int row = 0; row < 3; row++) {
            if (board[row][j] != symbol) {
                win = false;
                break;
            }
        }
        if (win)
            return new Point[] { new Point(0, j), new Point(1, j), new Point(2, j) };
        // Check Left Diagonal
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
        // Check Right Diagonal
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

    private void updateScoreAndReset(String message, Point[] winningPoints) {
        Timer timer = new Timer(500, null); // Timer to toggle the visibility of text in buttons every 500 milliseconds
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

        drawWinningLine(winningPoints); // Draw line before showing dialog
        JOptionPane.showMessageDialog(mainPanel, message); // This will block until user closes the dialog
        timer.stop(); // Stop the blinking when user closes the dialog

        // Set the text back to the winning symbol after stopping the timer
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

    private void resetGame() {
        gridPanel.removeAll();
        initializeGame();
        gridPanel.revalidate();
        gridPanel.repaint();
        currentPlayer = 'X';
        footerLabel.setText("Player " + currentPlayer + "'s turn");
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ')
                    return false;
            }
        }
        return true;
    }

    @Override
    public void load(MinigameNetworkClient mnClient, GameMetadata game, String player) {
        this.mnClient = mnClient;
        this.gm = game;
        this.player = player;
        mnClient.getMainWindow().clearAll();
        mnClient.getMainWindow().addCenter(gridPanel);
        mnClient.getMainWindow().addNorth(scorePanel);
        mnClient.getMainWindow().addSouth(footerPanel);
        mnClient.getMainWindow().pack();
    }

    @Override
    public void execute(GameMetadata game, JsonObject command) {
        this.gm = game;
    }

    @Override
    public void closeGame() {
        // Clean up resources if needed
    }

}
