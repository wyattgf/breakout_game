package breakout;

import javafx.scene.Group;
import javafx.scene.shape.Circle;

public class PowerUp extends Circle {

  //constants
  private static final double POWER_UP_RADIUS = 5;
  private static final int POWER_UP_SPEED = 50;
  private static final int MY_Y_DIRECTION = 1;
  //instance variables
  private Group myRoot;
  private boolean paused;
  private int screenHeight;

  public PowerUp(double initialX, double initialY, Group myRoot, int screenHeight) {
    super(initialX, initialY, POWER_UP_RADIUS);
    this.screenHeight = screenHeight;
    this.paused = false;
    this.myRoot = myRoot;
    addPowerUpToRoot();
  }
  public void addPowerUpToRoot(){
    myRoot.getChildren().add(this);
  }
  public void removePowerUpFromRoot(){
    myRoot.getChildren().remove(this);
  }
  public void movePowerUp(double elapsedTime) {
    if (!paused) {
      if (this.getCenterY() - this.getRadius() >= screenHeight) {
        removePowerUpFromRoot();
      }
      this.setCenterY(this.getCenterY() + MY_Y_DIRECTION * POWER_UP_SPEED * elapsedTime);
    }
  }
}
