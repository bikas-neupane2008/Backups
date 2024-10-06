## A Git, GitLab, and JUnit exercise

This assignment is intended to give you a relatively small exercise to give you a little familiarisation working with Git, Gradle,
JUnit, and some more recent Java language features on a very small codebase - 
before you and your group do something much more exciting on the much larger class-wide project.

The example code you have been given is a simple Java "dots and boxes" game. It has a couple of known bugs in it
(already commented for you - the task is not really in finding the bugs). Please note: your task **is not simply to fix the bugs**. 
Your task is a series of steps that show you can work with git, gitlab, gradle, and junit.

You will notice that this is a git repository, but it is provided to you just as a zip that you download.
If you run `git status`, you should see that you are on branch `main`.

Dots And Boxes is normally a two-player pencil-and-paper game. See https://en.wikipedia.org/wiki/Dots_and_Boxes  
In our version, a player "draws" a line by clicking on it. If it completes a square, they automatically claim the
square and get another turn. There are two known bugs in our code-base:

1. The code for checking if a square is complete is wrong. It just returns `true` at the moment, whereas it should
   test if the lines surrounding the square have been "drawn".
2. The grid doesn't complain if you "draw" a line that has already been drawn. It should throw an `IllegalStateException`.

Remember, your task *isn't* just to fix the bugs.

Your tasks:

1. Create a repository on gitlab.une.edu.au and push the main branch of this repository to it **entirely unmodified**. 
   **You will need to set the visibility on your repository so that the marker can read from it**. 
   Repositories we cannot access cannot be marked (and will receive 0 until you can fix it)
   
2. Set your name and email on the repository for your future commits, using `git config`

3. Update the "name" label in the application to show **your name**.
   Commit this change and push it to your **main** branch.

3. Create an Issue in your GitLab project for fixing the errors in the assignment.
   
4. Create and check out a branch for your bugfix. 
   This should follow the convention of having the issue number, followed by a hyphenated description of the issue.
   e.g. `1-fix-assignment-errors`.  
   Push this branch to your remote repository. (From here on, I'll refer to this as your "issue branch")

5. Create **unit tests** that will detect the errors in the code. Particularly:
   * That the algorithm for testing whether a box is complete is wrong.
   * That the DotsAndBoxesGrid currently permits you to "draw" a line that has already been drawn, whereas it should throw an exception.
    
6. Commit these unit tests with an appropriately descriptive commit message.  
   **Do not fix the bugs in this commit. The unit tests should *fail*.**    
   Push this commit to your remote repository (on your issue branch)

7. **Tag** this commit, with the label `testsfail`. Push this tag to your remote repository.

8. Fix the bugs in the code. Run your unit tests again. The tests should now pass.

9. Commit your bugfix. Push it to your issue branch.

10. Check out `main`. Merge your issue branch to main. The tests should still pass. Push this to your gitlab repository.

11. Close your issue with a reference to the commit hash in which you fixed the bugs.

12. Paste the link to your gitlab repository into the assignment and submit.

Marking. 

1. You have submitted a link to your repository on gitlab.une.edu.au and it is visible to the marker (1 point)
2. The first few commits on the main branch on the repository contains the starter code, showing me as author. 
   The last of my commits has the *same commit hash* as in the original code I gave you.  
   (i.e. you successfully pushed the original code I gave you to your GitLab repository). (1 point)
3. The next commit on the main branch adds your name to the label and shows you as author  
   (i.e. you have successfully configured your name & email and you have successfully created and pushed a commit) (1 point)
4. You have at least one issue on your repository for the bugfixes. It is closed referencing the bugfix commit. (1 point)
5. There is a `testsfail` tag in your repository (1 point)
6. Checking out the `testsfail` tag, and running the `gradlew test`, produces two failed tests (one for each bug). (2 points)
7. Checking out the main branch, both tests are present and pass (2 points)
8. The most recent commit on the `main` branch is a merge commit, merging the commit in which you set your name on the label
   and the commit in which you fixed the tests. (1 point)


## Features of this code base

As well as being an assignment repository, this codebase is also a sample codebase, showing you a few things:

* It uses a *gradle wrapper* (a script that will download the correct version of gradle for you.) This means you can
  `./gradlew build` and it'll go get gradle before it builds. Normally, that would fetch from a URL on the gradle site,
  but so this works behind UNE's webproxy, the gradle jar is also checked into the repository.
  
* It contains Log4J, which is the logging library (basically, super-powered printlns) that we will use.

* It uses some more recent Java features. e.g. record classes and lambdas.
  These may be familiar to students who've studied Scala, but they are making their way into Java too. 
  For instance, notice that in `DotsAndBoxesUI` we register listeners onto the dots and boxes grid by registering lambda functions.
  
  ```java
   grid.addConsumer((g) -> {
      updateLabel();
      canvas.repaint();
   });
  ```
  
  In Java, functions aren't "first class citizens" in the way they are in Scala, however. Our code here is really 
  creating an *anonymous inner class*.
  
  Instead, Java has the idea of "SAM types" (Single Abstract Method types). `addConsumer` asks for an *object* of type
  `Consumer<DotsAndBoxesGrid>`. But `Consumer<>` has only one abstract method. So, Java gives us the shorthand
  that if we just describe how we want to implement the abstract method (`(g) -> { ...`) then the Java compiler will 
  fill in the rest of the code about creating an anonymous inner class of type `Consumer<>` for us.

  Record classes are similar to Scala's "case classes", e.g. 

  ```java
  record Vertical(int col, int row) {
      Rectangle rect() {
            int x = corner(col);
            int y = corner(row) + dotDiameter + gap;
            return new Rectangle(x, y, dotDiameter, lineLength);
      }

      /** Whether or not this line contains this point */
      boolean contains(int x, int y) {
            return rect().contains(x, y);
      }

      /** Paints this element, based on the passed in grid */
      public void draw(DotsAndBoxesGrid grid, Graphics2D g2d) {
            g2d.setColor(grid.getVertical(col, row) ? Color.DARK_GRAY : Color.LIGHT_GRAY);
            g2d.fill(this.rect());
      }
  }
  ```

  Particularly, `col` and `row` are made to be fields of a `Vertical` instance and the constructor is
  automatically defined. (So is `toString` and `hashCode`, but we're not using those.)

  Record classes aren't as common in Java code-bases, but they're available in Java 17, so students might use 
  them in the group project, so let's give you a little example of them!