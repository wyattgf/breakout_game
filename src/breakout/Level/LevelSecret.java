package breakout.Level;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class LevelSecret extends Level {

  private static final String BLOCK_FILE = "secretLevel.txt";

  /**
   * This is a constructor for a secretLevel object
   *
   * @param myLevelManager LevelManager corresponding to the associated LevelManager with this
   *                       level
   */
  public LevelSecret(LevelManager myLevelManager) {
    super(BLOCK_FILE, myLevelManager);
  }

  /**
   * @param elapsedTime  double representing how much time has passed
   * @param paused       boolean representing whether the game is paused
   * @param screenHeight int representing the height of the screen
   */
  @Override
  public void activateLevelFunctionality(double elapsedTime, boolean paused, int screenHeight) {
    //not necessary for this level
  }

  /**
   * This method empties the current root of all objects that are created specifically for a single
   * levels functionality
   */
  @Override
  public void emptyRootOfLevelSpecificObjects() {
    //not necessary for this level
  }

}
