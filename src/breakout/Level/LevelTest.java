package breakout.Level;

import breakout.Block.Block;
import java.util.List;

public class LevelTest extends Level {

  private static final String BLOCK_FILE = "initialFile.txt";

  /**
   * This is a constructor for a LevelTest object
   * @param myLevelManager LevelManager corresponding to the associated LevelManager with this level
   */
  public LevelTest(LevelManager myLevelManager) {
    super(BLOCK_FILE,myLevelManager);
  }

  @Override
  public void activateLevelFunctionality(double elapsedTime, boolean paused, int screenHeight){}

  @Override
  public void emptyRootOfLevelSpecificObjects() {

  }

  ;

}
