package breakout.Level;

public class LevelSecret extends Level {

  private static final String BLOCK_FILE = "secretLevel.txt";

  /**
   * This is a constructor for a secretLevel object
   * @param myLevelManager LevelManager corresponding to the associated LevelManager with this level
   */
  public LevelSecret(LevelManager myLevelManager) {
    super(BLOCK_FILE,myLevelManager);
  }

  @Override
  public void activateLevelFunctionality(double elapsedTime, boolean paused, int screenHeight){}

  @Override
  public void emptyRootOfLevelSpecificObjects() {

  }

}
