package breakout.PowerUp;

import breakout.Ball;
import breakout.Paddle;
import breakout.Player;
import javafx.scene.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerUpManager {

  private final List<PowerUp> POSSIBLE_POWER_UPS = List
      .of(new PowerUpPaddleSize(0, 0, this), new PowerUpLife(0, 0, this),
          new PowerUpPaddleSpeed(0, 0, this));

  private Group myRoot;
  private List<Paddle> myPaddles;
  private List<Ball> myBalls;
  private int screenHeight;
  private Player myPlayer;
  private List<PowerUp> currentPowerUps;
  private int powerUpCount;
  private boolean paused;


  /**
   * This method creates a PowerUpManager object
   *
   * @param myRoot       Group that refers to the root that the game is displaying
   * @param myPaddles    List that refers to all exsiting paddles in the current game
   * @param myBalls      List that refers to all existing balls in the current game
   * @param myPlayer     Player that refers to the player playing the current game
   * @param screenHeight int that refers to the height of the screen of the current game
   */
  public PowerUpManager(Group myRoot, List<Paddle> myPaddles, List<Ball> myBalls, Player myPlayer,
      int screenHeight) {
    this.myRoot = myRoot;
    this.myPaddles = myPaddles;
    this.myBalls = myBalls;
    this.myPlayer = myPlayer;
    this.screenHeight = screenHeight;
    currentPowerUps = new ArrayList<>();
    powerUpCount = 0;
    paused = false;

  }

  /**
   * This method creates a new power up at the given x and y position.
   *
   * @param xPosition double representing the x position of the new power up
   * @param yPosition double representing the y position of the new power up
   * @return
   */
  public PowerUp createPowerUp(double xPosition, double yPosition) {
    PowerUp placeHolder = POSSIBLE_POWER_UPS.get(new Random().nextInt(POSSIBLE_POWER_UPS.size()));
    PowerUp newPowerUp = placeHolder.newCopy();
    newPowerUp.setCenterX(xPosition);
    newPowerUp.setCenterY(yPosition);
    newPowerUp.setId("powerup" + powerUpCount);
    powerUpCount++;
    currentPowerUps.add(newPowerUp);
    addPowerUpToRoot(newPowerUp);

    return newPowerUp;
  }

  /**
   * This method removes the specified power up from the current root
   *
   * @param p PowerUp to be removed from the current root
   */
  public void removePowerUpFromRoot(PowerUp p) {
    myRoot.getChildren().remove(p);
  }

  /**
   * This method adds the specified power up to the given root
   *
   * @param p PowerUp to be added to the current root
   */
  public void addPowerUpToRoot(PowerUp p) {
    myRoot.getChildren().add(p);
  }

  /**
   * This method determines which power up (if any) has collided with the current paddle
   *
   * @return PowerUp object that has collided with the current Paddle
   */
  private PowerUp determinePowerUpCollision() {
    for (PowerUp powerup : currentPowerUps) {
      if (powerup.getBoundsInParent().intersects(myPaddles.get(0).getBoundsInParent())) {
        return powerup;
      }
    }
    return null;
  }

  /**
   * Upon a power up colliding with the current paddle, this method activates that specific power
   * up
   */
  public void handlePowerUpPaddleCollision() {
    PowerUp p = determinePowerUpCollision();
    if (p != null) {
      powerUpActivation(p);
    }
  }

  /**
   * This method activates a power up and removes it from the current root
   *
   * @param p PowerUp object that is to be activated and removed from the current root
   */
  public void powerUpActivation(PowerUp p) {
    p.activatePowerUp();
    removePowerUpFromRoot(p);
    currentPowerUps.remove(p);
  }

  /**
   * This method updates the position of all currently existing power ups based on the elapsed time
   *
   * @param elapsedTime double representing how much time has passed
   */
  public void updatePowerUps(double elapsedTime) {
    for (PowerUp p : currentPowerUps) {
      if (!paused) {
        if (p.getCenterY() - p.getRadius() >= screenHeight) {
          removePowerUpFromRoot(p);
        }
        p.setCenterY(p.getCenterY() + p.getYDirection() * p.getPowerUpSpeed() * elapsedTime);
      }
    }
  }

  /**
   * This method controls the indicator of whether or not all current power ups should be paused
   */
  public void controlFreeze() {
    paused = !paused;
  }

  /**
   * This method removes all existing power ups from the current root and from the list of current
   * power ups
   */
  public void resetPositions() {
    for (PowerUp p : currentPowerUps) {
      removePowerUpFromRoot(p);
    }
    currentPowerUps.clear();
  }

  /**
   * This method is a getter method for the current paddle
   *
   * @return Paddle corresponding to the current paddle
   */
  public Paddle getPaddle() {
    if (myPaddles != null) {
      return myPaddles.get(0);
    }
    return null;
  }

  /**
   * This method is a getter method for the current player
   *
   * @return Player corresponding to the current player
   */
  public Player getPlayer() {
    return myPlayer;
  }

  /**
   * This method is a getter method for the list that contains all current power ups in the game
   *
   * @return List containing all current power ups
   */
  public List<PowerUp> getCurrentPowerUps() {
    return currentPowerUps;
  }
}
