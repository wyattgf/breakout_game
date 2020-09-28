package breakout.Level;

public class LevelOne extends Level {

  private static final String BLOCK_FILE = "levelOne.txt";

  public LevelOne(LevelManager myLevelManager) {
    super(BLOCK_FILE,myLevelManager);
  }

  public void activateLevelFunctionality(double elapsedTime){};

}
