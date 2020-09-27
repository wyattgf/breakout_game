package breakout.Level;

import breakout.Block.Block;
import breakout.Block.BlockReader;
import java.util.List;

public class Level {

  private BlockReader myBlockReader;
  private List<Block> currentBlocks;
  private LevelManager myLevelManager;

  public Level(String blockFile, LevelManager myLevelManager) {
    this.myLevelManager = myLevelManager;
    myBlockReader = new BlockReader();
    currentBlocks = initializeBlocks(blockFile);
  }

  public List<Block> getBlocks() {
    return currentBlocks;
  }

  private List<Block> initializeBlocks(String blockFile) {
    return myBlockReader.getBlocks(blockFile);
  }

  public LevelManager getLevelManager(){
    return myLevelManager;
  }
}
