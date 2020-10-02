# Game Design Final
### Names: Wyatt Focht (wgf6), Hosam Tageldin (ht120)

## Team Roles and Responsibilities

 * Team Member #1: Wyatt Focht
 
 Responsible for the contents of the Level folder (LevelManager,Level and the 
 subclasses extending Level) and the PowerUp folder (PowerUpManager, PowerUp, and
 three of the PowerUp subclasses). Also responsible for the downward movement
 of the blocks found in Level 2. For Level 3, developed the LaserBeam class. 
 Was also responsible for a good amount of the cheat keys and the Paddle class.
 Also developed the BlockReader class which reads in all the data regarding
 the blocks to be formed at a specific level and returns a list of these blocks.

 * Team Member #2: Hosam Tageldin
 
 Responsible for the contents of the Block folder and responsible for the Display on the right side of the screen which includes all the player statuses. 
 Worked a lot with the Ball class, specifically the movement and what happens after a collision.
 Responsible for creating the Player class which kept track and updated the data regarding the player
 including score, lives, and the current level. Created the ending screen regarding whether a player
 lost or won and the final score. Worked on the PowerUpFireBall to test out
 the ease of adding new PowerUps in our current design.

## Design goals

#### What Features are Easy to Add

The design goals that we made were focused on developing a program that allows
for easy extension of functionality in the future. Adding new powerups and 
new levels are a very easy feature to add based on the way the code is currently
implemented. We attempted to make the implementation of new types of Balls and 
Paddles easy to add by allowing the Game class to work with both a list of 
Balls and Paddles. This would allow for an easy implementation of a power up
that creates multiple balls and multiple paddles at the bottom. It is also 
very easy to add new types of blocks for the game. These features are easy
to add because of the program's use of extractions.


## High-level Design

#### Core Classes

The following list of classes contains the name of the most important/core classes that were utilized in this project: Game, BlockReader, ScoreBoard, LevelManager, PowerUpManager, Ball, Paddle, and Player.

Game: This class does the majority of the work when it comes to setting up features of the game, determining whether the game has ended, instigating changes based on user input (cheat keys), as well as initializing many other important variables that are integral to the functionality of the game.  Specifically, such initialization includes instances of most other previously mentioned core classes: ScoreBoard, LevelManager, PowerUpManager, Ball, Paddle, and Player.

BlockReader: The main purpose of this class is to read in information from text files in order to initialize all blocks (along with their type) for a given level.  This is a very important class as every subclass of Level has its own instantiation of BlockReader that reads in blocks for the level-specific text file.  All blocks read in are stored and then passed to LevelManager for maintenance/updating.

LevelManager: As mentioned above, LevelManager is responsible for storing and updating the blocks for the current level in which the game is being played.  In addition to this, LevelManager uses logic based on the blocks that it is storing in order to update which level is being displayed as well as update the level instance variable for player.  Additionally, LevelManager is responsible for determining collisions between all current blocks and the Ball.

PowerUpManager:  The PowerUpManager class is responsible for updating/maintaining all currently-existing power ups, activating such power ups upon collision with the Paddle class, and creating powerups when needed.  A significant amount of communication exists between the PowerUpManager class and the LevelManager class in the sense that the LevelManager determines when the Ball collides with a block and then calls PowerUpManager to create a new power up if the previously-destroyed block is an instance of the PowerUpBlock class.

The four classes that were previously mentioned are the most important classes in our group's implementation of Game because they utilize, as well as organize, the functionality of smaller classes in order to produce the final, interactive product that is seen upon running the main method. 


## Assumptions that Affect the Design


#### Features Affected by Assumptions

**Main Assumptions**

1. Assumes first paddle in list of paddles is the desired paddle
2. Assumes second ball in list of balls is the desired ball

Currently, the Game class is equipped with a list of current paddles (myPaddles) and a list of current balls (myBalls).  So, essentially, the two main assumptions in this project are that the first paddle in myPaddles (index 0) and the first ball in myBalls (index 0).  In essence, the program only "cares" about the first values of these lists.  The reason for these assumptions is that the current version of the game does not have any features relating to multiple paddles or multiple balls at a time.  However, we did see the potential for adding in these features, so our team chose to include support for them (i.e. the use of the lists).  But, since there is no functionality associated with having many of these items, we decided to leave out any logic that had to do with integrating them.

As a result of these assumptions, several classes, when accessing myBalls and myPaddles, "know" to access only the first elements of each list.


## Plan Changes

#### Differences between Plan and Final Product

We initially had the idea of pressing multiple buttons (Left,Right,Left,Right,
Down,Up,Down,Up) in order to create a power up. This became different from the 
final product because the complexity of creating such a power up would have taken
focus away from the design of our program. We tried to keep the level implementations
as close as possible to the initial plan, for example, in level 3, the laser
beams replaced the idea of the falling blocks. Instead of the multiple balls
power up, our fourth power up was the fiery ball power up. When creating our
plan we chose that our different types of blocks would require different numbers
of hits to get destroyed. However, as we were implementing it we decided 
we can implement something more complicated and ended up going with 
moving blocks, a power up block and then one block that required more hits
than the others to get destroyed.

## New Features HowTo

#### Easy to Add Features

**Adding New Levels:**

Adding a new level to the game should be simple enough as it only involves a few basic steps:
1. Create new class (named LevelXXX, where XXX represents the level number) extending Level
2. Overwrite the activateLevelFunctionality method (which performs the functionality that is specific to the current level)
3. Overwrite the emptyRootOfLevelSpecificObjects method (as needed) to remove the scene root of objects that are specific to the current level (this is called when changing levels) 
4. Create the new level and add it to the list POSSIBLE_LEVELS in LevelManager class (as seen below)

```java
   private final List<Level> POSSIBLE_LEVELS = List
      .of(new LevelSecret(this), new LevelOne(this), new LevelTwo(this), new LevelThree(this));
```

5. Add a new text file in the data folder that lists all desired blocks for the current level
6. Assign the name of the above text file to the final variable BLOCK_FILE

**Adding New PowerUps**

Adding a new power up to the game should be simple enough as it only involves a few basic steps:
1. Create new class (named PowerUpXXX, where XXX represents a shorthand description of the new power up's functionality) extending PowerUp
2. Overwrite the newCopy method (which produces a new instance of a power up of the same type of type as the one it was called on)
3. Overwrite the activatePowerUp method whose purpose is to perform whatever functionality is desired of the new power up
4. In addition to the previous step, add any other helper methods (or classes) that are necessary to implement the desired functionality of the new power up
5. Create the new power up and add it to the list POSSIBLE_POWER_UPS in PowerUpManager class (as seen below)

```java
   private final List<PowerUp> POSSIBLE_POWER_UPS = List
      .of(new PowerUpPaddleSize(0, 0, this), new PowerUpFireBall(0, 0, this),
          new PowerUpPaddleSpeed(0, 0, this), new PowerUpLife(0, 0, this));
```


#### Other Features not yet Done

**Additional Splash Screens**

The current implementation of this project solely involves a splash screen at the end of the game to indicate whether a player has won or lost as well as the player's curent score.  Therefore, potential features that could have been added include an initial splash screen, introducing the player to the game's name and rules, as well as splash screens between each level indicating current score, current lives, and the new functionality that is being added to the next level.  In order to implement such functionality, a future programmer should adhere to the following steps:

1. Create a new class(es) extending the StatusDisplay class
2. Populate the previously created class(es) with the desired information for the specific splash screen
3. Create a Map within ScoreBoard with Integer keys (corresponding to the level that is about to be played) and StatusDisplay values (corresponding to the desired splash screen to be displayed before the corresponding level)
4. Relay information from the Game class to the ScoreBoard class to display the StatusDisplay object that is mapped to the new level
5. Include simple, additional logic in the ScoreBoard class to indicate to the Game when the player is ready to move on to the level past the splash screen

