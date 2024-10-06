/*
* This Missile Command game is developed by we two students from UNE Sydney college.
* Author @ bneupan2 (Bikash Neupane) and sdhital (Sabin Dhital).
* In this assignment, we two worked in a team to build the game. We divided the task with each other.
* We developed the code by starting from the simple design and implementing it and again adding new design and again implementing and so on.
* The sound and animation files we used in the code are copyright free.
* As we both are from same home country i.e. Nepal, we thought of using the game theme dedicated to Nepal which indicates our country pride.
* We have used the sound in the start screen and game over screen which was the previous national anthem of Nepal when there was monarchy system in Nepal.
* The other sound includes firing sound from the battery, destroying sound of the missile and destroying sound of the city.
* The waving animation is of the Nepal national flag in the start screen and game over screen.
* The background image used in the start screen and game over screen dedicates the message to the world that the "The Light of Asia" Gautam Buddha was born in Nepal.
* We tried to use many other data and features in the game but due to the approaching deadline we used only these ones.
* The above sound and images and design concepts theme was provided mainly by Sabin.
* We jointly designed the city, missiles, battery and counter missile and simple GUI to combine these all features.
* Bikash did the part of combining the data from Sabin and implementing them in the previously designed GUI
* and also designed the collision detection and other features of the game.
* The other features includes setting timer in the game, increasing the game difficulty as the level rises, customising the cursor and
* making the missile and counter missile bounce back to the screen when it strikes the vertical edges of the screen.
* The game is in default in the screen size of 1200 X 900.
* Although the task was divided to both of us, we worked in the team by helping each others as well.
* The codes and logics used in the game is also dependent with the tutorials and lectures tasks from the unit and other related units.
* Some of the logics is also used from internet as well by converting them to our own design and concepts.
* At last, with our hard team work and communication we finally suceed to build this simple missile game.
*/

// Import required libraries
import processing.sound.*;
import gifAnimation.*;

// Declare global variables
Gif nepalFlag, gameScreen;
PImage buddha, background;
SoundFile bgMusic, fireSound, missileCollisionSound, cityCollisionSound;
ArrayList<Missile> missiles;
ArrayList<City> cities;
Battery battery;
int score;
int level;
boolean gameEnd;
boolean gameStarted;
int timer;
int maxTime;

// Setup function to initialize the variables and load assets
void setup() {
  // Setup the canvas size
  size(1200, 900);
  
  // Initialize the global variables
  missiles = new ArrayList<Missile>();
  cities = new ArrayList<City>();
  battery = new Battery();
  score = 0;
  level = 1;
  gameEnd = false;
  gameStarted = false;
  timer = 0;
  maxTime = 1800;
  
  // Load images and sound files
  nepalFlag = new Gif(this, "/sources/Nepal_Flag.gif");
  gameScreen = new Gif(this, "/sources/gameScreen.gif");
  buddha = loadImage("/sources/buddha.jpg");
  background = loadImage("/sources/tiger.jpg");
  fireSound = new SoundFile(this, "/sources/fireSound.wav");
  missileCollisionSound = new SoundFile(this, "/sources/missileCollisionSound.wav");
  cityCollisionSound = new SoundFile(this, "/sources/cityCollisionSound.wav");
  bgMusic = new SoundFile(this, "/sources/nepal-old-national-anthem.mp3");
  
  // Add cities to the array list
  for (int i = 0; i < 6; i++) {
    cities.add(new City(i * width / 6 + width / 12, height - 10));
  }
  
  gameStarted = false;
}

// A function to start the game, re-initializing variables as necessary
void startGame() {
  // Game related variables
  gameEnd = false;
  gameStarted = true;
  timer = 0;
  score = 0;
  level = 1;
  
  // Clear previous game data if any
  missiles.clear();
  cities.clear();
  battery.counterMissiles.clear();
  
  // Add new cities for the new game
  for (int i = 0; i < 6; i++) {
    cities.add(new City(i * width / 6 + width / 12, height - 10));
  }
  
  // Add new missiles for the new game
  for (int i = 0; i < level * 10; i++) {
    missiles.add(new Missile(random(width), 0, random(-1, 1), random(1, 3)));
  }
}

// The draw function runs in a loop and updates the game screen
void draw() {
  // Clear the screen
  background(0);
  
  // If the game hasn't started, play background music and display the start screen
  if (!gameStarted) {
    playBackgroundMusic();
    displayStartScreen();
    return;
  }
  
  // If the game has ended, play background music and display the game over screen
  if (gameEnd) {
    playBackgroundMusic();
    displayGameOverScreen();
    return;
  }

  // Otherwise, display the game
  image(background, 0, 0, width, height);
  
  // Increment the timer
  timer++;
  
  // Check for game end conditions
  if (timer >= maxTime) {
    gameEnd = true;
    return;
  }
  if (cities.isEmpty()) {
    gameEnd = true;
    return;
  }
  
  // If all missiles are destroyed, increment the level and generate new missiles
  boolean currentWaveDestroyed = missiles.isEmpty();
  if (currentWaveDestroyed) {
    level++;
    timer = 0;
    for (int i = 0; i < level * 10; i++) {
      missiles.add(new Missile(random(width), 0, random(-1, 1), random(1, 3)));
    }
  }
  
  // Update and draw each city, removing any destroyed cities
  for (int i = cities.size() - 1; i >= 0; i--) {
    City city = cities.get(i);
    city.update();
    city.draw();
    if (city.destroyed) {
      cities.remove(i);
    }
  }
  
  // Update and draw each missile, removing any that have moved off-screen or hit a city
  for (int i = missiles.size() - 1; i >= 0; i--) {
    Missile missile = missiles.get(i);
    missile.update();
    missile.draw();
    if (missile.y > height || missile.y < 0) {
      missiles.remove(i);
    } else {
      for (City city : cities) {
        if (city.collidesWithMissile(missile)) {
          cities.remove(city);
          cityCollisionSound.play();
          break;
        }
      }
    }
  }
  
  // Update and draw the battery
  battery.update();
  battery.draw();
  
  // Display score, level, and remaining time
  textSize(20);
  fill(255);
  textAlign(CENTER, CENTER);
  text("Score: " + score, width/2-500, 30);
  textSize(20);
  fill(255);
  text("Level: " + level, width/2, 30);
  int remainingTime = maxTime - timer;
  int seconds = remainingTime / 60;
  int milliseconds = remainingTime % 60;
  String timeString = nf(seconds, 2) + ":" + nf(milliseconds, 2);
  text("Time: " + timeString, width/2+500, 30);
  
  // Draw the custom cursor
  drawCustomCursor();
}

// Draw a custom cursor
void drawCustomCursor() {
  // Hide the default cursor
  noCursor();
  
  // Draw a white dot at the mouse position
  fill(255);
  stroke(255);
  strokeWeight(1);
  ellipseMode(CENTER);
  point(mouseX, mouseY);
  
  // Draw a white circle around the mouse position
  noFill();
  ellipse(mouseX, mouseY, 20, 20);
}

// Fires a missile when the mouse is clicked
void mousePressed() {
  float targetX = mouseX;
  float targetY = mouseY;
  battery.fire(targetX, targetY);
}

// Handles keyboard input
void keyPressed() {
  // Start a new game or exit the game when ENTER or ESC are pressed
  if (keyCode == ENTER) {
    if (!gameStarted || gameEnd) {
      bgMusic.stop();
      startGame();
    }
  }
  if (keyCode == ESC) {
    if (!gameStarted || gameEnd) {
      exit();
    }
  }
}

// Play the background music, looping if necessary
void playBackgroundMusic() {
  if (!bgMusic.isPlaying()) {
    bgMusic.loop();
  }
}

// Display the start screen
void displayStartScreen() {
  // Hide the cursor
  noCursor();
  
  // Display the background images
  image(buddha, 0, 0, width, height);
  image(nepalFlag,  width-255, 50, 250, 380);
  nepalFlag.loop();
  
  // Draw a box and title text
  fill(255);
  rect(width/2 - 200, height/2+180, 400, 120);
  textSize(32);
  fill(0);
  textAlign(CENTER, CENTER);
  text("Missile Command", width/2, height/2+200);
  stroke(0);
  
  // Draw instructions for the player
  line(width/2-120, height/2+220, width/2+120, height/2+220);
  textSize(24);
  text("Press ENTER to start new game", width/2, height/2+240);
  text("Press ESC to exit the game", width/2, height/2+280);
}

// Display the game over screen
void displayGameOverScreen() {
  // Hide the cursor
  noCursor();
  
  // Display the background images
  image(buddha, 0, 0, width, height);
  image(nepalFlag, width-255, 50, 250, 380);
  nepalFlag.loop();
  
  // Draw a box and game over text
  fill(255);
  rect(width/2, height/2+220, 400, 160);
  textSize(32);
  fill(0);
  textAlign(CENTER, CENTER);
  text("Game Over", width / 2, height / 2 + 160);
  stroke(0);
  
  // Draw the game title and instructions for the player
  line(width/2-80, height/2+180, width/2+80, height/2+180);
  text("Missile Command", width / 2, height / 2 +200);
  stroke(0);
  line(width/2-120, height/2+220, width/2+120, height/2+220);
  textSize(24);
  text("Press ENTER to re-start the game", width / 2, height / 2 + 240);
  text("Press ESC to exit the game", width / 2, height / 2 + 280);
}
