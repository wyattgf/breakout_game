package breakout.Level;

public class LevelOne extends Level {

  private static final String BLOCK_FILE = "levelOne.txt";

  /**
   * This is a constructor for a LevelOne object
   *
   * @param myLevelManager LevelManager corresponding to the associated LevelManager with this
   *                       level
   */
  public LevelOne(LevelManager myLevelManager) {
    super(BLOCK_FILE, myLevelManager);
  }

  /**
   * This method activated the functionality specific to level one
   *
   * @param elapsedTime  double representing how much time has passed
   * @param paused       boolean representing whether the game is paused
   * @param screenHeight int representing the height of the screen
   */
  @Override
  public void activateLevelFunctionality(double elapsedTime, boolean paused, int screenHeight) {
    //not necessary for this level
  }

  @Override
  public void emptyRootOfLevelSpecificObjects() {
    //not necessary for this level
  }


}
