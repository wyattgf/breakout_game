package breakout;


import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.Circle;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */

public abstract class PowerUp extends Circle {

  //constants
  private static final double POWER_UP_RADIUS = 5;
  private static final int POWER_UP_SPEED = 50;
  private static final int MY_Y_DIRECTION = 1;

  //instance variables
  private PowerUpManager myPowerUpManager;


  public PowerUp(double initialX, double initialY, PowerUpManager myPowerUpManager) {
    super(initialX, initialY, POWER_UP_RADIUS);
    this.myPowerUpManager = myPowerUpManager;
  }

  public abstract PowerUp newCopy();

  public void assignPowerUpColor(Color color) {
    this.setFill(color);
  }

  public abstract void activatePowerUp();

  public int getPowerUpSpeed() {
    return POWER_UP_SPEED;
  }

  public int getYDirection() {
    return MY_Y_DIRECTION;
  }

  public PowerUpManager getPowerUpManager() {
    return myPowerUpManager;
  }
}
