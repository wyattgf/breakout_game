package breakout.Level;

import breakout.Block.Block;
import breakout.Player;
import java.util.ArrayList;
import java.util.List;

public class LevelTwo extends Level {

  private static final String BLOCK_FILE = "levelTwo.txt";
  private LevelManager myLevelManager;
  private Player myPlayer;

  /**
   * This is a constructor for a LevelTwo object
   * @param myLevelManager LevelManager corresponding to the associated LevelManager with this level
   */
  public LevelTwo(LevelManager myLevelManager) {
    super(BLOCK_FILE, myLevelManager);
    this.myLevelManager = myLevelManager;
    createMovingBlocks();
  }

  /**
   * This method sets all current blocks in the level to falling blocks
   */
  public void createMovingBlocks() {
    for (Block block : getBlocks()) {
      block.setSpeed();
    }
  }

  @Override
  public void activateLevelFunctionality(double elapsedTime, boolean paused, int screenHeight) {
    List<Block> blocksToRemove = new ArrayList<>();
    if (!paused) {
      for (Block block : getBlocks()) {
        block.moveBlock(elapsedTime);
        checkBlockReachedBottom(block,screenHeight,blocksToRemove);
      }
    }
    removeBlocks(blocksToRemove);
    blocksToRemove.clear();
  }

  @Override
  public void emptyRootOfLevelSpecificObjects() {

  }

  private void checkBlockReachedBottom(Block block, int screenHeight, List<Block> blocksToRemove){
    if (myPlayer==null) myPlayer = myLevelManager.getPlayer();
    if(block.getY() + block.getBlockHeight() >= screenHeight){
      blocksToRemove.add(block);
      myPlayer.lostLife();
    }
  }

  private void removeBlocks(List<Block> blocksToRemove){
    for(Block block : blocksToRemove){
      getLevelManager().getPlayer().lostLife();
      getLevelManager().removeSingularBlockFromRoot(block);
      getLevelManager().updateLevelBlocks();
    }
  }

}
