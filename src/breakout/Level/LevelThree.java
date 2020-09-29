package breakout.Level;


import breakout.LaserBeam;
import breakout.Paddle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelThree extends Level {

  private static final String BLOCK_FILE = "levelThree.txt";
  private static final int LASER_INITIAL_Y = 0;
  private static final int GENERATE_LASERS_THIS_OFTEN = 60;
  private static final int LASER_WIDTH = 10;

  private int screenWidth;
  private int whenToShoot;
  private List<LaserBeam> currentLasers;
  private Paddle myPaddle;

  /**
   * This is a constructor for a LevelThree object
   *
   * @param myLevelManager LevelManager corresponding to the associated LevelManager with this
   *                       level
   */
  public LevelThree(LevelManager myLevelManager) {
    super(BLOCK_FILE, myLevelManager);
    currentLasers = new ArrayList<>();
    whenToShoot = 0;
  }

  @Override
  public void activateLevelFunctionality(double elapsedTime, boolean paused, int screenHeight) {
    initializePaddleAndScreenWidth();
    checkLaserPaddleCollisions();
    if (!paused) {
      activateLasers();
      for (LaserBeam laser : currentLasers) {
        laser.moveLaser(elapsedTime);
      }
    }
  }

  private void initializePaddleAndScreenWidth() {
    if (myPaddle == null) {
      myPaddle = getLevelManager().getPaddles().get(0);
    }
    if (screenWidth == 0) {
      screenWidth = getLevelManager().getScreenWidth();
    }
  }

  @Override
  public void emptyRootOfLevelSpecificObjects() {
    ArrayList<LaserBeam> copy = new ArrayList<>(currentLasers);
    for (LaserBeam laser : copy) {
      removeLaserFromRoot(laser);
    }
  }

  private void checkLaserPaddleCollisions() {
    for (LaserBeam laser : currentLasers) {
      if (myPaddle.getBoundsInParent().intersects(laser.getBoundsInParent())) {
        handleLaserPaddleCollision(laser);
        break;
      }
    }
  }

  private void handleLaserPaddleCollision(LaserBeam laser) {
    removeLaserFromRoot(laser);
    getLevelManager().getPlayer().lostLife();
  }

  /**
   * This method generates and returns a LaserBeam in a random position based on the width of the
   * screen
   *
   * @return LaserBeam object in a randomly generated position
   */
  private LaserBeam randomlyGenerateLasers() {
    Random r = new Random();
    double randomXPosition = (screenWidth - LASER_WIDTH) * r.nextDouble();
    return new LaserBeam(randomXPosition, LASER_INITIAL_Y);
  }

  /**
   * This method determines how often to shoot lasers from the top of the screen.  whenToShoot is a
   * counter that determines when to generate new lasers based on GENERATE_LASERS_THIS_OFTEN
   */
  private void activateLasers() {
    whenToShoot++;
    if (whenToShoot % GENERATE_LASERS_THIS_OFTEN == 0) {
      LaserBeam laser = randomlyGenerateLasers();
      addLaserToRoot(laser);
    }
  }

  /**
   * This method adds a given LaserBeam to the list of current lasers and the root
   *
   * @param laser LaserBeam that is to be added to the current root
   */
  private void addLaserToRoot(LaserBeam laser) {
    currentLasers.add(laser);
    getLevelManager().getRoot().getChildren().add(laser);

  }

  /**
   * This method removes a LaserBeam from the given root and the list of current lasers
   *
   * @param laser LaserBeam that is to be removed from the given root
   */
  private void removeLaserFromRoot(LaserBeam laser) {
    currentLasers.remove(laser);
    getLevelManager().getRoot().getChildren().remove(laser);
  }

  /**
   * This method is a getter for currentLasers instance variable
   *
   * @return List of LaserBeams corresponding to the running list of lasers in the current level
   */
  public List<LaserBeam> getCurrentLasers() {
    return currentLasers;
  }
}
