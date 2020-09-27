package breakout.Level;

import breakout.Ball;
import breakout.Block;
import breakout.Paddle;
import breakout.Player;
import breakout.PowerUp.PowerUpManager;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;

public class LevelManager {

  private final List<Level> POSSIBLE_LEVELS = List
      .of(new LevelTest(), new LevelOne(), new LevelTwo(), new LevelThree());

  private Group myRoot;
  private List<Paddle> myPaddles;
  private List<Ball> myBalls;
  private Player myPlayer;
  private List<Block> currentBlocks;
  private int currentLevel;
  private int blockCount;
  private PowerUpManager myPowerUpManager;

  public LevelManager(Group myRoot, List<Paddle> myPaddles, List<Ball> myBalls, Player myPlayer,
      PowerUpManager myPowerUpManager) {
    this.myRoot = myRoot;
    this.myPaddles = myPaddles;
    this.myBalls = myBalls;
    this.myPlayer = myPlayer;
    this.myPowerUpManager = myPowerUpManager;
    currentLevel = -1;
    blockCount = 1;
    incrementLevel();
  }

  public void setForTesting(){
    for (int i = 0; i < POSSIBLE_LEVELS.size(); i++){
      if (i!=0){
        POSSIBLE_LEVELS.get(i).getBlocks().clear();
      }
    }
  }

  public List<Block> getLevelBlocks() {
    return POSSIBLE_LEVELS.get(currentLevel).getBlocks();
  }

  public List<Block> getCurrentBlocks(){
    return currentBlocks;
  }

  public void incrementLevel() {
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
    }
    block.updateBlockDurability();
    updateLevelBlocks();
  }

  public void updateLevelBlocks() {
    List<Block> blocksToRemove = new ArrayList<>();
    for (Block b : currentBlocks) {
      if (b.getBlockDurability() == 0) {
        blocksToRemove.add(b);
        myPowerUpManager.createPowerUp(b.getX(), b.getY());
        myPlayer.blockDestroyed();
      }
    }
    for (Block b : blocksToRemove){
      removeBlockFromRoot(b);
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
  public void removeBlockFromRoot(Block b) {
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
    currentBlocks.remove(0);
  }
}
