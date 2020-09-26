package breakout.Level;

import breakout.Block;
import breakout.BlockReader;
import java.util.List;

public class Level {

  private static final String BLOCK_FILE = "initialFile.txt";

  private BlockReader myBlockReader;
  private List<Block> currentBlocks;

  public Level() {
    myBlockReader = new BlockReader();
    currentBlocks = initializeBlocks();
  }

  public List<Block> getBlocks() {
    return currentBlocks;
  }

  private List<Block> initializeBlocks() {
    return myBlockReader.getBlocks(BLOCK_FILE);
  }
}
