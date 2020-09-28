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
  }

  public void setForTesting() {
    List<Block> copy = new ArrayList<>(currentBlocks);
    for (Block block : copy) {
      removeSingularBlockFromRoot(block);
    }
    currentBlocks.clear();
    currentLevel = SET_FOR_TESTING_LEVEL;
    incrementLevel();
  }

  public List<Block> getLevelBlocks() {
    if (currentLevel < POSSIBLE_LEVELS.size()) {
      Level level = POSSIBLE_LEVELS.get(currentLevel);
      level.resetBlocksToOriginal();
      return level.getBlocks();
    }
    return new ArrayList<>();
  }

  public List<Block> getCurrentBlocks() {
    return currentBlocks;
  }

  public void incrementLevel() {
    myPowerUpManager.resetPositions();
    myBalls.get(0).moveToCenter();
    currentLevel++;
    blockCount = 1;
    removeAllBlocksFromRoot();
    currentBlocks = getLevelBlocks();
    addBlocksToRoot();

  }

  private void removeAllBlocksFromRoot() {
    for (Block block : currentBlocks) {
      myRoot.getChildren().remove(block);
    }
    currentBlocks.clear();
  }

  public void handleBallBlockCollision(Block block) {
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

  public void removeFirst() {
    Block block = currentBlocks.get(0);
    removeSingularBlockFromRoot(block);
    updateLevelBlocks();
  }

  public int currentLevel() {
    return currentLevel;
  }

  public int getNumberOfLevels() {
    return POSSIBLE_LEVELS.size();
  }

  public void levelFunctionality(double elapsedTime) {
    if (currentLevel < POSSIBLE_LEVELS.size()) {
      POSSIBLE_LEVELS.get(currentLevel).activateLevelFunctionality(elapsedTime,paused,screenHeight);
    }
  }

  public void freezeBlocks(){
    paused = !paused;
  }

  public void controlMovingBlocks(double elapsedTime) {
    if(!paused) {
      for (Block block : currentBlocks) {
        if (block instanceof MovingBlock) {
          ((MovingBlock) block).moveBlockHorizontally(elapsedTime, screenWidth);
        }
      }
    }
  }

  public Group getRoot() {
    return myRoot;
  }

  public int getScreenWidth() {
    return screenWidth;
  }

  public List<Paddle> getPaddles() {
    return myPaddles;
  }

  public Player getPlayer() {
    return myPlayer;
  }

  public Level getLevelForTesting(int level) {
    return POSSIBLE_LEVELS.get(level);
  }

  public void setLevel(int levelNumber) {
    POSSIBLE_LEVELS.get(currentLevel).emptyRootOfLevelSpecificObjects();
    currentLevel = levelNumber - 1;
    incrementLevel();
  }

  public int getCurrentLevelNumberForTesting() {
    return currentLevel;
  }

  public List<LaserBeam> getLaserBeamsForTesting() {
    LevelThree three = (LevelThree) getLevelForTesting(LEVEL_THREE_FOR_TESTING);
    return three.getCurrentLasers();
  }
}
