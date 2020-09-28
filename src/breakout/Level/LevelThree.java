package breakout.Level;


import breakout.Block;
import breakout.LaserBeam;
import breakout.Paddle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelThree extends Level {

  private static final String BLOCK_FILE = "levelThree.txt";
  private static final int LASER_INTIAL_Y = 0;
  private static final int GENERATE_LASERS_THIS_OFTEN = 80;

  private int screenWidth;
  private int whenToShoot;
  private List<LaserBeam> currentLasers;
  private Paddle myPaddle;

  public LevelThree(LevelManager myLevelManager) {
    super(BLOCK_FILE, myLevelManager);
    currentLasers = new ArrayList<>();
    whenToShoot = 0;
  }

  public void activateLevelFunctionality(double elapsedTime){
    if (myPaddle==null) myPaddle = getLevelManager().getPaddles().get(0);
    if (screenWidth == 0) screenWidth = getLevelManager().getScreenWidth();
    checkLaserPaddleCollisions();
    activateLasers();
    for (LaserBeam laser: currentLasers){
      laser.moveLaser(elapsedTime);
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

  public LaserBeam randomlyGenerateLasers() {
      Random r = new Random();
      double randomXPosition = screenWidth * r.nextDouble();
      return new LaserBeam(randomXPosition, LASER_INTIAL_Y);
  }

  public void activateLasers() {
    whenToShoot++;
    if (whenToShoot%GENERATE_LASERS_THIS_OFTEN==0){
      LaserBeam laser = randomlyGenerateLasers();
      addLaserToRoot(laser);
    }
  }

  public void addLaserToRoot(LaserBeam laser) {
    currentLasers.add(laser);
    getLevelManager().getRoot().getChildren().add(laser);

  }

  private void removeLaserFromRoot(LaserBeam laser){
    currentLasers.remove(laser);
    getLevelManager().getRoot().getChildren().remove(laser);
  }

  public List<LaserBeam> getCurrentLasers(){
    return currentLasers;
  }
}
