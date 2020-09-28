package breakout.Level;


import breakout.Block.Block;
import breakout.Block.BlockReader;
import java.util.ArrayList;
import java.util.List;

public abstract class Level {


  private BlockReader myBlockReader;
  private List<Block> currentBlocks;
  private LevelManager myLevelManager;
  private String blockFile;

  /**
   * This is a constructor for a generic Level object.
   * @param blockFile String that corresponds to the desired file
   * @param myLevelManager LevelManager object that is associated with the given level
   */
  public Level(String blockFile, LevelManager myLevelManager) {
    this.myLevelManager = myLevelManager;
    myBlockReader = new BlockReader();
    this.blockFile = blockFile;
    currentBlocks = new ArrayList<>();
  }

  /**
   * This method activated the functionality specific to a level.
   * @param elapsedTime double representing how much time has passed
   * @param paused boolean representing whether the game is paused
   * @param screenHeight int representing the height of the screen
   */
  public abstract void activateLevelFunctionality(double elapsedTime, boolean paused, int screenHeight);

  /**
   * This is a getter method for the currentBlocks in a level
   * @return List of Blocks corresponding to the current blocks in a level
   */
  public List<Block> getBlocks() {
    return currentBlocks;
  }

  /**
   * This method resets all the blocks in a level to their original specficiations
   */
  public void resetBlocksToOriginal(){
    currentBlocks = initializeBlocks(blockFile);
  }

  /**
   * This method creates and returns a list of Blocks created based off of a given text file
   * @param blockFile String corresponding to the desired file to read blocks off of
   * @return List of Blocks representing the current blocks in a given level
   */
  private List<Block> initializeBlocks(String blockFile) {
    return myBlockReader.getBlocks(blockFile);
  }

  /**
   * This method is a getter method for the myLevelManager instance variable
   * @return LevelManger object that is associated with the given level
   */
  public LevelManager getLevelManager(){
    return myLevelManager;
  }

  /**
   * This method empties the current root of all objects that are created specifically for a single levels functionality
   */
  public abstract void emptyRootOfLevelSpecificObjects();
}
