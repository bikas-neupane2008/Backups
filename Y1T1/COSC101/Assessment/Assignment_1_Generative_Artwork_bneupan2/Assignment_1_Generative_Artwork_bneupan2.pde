/************************************************************************************************* //<>//
 * Assignment 1 - A simple generative artwork
 * Author - @bneupan2
 * purpose of the program is to fulfill all the aspects of assignment 1.
 *
 * This program uses the draw loop to incrementally draw a simple "Wave Clock" inspired graphic.
 *
 * This pattern is created by repeated drawing lines from the centre point outwards at different
 * angles (incremented at 30.7 degrees in this case) and 
 * 
 * also the center point can be changed when the mouse is clicked. 
 * 
 * The colour for each line is updated to Red, Green and Blue and also their
 * opacity is slightly different shade in each iteration and the length of each line is altered
 * using a randomised value, giving the unique shape. As the program progresses, the start length
 * of each line (before adding random noise) is reduced by 0.05 units in each iteration, producing
 * a layered effect.
 **************************************************************************************************/
// Global varibles used in the draw loop
float radius;                  //Length of the current line
int centX;                     //Center X Coordinate
int centY;                     //Center Y Coordinate
float x;                       //Current line end x coodinate
float y;                       //Current line end y coodinate
float radius_Noise;             //The random noise value added to the line
float ang_value;               //Current angle of the line to draw
int color_red;                 //Red color value
int color_green;               //Green color value
int color_blue;                //Blue colour value
int color_dir_red;             //Direction of movement over the red colour gradient (getting lighter or darker)
int color_dir_green;           //Direction of movement over the green colour gradient (getting lighter or darker)
int color_dir_blue;            //Direction of movement over the blue colour gradient (getting lighter or darker)
int stroke_transparency;       //The opacity of the stroke
int dir_stroke_transparency;   //Direction of opacity
/*************************************************************************************************
 * setup() - Initialise all required values for our program.
 *************************************************************************************************/
void setup() {
  size(800, 800);      //The screen size is made small because of mousePressed()
  background(255);
  radius = 350;
  centX = width/2;
  centY = height/2;
  radius_Noise = random(20);
  ang_value = 0;
  color_red = int(random(0, 255)) ;
  color_green = int(random(0, 255));
  color_blue = int(random(0, 255));
  stroke_transparency = int(random(0, 255));
  color_dir_red = 1;
  color_dir_green = 1;
  color_dir_blue = 1;
  dir_stroke_transparency = 1;
}
/*************************************************************************************************
 * draw() - Iteratively render the artwork
 *************************************************************************************************/
void draw() {
  smooth();
  ang_value -= 30.6; // this is used to make the graphic rotate anti - clockwise
  radius -= 0.05;
  radius_Noise += 0.05;
  //This code controls the colour gradient. We sweep through the colour range back and forth
  if (color_red > 255) {
    color_dir_red = -1;
  } else if (color_red < 1) {
    color_dir_red = 1;
  }
  if (color_green > 255) {
    color_dir_green = -1;
  } else if (color_green < 1) {
    color_dir_green = 1;
  }
  if (color_blue > 255) {
    color_dir_blue = -1;
  } else if (color_blue < 1) {
    color_dir_blue = 1;
  }
  if (stroke_transparency > 255) {
    dir_stroke_transparency = -1;
  } else if (stroke_transparency < 1) {
    dir_stroke_transparency = 1;
  }
  color_red += color_dir_red;
  color_green += color_dir_green;
  color_blue += color_dir_blue;
  stroke_transparency += dir_stroke_transparency;
  float thisRadius = radius + (noise(radius_Noise) * 150) - 100;
  float rad = radians(ang_value);
  x = centX + (thisRadius * cos(rad));
  y = centY + (thisRadius * sin(rad));
  strokeWeight(5);
  stroke(color_red, color_green, color_blue, stroke_transparency); // This works on RGB colour gradient and its opacity.
  if (mousePressed == true) {
    centX = mouseX; // the center of x axis for the drawing changes when mouse is clicked
    centY = mouseY; // the center of y axis for the drawing changes when mouse is clicked
    x = centX + (thisRadius * cos(rad));
    y = centY + (thisRadius * sin(rad));
}
  line(centX, centY, x, y);
  //println("Red = "+color_red);   //for checking the value of red
  //println("Green = "+color_green);   //for checking the value of green
  //println("Blue = "+color_blue);   //for checking the value of blue
  //println("Alpha = "+stroke_transparency);   //for checking the value of aplha in stroke

  //Uncomment this line if you wish to create a series of images that can be used to
  //create a movie using the movie-maker functionality.

  //saveFrame("frames/"+nf(frameCount,4)+".tif");   // this line is kept commented because it used my storage alot
}
