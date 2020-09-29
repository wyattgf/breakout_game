game
====

This project implements the game of Breakout.

Name: Wyatt Focht (wgf6), Hosam Tageldin (ht120)

### Timeline

Start Date: 9/10/20

Finish Date: 9/28/20

Hours Spent: 60 total

### Resources Used

Used Prof. Duvall's starter code from javaFX lab for Game class
Used Prof. Duvall's extension of ApplicationTest for javaFX testing (DukeApplicationTest)

### Running the Program

Main class: Game

Data files needed: galaxy.jpg, heart.png, highScore.txt, initialFile.txt, laserBeam.png, levelOne.txt, levelThree.txt, levelTwo.txt, plus.png, question.jpg, sun.jpg, wind.png

Key/Mouse inputs: KeyCode.left (move paddle left), KeyCode.right (move paddle right), KeyCode.space (pause game)

Cheat keys:

1. L, add life
2. R, reset position
3. P, add power up
4. W, increase paddle width
5. D, destroy first block (first block is the first listed block in the currenList list)
6. F, freeze game (not including paddle)
7. T, teleport paddle to other side of screen
8. UP, increase paddle speed
9. DOWN, decrease paddle speed
10. 1, 2, 3 (skip to desired corresponding level)
11. 0 (skip to ~secret~ level)

Known Bugs:

Ball rolls along paddle when paddle is simultaneously accelerating as the ball hits a corner of the paddle

Extra credit:

1. Extra power up: Fire Ball Power Up (breaks through any block without bouncing)
2. Added images to several game components
3. Added secret level (see #11 above)
4. Win/Loss screen

### Notes/Assumptions

1. Assumes first paddle in list of paddles is the desired paddle
2. Assumes second ball in list of balls is the desired ball
3. Note: in Game class, cheat keys are controlled by a switch tree.  This is a long method that violates the open/closed
principle of software development, however, given our current level of knowledge, we were unaware as how to refactor 
it in such a way that does abide with the open/closed principle
4. Note: in order to provide tests that holistically evaluated the functionality of our program, there are several
methods and constant variables that were added in several of the classes throughout the entirety of the codebase
 



### Impressions

General Impressions: The completion of this project necessitated not only a sizeable amount of hard work, when it 
came specifically to coding, but additionally required a strategic approach in regard to overall design.
If this game was further refined (past what we have already implemented), we see such further refinement
taking the form of increased specificity of functionality in each level and better visual design (when it comes to 
images utilized for the various game components).  Specifically, in regards to the codebase, we would see
an improvement taking the form of greater use of inhertiance (for paddles and balls) as well as better adhering
to the open/close principle (see #3 in **Notes/Assumptions**).
 

What We Learned For Future Projects: In seeing the project develop from start to finish, we observed the necessity
of having a well thought-out design before beginning to write any code.  This establishes a fundamental plan
that all team members will follow, resulting in everyone being on the same page.  Additionally, this project aided
in showing the importance of polymorphism in writing clean, scalable code.  Though inheritance was heavily utilized
throughout this project, we would have wished to begun implementing it earlier in our design, as it contributed
heavily to easing the difficulty in adding new components.