# Simulation Design Final
### Names

## Team Roles and Responsibilities

 * Team Member #1

 * Team Member #2

 * Team Member #3


## Design goals

#### What Features are Easy to Add


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

