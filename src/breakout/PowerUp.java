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

public class PowerUp extends Circle {

  //constants
  private static final double POWER_UP_RADIUS = 5;
  private static final int POWER_UP_SPEED = 50;
  private static final int MY_Y_DIRECTION = 1;
  private static final int LIFE_BONUS = 1;
  private static final int PADDLE_DELTA = 10;
  private static final List<Color> COLORS = new ArrayList<>(
      Arrays.asList(Color.YELLOW, Color.CYAN));
  //instance variables
  private Group myRoot;
  private boolean paused;
  private int screenHeight;
  private int myType;
  private Player myPlayer;
  private Paddle myPaddle;

  public PowerUp(double initialX, double initialY, Group myRoot, int screenHeight, Player myPlayer, Paddle myPaddle) {
    super(initialX, initialY, POWER_UP_RADIUS);
    assignPowerUpType();
    this.screenHeight = screenHeight;
    this.myPlayer = myPlayer;
    this.myPaddle = myPaddle;
    this.paused = false;
    this.myRoot = myRoot;
    addPowerUpToRoot();
  }

  public void addPowerUpToRoot() {
    myRoot.getChildren().add(this);
  }

  public void removePowerUpFromRoot() {
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

  public void assignPowerUpType() {
    myType = (int) ((Math.random() * (COLORS.size() - 0)) + 0);
    assignPowerUpColor();
  }

  private void assignPowerUpColor() {
    this.setFill(COLORS.get(myType));
  }

  public void activatePowerUp(){
    switch (myType) {
      case 0:
        increasePaddleSize();
        break;
      case 1:
        addExtraLife();
        break;
    }
    removePowerUpFromRoot();
  }
  public void increasePaddleSize(){
    myPaddle.changeWidth(myPaddle.getWidth()+PADDLE_DELTA);
  }

  public void addExtraLife(){
    myPlayer.setPlayerLives(myPlayer.getLives()+LIFE_BONUS);
  }

  public void manuallySetType(int type){
    myType = type;
  }
}
