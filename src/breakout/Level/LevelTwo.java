package breakout.Level;

import breakout.Block;

public class LevelTwo extends Level {

  private static final String BLOCK_FILE = "levelTwo.txt";

  public LevelTwo() {
    super(BLOCK_FILE);
    createMovingBlocks();
  }

  public void createMovingBlocks() {
    for (Block block : getBlocks()) {
      block.setSpeed();
    }
  }

}
