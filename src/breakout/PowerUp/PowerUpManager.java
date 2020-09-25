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
      .of(new PowerUpPaddleSize(0, 0, this), new PowerUpLife(0, 0, this));

  private Group myRoot;
  private List<Paddle> myPaddles;
  private List<Ball> myBalls;
  private int screenHeight;
  private Player myPlayer;
  private List<PowerUp> currentPowerUps;
  private int powerUpCount;
  private boolean paused;


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

  public void removePowerUpFromRoot(PowerUp p) {
    myRoot.getChildren().remove(p);
  }

  public void addPowerUpToRoot(PowerUp p) {
    myRoot.getChildren().add(p);
  }

  private PowerUp determinePowerUpCollision() {
    for (PowerUp powerup : currentPowerUps) {
      if (powerup.getBoundsInParent().intersects(myPaddles.get(0).getBoundsInParent())) {
        return powerup;
      }
    }
    return null;
  }

  public void handlePowerUpPaddleCollision() {
    PowerUp p = determinePowerUpCollision();
    if (p != null) {
      powerUpActivation(p);
    }
  }

  public void powerUpActivation(PowerUp p) {
    p.activatePowerUp();
    removePowerUpFromRoot(p);
    currentPowerUps.remove(p);
  }

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

  public void controlFreeze() {
    paused = !paused;
  }

  public void resetPositions() {
    for (PowerUp p : currentPowerUps) {
      removePowerUpFromRoot(p);
    }
    currentPowerUps.clear();
  }

  public Paddle getPaddle() {
    if (myPaddles!=null) {
      return myPaddles.get(0);
    }
    return null;
  }

  public Player getPlayer() {
    return myPlayer;
  }

  public List<PowerUp> getCurrentPowerUps() {
    return currentPowerUps;
  }
}
