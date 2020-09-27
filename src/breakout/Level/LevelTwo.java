package breakout.Level;

import breakout.Block.Block;

public class LevelTwo extends Level {

  private static final String BLOCK_FILE = "levelTwo.txt";

  public LevelTwo(LevelManager myLevelManager) {
    super(BLOCK_FILE, myLevelManager);
    createMovingBlocks();
  }

  public void createMovingBlocks() {
    for (Block block : getBlocks()) {
      block.setSpeed();
    }
  }

}
