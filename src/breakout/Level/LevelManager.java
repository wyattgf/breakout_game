package breakout.Level;

import breakout.Ball;

import breakout.Block.Block;
import breakout.Block.MovingBlock;
import breakout.LaserBeam;
import breakout.Paddle;
import breakout.Player;
import breakout.PowerUp.PowerUpManager;
import breakout.Block.PowerUpBlock;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;

public class LevelManager {

  private final List<Level> POSSIBLE_LEVELS = List
      .of(new LevelTest(this), new LevelOne(this), new LevelTwo(this), new LevelThree(this));
  private static final int SET_FOR_TESTING_LEVEL = -1;
  private static final int SET_FOR_STARTING_LEVEL = 0;
  private static final int LEVEL_THREE_FOR_TESTING = 3;

  private Group myRoot;
  private List<Paddle> myPaddles;
  private List<Ball> myBalls;
  private Player myPlayer;
  private List<Block> currentBlocks;
  private int screenWidth;
  private int screenHeight;
  private int currentLevel;
  private int blockCount;
  private boolean paused;
  private PowerUpManager myPowerUpManager;

  /**
   * This is a constructor for a LevelManager object
   * @param myRoot Group corresponding to the current root of the game
   * @param myPaddles List of Paddles containing all current Paddles in the game
   * @param myBalls List of Balls containing all current Balls in the game
   * @param myPlayer Player corresponding to current player of the game
   * @param myPowerUpManager PowerUpManager corresponding to associated PowerUp Manager for current game
   * @param screenWidth int representing width of the current screen
   * @param screenHeight int representing height of the current screen
   */
  public LevelManager(Group myRoot, List<Paddle> myPaddles, List<Ball> myBalls, Player myPlayer,
      PowerUpManager myPowerUpManager, int screenWidth, int screenHeight) {
    this.myRoot = myRoot;
    this.myPaddles = myPaddles;
    this.myBalls = myBalls;
    this.myPlayer = myPlayer;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.myPowerUpManager = myPowerUpManager;
    this.paused = false;
    currentBlocks = new ArrayList();
    currentLevel = SET_FOR_STARTING_LEVEL;
    blockCount = 1;
    incrementLevel();
    updateLevelBlocks();
  }

  /**
   * This method sets the game configurations for testing in the GameTesting class
   */
  public void setForTesting() {
    List<Block> copy = new ArrayList<>(currentBlocks);
    for (Block block : copy) {
      removeSingularBlockFromRoot(block);
    }
    currentBlocks.clear();
    currentLevel = SET_FOR_TESTING_LEVEL;
    incrementLevel();
  }

  /**
   * This method returns a List containing the original blocks created when reading blocks in from a level file
   * @return List of Blocks corresponding to the original blocks generated when a new level is read in
   * from a file
   */
  public List<Block> getLevelBlocks() {
    if (currentLevel < POSSIBLE_LEVELS.size()) {
      Level level = POSSIBLE_LEVELS.get(currentLevel);
      level.resetBlocksToOriginal();
      return level.getBlocks();
    }
    return new ArrayList<>();
  }

  /**
   * This method returns the current blocks in the current level
   * @return List of Blocks corresponding to the current blocks in the current level
   */
  public List<Block> getCurrentBlocks() {
    return currentBlocks;
  }

  private void incrementLevel() {
    myPowerUpManager.resetPositions();
    myBalls.get(0).moveToCenter();
    currentLevel++;
    blockCount = 1;
    removeAllBlocksFromRoot();
    currentBlocks = getLevelBlocks();
    setPlayerLevel();
    addBlocksToRoot();
  }

  private void setPlayerLevel(){
    myPlayer.setLevel(currentLevel);
  }
  private void removeAllBlocksFromRoot() {
    for (Block block : currentBlocks) {
      myRoot.getChildren().remove(block);
    }
    currentBlocks.clear();
  }

  private void handleBallBlockCollision(Block block) {
    Ball currentBall = myBalls.get(0);
    if (block != null && !currentBall.isFiery()) {
      if (currentBall.getCenterY() - currentBall.getRadius() >= block.getY() + block.getHeight()
          || currentBall.getCenterY() + currentBall.getRadius() <= block.getY()) {
        currentBall.bounceY();

      } else if (currentBall.getCenterX() <= block.getX()
          || currentBall.getCenterX() >= block.getX() + block.getBlockWidth()) {
        currentBall.bounceX();

      } else {
        currentBall.bounceY();
      }
    }
    block.updateBlockDurability();
    updateLevelBlocks();
  }

  /**
   * This method updates the blocks in the current level.  Updating corresponding to removing, changing color,
   * or incrementing the level if all blocks have been destroyed.
   */
  public void updateLevelBlocks() {
    List<Block> copyOfBlocks = new ArrayList<>(currentBlocks);
    for (Block b : copyOfBlocks) {
      if (b.getBlockDurability() == 0) {
        removeSingularBlockFromRoot(b);
        myPlayer.blockDestroyed();
        if(b instanceof PowerUpBlock){
          myPowerUpManager.createPowerUp(b.getX(), b.getY());
        }
      }
    }

    if (currentBlocks.size() == 0) {
      incrementLevel();
    }
  }

  /**
   * This method removes the specified block from the current root
   *
   * @param b Block to be removed from the current root
   */
  public void removeSingularBlockFromRoot(Block b) {
    currentBlocks.remove(b);
    myRoot.getChildren().remove(b);
  }

  /**
   * This method adds the specified block up to the given root
   */
  public void addBlocksToRoot() {
    for (Block block : currentBlocks) {
      myRoot.getChildren().add(block);
      block.setId("block" + blockCount++);
    }
  }

  /**
   * This method determines which block (if any) has collided with the current ball
   *
   * @return Block object that has collided with the current ball
   */
  public void determineBallCollision() {
    for (Block block : currentBlocks) {
      if (myBalls.get(0).getBoundsInParent().intersects(block.getBoundsInParent())) {
        handleBallBlockCollision(block);
        break;
      }
    }

  }

  /**
   * This method removes the first (as determined by order in currentBlocks) block in the current
   * list of level Blocks
   */
  public void removeFirst() {
    Block block = currentBlocks.get(0);
    removeSingularBlockFromRoot(block);
    updateLevelBlocks();
  }

  /**
   * This method returns an int corresponding to the number of the current level
   * @return int corresponding to the current level that is being played
   */
  public int currentLevel() {
    return currentLevel;
  }

  /**
   * This method returns an into corresponding to the total possible number of levels in the game
   * @return int corresponding to the current number of levels that exists
   */
  public int getNumberOfLevels() {
    return POSSIBLE_LEVELS.size();
  }

  /**
   * This method activates the functionality that is specific to each level
   * @param elapsedTime double representing how much time has passed in the game
   */
  public void levelFunctionality(double elapsedTime) {
    if (currentLevel < POSSIBLE_LEVELS.size()) {
      POSSIBLE_LEVELS.get(currentLevel).activateLevelFunctionality(elapsedTime,paused,screenHeight);
    }
    updateLevelBlocks();
  }

  /**
   * This method freezes all current blocks from moving
   */
  public void freezeBlocks(){
    paused = !paused;
  }

  /**
   * This method controls the movement of all currently existing horizontally moving blocks
   * @param elapsedTime double representing how much time has passed in the game
   */
  public void controlMovingBlocks(double elapsedTime) {
    if(!paused) {
      for (Block block : currentBlocks) {
        if (block instanceof MovingBlock) {
          ((MovingBlock) block).moveBlockHorizontally(elapsedTime, screenWidth);
        }
      }
    }
  }

  /**
   * @return Group representing the current root of the game
   */
  public Group getRoot() {
    return myRoot;
  }

  /**
   * @return int representing the width of the game screen
   */
  public int getScreenWidth() {
    return screenWidth;
  }

  /**
   * @return List of all Paddles in the current game
   */
  public List<Paddle> getPaddles() {
    return myPaddles;
  }

  /**
   * @return Player object in the current game
   */
  public Player getPlayer() {
    return myPlayer;
  }

  /**
   * This method is specifically meant to facilitate testing in the GameTesting class
   * @param level int corresponding to the desired Level object
   * @return Level corresponding to the parameter level
   */
  public Level getLevelForTesting(int level) {
    return POSSIBLE_LEVELS.get(level);
  }

  /**
   * This method sets the current level of the game based on levelNumber
   * @param levelNumber int corresponding to desired level
   */
  public void setLevel(int levelNumber) {
    POSSIBLE_LEVELS.get(currentLevel).emptyRootOfLevelSpecificObjects();
    currentLevel = levelNumber - 1;
    incrementLevel();
  }

  /**
   * This method is specifically meant to facilitate testing in the GameTesting class
   * @return int corresponding to the current level of the game
   */
  public int getCurrentLevelNumberForTesting() {
    return currentLevel;
  }

  /**
   * This method is specifically meant to facilitate testing in the GameTesting class
   * @return List of current LaserBeams of LevelThree
   */
  public List<LaserBeam> getLaserBeamsForTesting() {
    LevelThree three = (LevelThree) getLevelForTesting(LEVEL_THREE_FOR_TESTING);
    return three.getCurrentLasers();
  }
}
