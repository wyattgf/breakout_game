package breakout.Level;

import breakout.Ball;
import breakout.Block.Block;
import breakout.Block.MovingBlock;
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
  private final int SET_FOR_TESTING_LEVEL = -1;
  private final int SET_FOR_STARTING_LEVEL = 0;

  private Group myRoot;
  private List<Paddle> myPaddles;
  private List<Ball> myBalls;
  private Player myPlayer;
  private List<Block> currentBlocks;
  private int screenWidth;
  private int currentLevel;
  private int blockCount;
  private PowerUpManager myPowerUpManager;

  public LevelManager(Group myRoot, List<Paddle> myPaddles, List<Ball> myBalls, Player myPlayer,
      PowerUpManager myPowerUpManager, int screenWidth) {
    this.myRoot = myRoot;
    this.myPaddles = myPaddles;
    this.myBalls = myBalls;
    this.myPlayer = myPlayer;
    this.screenWidth = screenWidth;
    this.myPowerUpManager = myPowerUpManager;
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
      return POSSIBLE_LEVELS.get(currentLevel).getBlocks();
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
    currentBlocks = getLevelBlocks();
    addBlocksToRoot();

  }

  public void handleBallBlockCollision(Block block) {
    Ball currentBall = myBalls.get(0);
    if (block != null) {
      if (currentBall.getCenterY() - currentBall.getRadius() >= block.getY() + block.getHeight()
          || currentBall.getCenterY() + currentBall.getRadius() <= block.getY()) {
        currentBall.bounceY();

      } else if (currentBall.getCenterX() <= block.getX()
          || currentBall.getCenterX() >= block.getX() + block.getBlockWidth()) {
        currentBall.bounceX();

      }
      else {
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

  public void moveBlocks(double elapsedTime) {
    for (Block block: currentBlocks){
      if(block instanceof MovingBlock){
        ((MovingBlock) block).moveBlockHorizontally(elapsedTime,screenWidth);
      }
      block.moveBlock(elapsedTime);
    }
  }
}
