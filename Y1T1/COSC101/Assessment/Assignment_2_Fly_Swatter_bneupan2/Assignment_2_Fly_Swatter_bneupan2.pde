/*
 ************************************************************************************************
 * Assessment 2 - A simple Fly Swatter! game
 * Author - @bneupan2
 * purpose of the program is to develop a fly swatter game and fulfill all the aspects of assessment 2.
 *******************
 * This is a simple game in which the user can click their mouse to swat the fly that appears on the screen.
 * The more the user goes on swatting the fly, the score goes on increasing.
 *******************
 * This program imports processing.sound.* from the processing library to use sound in the program.
 * So, to run this program at first, please import processing.sound.* library.
 * This program uses the setup and draw loop.
 * Inside setup, the screen is created, the required images and sounds are loaded to the memory.
 * There is also a background sound looping which seems like the humming of the fly.
 * Inside draw loop it calls 
 * populate method to draw fly and flybye (dead fly) in the screen whose width and height are customised
 * and draws score as game runs and the swatter width and height is also customised.
 * When mouse is pressed, the swatted image is displayed in place of swatter 
 * and swatted width and height is also customised
 * and also swat sound is played
 * Then collisionDetect method is called to check the collision is in the fly or not.
 * The collision with fly is deemed if the fly is almost completely inside the swatter upper square area 
 * If the collision is in the fly, the wow sound is played and the fly image changes to flybye 
 * and new fly is displayed at random location
 * and also the score increases by 1
 * If the collision is not in the fly then oops sound is played.
 **************************************************************************************************
 */

import processing.sound.*;
SoundFile bgSound, swatSound, hitSound, missSound;
PImage fly, flybye, swatter, swatted;
int[] fX, fY; // fly locations array
int[] swat; // fly swatted binary boolean array, 1 = swatted, 0 = not swatted
int score = 0; // increments when swatted.

void setup() {
  size(800, 800);
  fX = new int[0];
  fY = new int[0];
  swat = new int[0];

  // load images
  fly = loadImage("fly.png");
  flybye = loadImage("flybye.png");
  swatter = loadImage("swatter.png");
  swatted = loadImage("swatted.png");

  // loading sounds
  missSound = new SoundFile(this, "oops.wav");
  hitSound = new SoundFile(this, "wow.wav");
  swatSound = new SoundFile(this, "swat.wav");
  bgSound = new SoundFile(this, "bg.wav");
  bgSound.loop();
   
  // first fly - random location.
  // In both fX and fY, random is customised to keep the fly completely inside screen
  fX = append(fX, int(random(30, width - 80)));
  fY = append(fY, int(random(30, height - 80)));
  swat = append(swat, 0); // used as a boolean and matches to each individual fly, 0 = fly not swatted, 1 = swatted.
}

void populate() { // draw the flies in memory to the screen.
  for (int i = 0; i < fX.length; i++) {
    if (swat[i] == 1) { // if swatted
      // resize the fly image and place based on fx/fy array values
      // In both fly and flybye, image is customised in height = 50 and width = 50
      image(flybye, fX[i], fY[i], 50, 50);
      } else { // not swatted
        image(fly, fX[i], fY[i], 50, 50);
    }
  }
}

void collisionDetect() { //collision detection - detect collision between swatter and fly
  boolean isFlySwatted = false; // flag to track if the fly is swatted
  for (int i = fX.length - 1; i < fX.length; i++) { // bounding box detection
    int inX = fX[i] + 50;
    int inY = fY[i] + 50;
    if (mouseX >= fX[i] && mouseX <= inX && mouseY >= fY[i] && mouseY <= inY) { // condition should look at location of mouse and individual coordinates in fX and fY
      // play swat sound effect
      hitSound.play();
      swat[i] = 1; // swatted
      // In both fX and fY, random is customised to keep the fly completely inside screen
      fX = append(fX, int(random(30, width - 80))); //new fly placed in random x co-ordinate location when old fly dies.
      fY = append(fY, int(random(30, height - 80))); //new fly placed in random y co-ordinate location when old fly dies.
      swat = append(swat, 0); // new fly not swatted
      score++; //increment score
      isFlySwatted = true; // set the flag to indicate fly is swatted
    }
    if (!isFlySwatted) { // if the fly is not swatted
      // play missed sound
      missSound.play();
    }
  }
}

void draw() {
  background(255);
  populate(); // draw flys to screen.
  fill(0);
  // set a text size and location for the score.
  textSize(20);
  text("Score: " + score, 700, 30);
  // swatter image is customised so that the center is deemed to be the center of the hitting part
  // the values are taken after calculations
  // and also height = 70 and width = 200
  image(swatter, mouseX - 35, mouseY - 30, 70, 200);
}

void mousePressed() {
  // swatted image is customised as per the swatter
  image(swatted, mouseX - 35, mouseY - 30, 70, 200);
  // play swat sound effect
  swatSound.play();
  collisionDetect();
}
