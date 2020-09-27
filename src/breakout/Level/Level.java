package breakout.Level;

import breakout.Block;
import breakout.BlockReader;
import java.util.List;

public class Level {

  private BlockReader myBlockReader;
  private List<Block> currentBlocks;

  public Level(String blockFile) {
    myBlockReader = new BlockReader();
    currentBlocks = initializeBlocks(blockFile);
  }

  public List<Block> getBlocks() {
    return currentBlocks;
  }

  private List<Block> initializeBlocks(String blockFile) {
    return myBlockReader.getBlocks(blockFile);
  }
}
