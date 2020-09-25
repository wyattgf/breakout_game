package breakout;

import breakout.PowerUp.PowerUpManager;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Ball extends Circle {

  //constants
  private static final double BALL_RADIUS = 5;
  private static final double INITIAL_X = 200;
  private static final double INITIAL_Y = 200;
  private static final int INITIAL_BALL_SPEED = 100;
  //instance variables
  private int mySpeed;
  private int screenWidth;
  private int screenHeight;
  private Group myRoot;
  private List<Paddle> myPaddles;
  private Player myPlayer;
  private PowerUpManager myPowerUpManager;
  private int myXDirection;
  private int myYDirection;
  private boolean paused;

  public Ball(int screenWidth, int screenHeight, Group myRoot, List<Paddle> myPaddles,
      Player myPlayer, PowerUpManager myPowerUpManager) {
    super(INITIAL_X, INITIAL_Y, BALL_RADIUS);
    mySpeed = INITIAL_BALL_SPEED;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.myRoot = myRoot;
    this.myPaddles = myPaddles;
    this.myPlayer = myPlayer;
    this.myPowerUpManager = myPowerUpManager;
    this.myXDirection = 1;
    this.myYDirection = -1;
    this.paused = false;
    moveToCenter();
    this.setFill(Color.BLACK);
  }
  public void checkCollisions(List<Block> currentLevelBlocks) {
    if (this.getBoundsInParent().intersects(myPaddles.get(0).getBoundsInParent())) {
      handlePaddleCollision();
    }
    for (Block block : currentLevelBlocks) {
      if (this.getBoundsInParent().intersects(block.getBoundsInParent())) {
        handleBlockCollisions(block, currentLevelBlocks);
        break;
      }
    }
    if (this.getCenterY() - BALL_RADIUS >= screenHeight) {
      myPlayer.lostLife();
      moveToCenter();
    }
  }


  private void handleBlockCollisions(Block block, List<Block> currentLevelBlocks) {
    if (getCenterY() - BALL_RADIUS >= block.getY() + block.getHeight()
        || getCenterY() + BALL_RADIUS <= block.getY()) {
      bounceY();

    } else if (getCenterX() <= block.getX()
        || getCenterX() >= block.getX() + block.getBlockWidth()) {
      bounceX();

    }
    myRoot.getChildren().remove(block);
    block.updateBlockDurability();
    if(block.getBlockDurability() == 0){
      currentLevelBlocks.remove(block);
      myPowerUpManager.createPowerUp(block.getX(), block.getY());
      myPlayer.blockDestroyed();
    }else{
      myRoot.getChildren().add(block);
    }

  }

  private void handlePaddleCollision() {
    if (getCenterY() <= myPaddles.get(0).getY()) {
      bounceY();
    } else if (getCenterX() + BALL_RADIUS <= myPaddles.get(0).getX()
        || getCenterX() >= myPaddles.get(0).getX()) {
      bounceX();
    }
  }

  /**
   * This method moves a Ball object to the center of the screen
   */
  public void moveToCenter() {
    this.setCenterX(screenWidth / 2.0);
    this.setCenterY(screenHeight / 2.0);
  }

  /**
   * @param elapsedTime
   */
  public void moveBall(double elapsedTime, List<Block> currentLevelBlocks) {
    checkCollisions(currentLevelBlocks);
    if (!paused) {
      if (this.getCenterY() - this.getRadius() <= 0) {
        myYDirection *= -1;
      }
      if (this.getCenterX() - this.getRadius() <= 0 ||
          this.getCenterX() + this.getRadius() >= screenWidth) {
        myXDirection *= -1;
      }
      this.setCenterX(this.getCenterX() + myXDirection * mySpeed * elapsedTime);
      this.setCenterY(this.getCenterY() + myYDirection * mySpeed * elapsedTime);
    }
  }

  private void bounce(boolean isY) {
    if (isY) {
      myYDirection *= -1;
    } else {
      myXDirection *= -1;
    }
  }

  public void bounceX() {
    bounce(false);
  }

  public void bounceY() {
    bounce(true);
  }

  /**
   * This method returns the mySpeed instance variable of a Ball
   *
   * @return int representing the mySpeed of a Ball object
   */
  public int getSpeed() {
    return mySpeed;
  }

  /**
   * This method sets mySpeed of a Ball to the given parameter
   *
   * @param speed int representing desired speed to set mySpeed to
   */
  public void setSpeed(int speed) {
    mySpeed = speed;
  }

  public void setMyXDirection(int direction) {
    myXDirection = direction;
  }

  public void setMyYDirection(int direction) {
    myYDirection = direction;
  }

  public void controlFreeze() {
    paused = !paused;
  }

}
