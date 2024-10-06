package minigames.client.ponggame;

import java.util.Collections;

import io.vertx.core.json.JsonObject;
import minigames.client.GameClient;
import minigames.client.MinigameNetworkClient;
import minigames.rendering.GameMetadata;
import minigames.commands.CommandPackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.*;


public class PongUi implements GameClient {
    private static final Logger logger = LogManager.getLogger(PongUi.class);

    MinigameNetworkClient mnClient;

    GameMetadata gm;

    /** Your name */    
    String player;

    /** A text area for showing room descriptions, etc */
    JTextArea textArea; 

     //Header Section
     JPanel headerPanel;
     JPanel mainPanel;
     JPanel newPanel;
     JLabel headerText;
     JLabel footerText;
 
     //Menu Section
     JPanel mainMenuPanel; 
     JPanel buttonsPanel;  
     JButton startGameButton; //Start Game Button
     JButton highScoreButton; //High Score Button
     JButton mainMenuButton;  //Main Menu Button
     JButton exitButton;      //Exit Button
     JPanel instructionsPanel; // Panel for instructions
     JButton howToPlayButton;
     JButton backButton;
    

    /** Direction commands */
    JButton north, south, east, west;

    JTextField userCommand;
    JButton send;
    
    JPanel commandPanel;
    JPanel footerPanel;
    JPanel messagePanel;
    JLabel messageLabel;
    JButton yesButton;
    JButton noButton;
    int time = 120;

    boolean exitConfirmed = false; // Flag to track exit confirmation

  public PongUi() {
    headerPanel = new JPanel();
    newPanel = new JPanel();

    mainPanel = new JPanel();
    mainPanel.setPreferredSize(new Dimension(800, 758));
    mainPanel.setBackground(Color.BLACK);
    mainPanel.setLayout(new GridBagLayout());

        // Create the footer panel and developer credit label
    footerPanel = new JPanel();
    footerPanel.setBackground(Color.BLACK);
    footerPanel.setLayout(new BorderLayout());

    footerText = new JLabel("Developed by Fab4 Team");
    footerText.setForeground(Color.RED);
    footerText.setFont(new Font("Monospaced", Font.ITALIC, 22));
    footerPanel.add(footerText);

    headerText = new JLabel("Pong Game!");
    headerText.setForeground(Color.RED);
    headerText.setFont(new Font("Monospaced", Font.BOLD, 32));
    headerPanel.add(headerText);

    JButton newGame = new JButton("Start Game");
    newGame.addActionListener((evt) -> sendCommand("START_GAME"));
    
    JButton backButton = new JButton("Back");
    backButton.addActionListener(e -> mnClient.runMainMenuSequence());

    JButton howToPlayButton = new JButton("How to Play");
    howToPlayButton.addActionListener((e) -> toggleInstructions());

    JButton exitButton = new JButton("Quit");
    exitButton.addActionListener((evt) ->sendCommand("QUIT"));

    JButton highScore = new JButton("High Score");

    JButton menuButton = new JButton("Back menu");
    menuButton.addActionListener(e -> hideInstructions());

    
    // Initialize the instructions panel
    instructionsPanel = new JPanel();
    instructionsPanel.setPreferredSize(new Dimension(400, 600));
    instructionsPanel.setBackground(Color.BLACK);
    instructionsPanel.setLayout(new BorderLayout());
    instructionsPanel.setVisible(false); // Hide the instructions panel

   // Create a JTextArea for instructions
   JTextArea instructionsText = new JTextArea();
   instructionsText.setForeground(Color.GREEN);
   instructionsText.setBackground(Color.BLACK);
   instructionsText.setFont(new Font("Monospaced", Font.PLAIN, 20));
   instructionsText.setEditable(false);
   instructionsText.setWrapStyleWord(true);
   instructionsText.setLineWrap(true);
   instructionsText.setText("Instructions on how to play the game:\n" +
       "1. Player 1 use the UP and DOWN arrow to move the paddle.\n" +
       "2. Player 2 use the W and S button to move the paddle.\n" +
       "3. Try to bounce the ball off your paddle and prevent it from reaching your opponent's side.\n" +
       "4. Score points by getting the ball past your opponent's paddle.\n" +
       "5. The game continues until one player reaches a certain score.");
   instructionsPanel.add(new JScrollPane(instructionsText), BorderLayout.CENTER);
   instructionsPanel.add(menuButton, BorderLayout.SOUTH);



    GridBagConstraints gbcButtons = new GridBagConstraints();
    gbcButtons.gridx = 0;
    gbcButtons.gridy = 0;
    gbcButtons.anchor = GridBagConstraints.CENTER;
    gbcButtons.insets = new Insets(10, 0, 10, 0);

    gbcButtons.gridy = 1;
    mainPanel.add(newGame, gbcButtons);

    gbcButtons.gridy = 2;
    mainPanel.add(backButton, gbcButtons);

    gbcButtons.gridy = 3;
    mainPanel.add(exitButton, gbcButtons);

    gbcButtons.gridy = 4;
    mainPanel.add(highScore, gbcButtons);

    gbcButtons.gridy = 5;
    mainPanel.add(howToPlayButton, gbcButtons);


    for (Component c : new Component[] { backButton, exitButton }) {
        gbcButtons.gridy++;
        mainPanel.add(c, gbcButtons);
    }

    instructionsPanel.add(new JScrollPane(instructionsText), BorderLayout.CENTER);
    mainPanel.add(instructionsPanel); 

      // Create the custom message panel
      messagePanel = new JPanel();
      messagePanel.setPreferredSize(new Dimension(600, 200));
      messagePanel.setBackground(Color.BLACK);
      messagePanel.setLayout(new BorderLayout());
      messagePanel.setVisible(false);

      messageLabel = new JLabel();
      messageLabel.setForeground(Color.GREEN);
      messageLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
      messageLabel.setHorizontalAlignment(JLabel.CENTER);

      yesButton = new JButton("Yes");
      yesButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              exitConfirmed = true; // Set exit confirmation flag
              System.exit(0);
          }
      });

      noButton = new JButton("No");
      noButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              hideMessage(); // Hide the message panel
          }
      });

      
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(yesButton);
      buttonPanel.add(noButton);

      messagePanel.add(messageLabel, BorderLayout.CENTER);
      messagePanel.add(buttonPanel, BorderLayout.SOUTH);
      mainPanel.add(messagePanel, gbcButtons);


}

 // Method to display a custom message with "Yes" and "No" options
 private void showMessage(String message) {
    exitConfirmed = false; // Reset exit confirmation flag
    messageLabel.setText(message);
    mainPanel.setVisible(false);
    messagePanel.setVisible(true);
}

// Method to hide the message panel
private void hideMessage() {
    messagePanel.setVisible(false);
    mainPanel.setVisible(true);
}

private void hideInstructions() {
    instructionsPanel.setVisible(false);
    mainPanel.setVisible(true);
}

private void toggleInstructions() {
    instructionsPanel.setVisible(true);
    mainPanel.setVisible(false);
}

    
    public void sendCommand(String command) {
        JsonObject json = new JsonObject().put("command", command);

        mnClient.send(new CommandPackage(gm.gameServer(), gm.name(), player, Collections.singletonList(json)));
    }
 
    @Override
    public void load(MinigameNetworkClient mnClient, GameMetadata game, String player) {
        this.mnClient = mnClient;
        this.gm = game;
        this.player = player;

        mnClient.getMainWindow().addNorth(headerPanel); 
        mnClient.getMainWindow().addCenter(mainPanel);
        mnClient.getMainWindow().addCenter(instructionsPanel);
        mnClient.getMainWindow().addCenter(messagePanel);
        mnClient.getMainWindow().addSouth(footerPanel); 

        mnClient.getMainWindow().pack();
      
    }

    @Override
    public void execute(GameMetadata game, JsonObject command) {
        this.gm = game;
        logger.info("my command: {}", command.getString("command"));
  
        switch (command.getString("command")) {
          
            case "start_game" ->{
                startGame();
            }
            case "quit" -> {closeGame();
                System.out.println("Exitingggggg");
            }
        }
    }

    @Override
    public void closeGame() {
       // Display a custom confirmation message
       showMessage("Are you sure you want to exit the game?");
    }

    public void startGame() {
        GamePanel newGame = new GamePanel();
        newGame.setVisible(true);
        newPanel.add(newGame);
        newGame.setOpaque(false);
        mnClient.getMainWindow().clearAll();
        mnClient.getMainWindow().addCenter(newGame);
        mnClient.getMainWindow().pack();
        newGame.requestFocus();
    }
}
