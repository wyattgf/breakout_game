package breakout.Level;

import breakout.Block.Block;
import java.util.List;

public class LevelTest extends Level {

  private static final String BLOCK_FILE = "initialFile.txt";

  public LevelTest(LevelManager myLevelManager) {
    super(BLOCK_FILE,myLevelManager);
  }

  @Override
  public void activateLevelFunctionality(double elapsedTime, boolean paused, int screenHeight){};

}
