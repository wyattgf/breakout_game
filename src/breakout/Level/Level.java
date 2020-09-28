package breakout.Level;


import breakout.Block.Block;
import breakout.Block.BlockReader;
import java.util.ArrayList;
import java.util.List;

public class Level {


  private BlockReader myBlockReader;
  private List<Block> currentBlocks;
  private LevelManager myLevelManager;
  private String blockFile;

  public Level(String blockFile, LevelManager myLevelManager) {
    this.myLevelManager = myLevelManager;
    myBlockReader = new BlockReader();
    this.blockFile = blockFile;
    currentBlocks = new ArrayList<>();
  }

  public void activateLevelFunctionality(double elapsedTime){};

  public List<Block> getBlocks() {
    return currentBlocks;
  }

  public void resetBlocksToOriginal(){
    currentBlocks = initializeBlocks(blockFile);
  }

  private List<Block> initializeBlocks(String blockFile) {
    return myBlockReader.getBlocks(blockFile);
  }

  public LevelManager getLevelManager(){
    return myLevelManager;
  }
}
