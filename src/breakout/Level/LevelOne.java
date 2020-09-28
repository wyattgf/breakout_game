package breakout.Level;

public class LevelOne extends Level {

  private static final String BLOCK_FILE = "levelOne.txt";

  public LevelOne(LevelManager myLevelManager) {
    super(BLOCK_FILE,myLevelManager);
  }

  @Override
  public void activateLevelFunctionality(double elapsedTime, boolean paused, int screenHeight){}

  @Override
  public void emptyRootOfLevelSpecificObjects() {

  }


}
