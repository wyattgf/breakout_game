package breakout;

import javafx.scene.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerUpManager {

  private static final List<PowerUp> POSSIBLE_POWER_UPS = List.of(new PowerUpPaddleSize());

  private Group myRoot;
  private List<Paddle> myPaddles;
  private List<Ball> myBalls;
  private int screenHeight;
  private Player myPlayer;
  private List<PowerUp> currentPowerUps;
  private int powerUpCount;


  public PowerUpManager(Group myRoot, List<Paddle> myPaddles, List<Ball> myBalls, Player myPlayer,
      int screenHeight) {
    this.myRoot = myRoot;
    this.myPaddles = myPaddles;
    this.myBalls = myBalls;
    this.myPlayer = myPlayer;
    this.screenHeight = screenHeight;
    currentPowerUps = new ArrayList<PowerUp>();
    powerUpCount = 0;

  }

  public List<PowerUp> createPowerUps() {
    PowerUp newPowerUp = POSSIBLE_POWER_UPS.get(new Random().nextInt(POSSIBLE_POWER_UPS.size()));
    newPowerUp.setId("powerup" + powerUpCount);
    powerUpCount++;
    currentPowerUps.add(newPowerUp);
    addPowerUpToRoot(newPowerUp);

    return currentPowerUps;
  }

  public void removePowerUpFromRoot(PowerUp p) {
    myRoot.getChildren().remove(p);
  }

  public void addPowerUpToRoot(PowerUp p) {
    myRoot.getChildren().add(p);
  }

  private boolean determinePowerUpCollision(){
    for (PowerUp powerup : currentPowerUps) {
      if (powerup.getBoundsInParent().intersects(myPaddles.get(0).getBoundsInParent())) {
        return true;
      }
    }
    return false;
  }

  public void handlePowerUpPaddleCollision(PowerUp p) {
    if (determinePowerUpCollision()){
      p.activatePowerUp();
      currentPowerUps.remove(p);
    }
  }
}
