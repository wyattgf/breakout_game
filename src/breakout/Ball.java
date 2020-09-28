package breakout;

import breakout.PowerUp.PowerUpManager;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Ball extends Circle {

  //constants
  private static final double BALL_RADIUS = 5;
  private static final double INITIAL_X = 200;
  private static final double INITIAL_Y = 200;
  private static final int INITIAL_BALL_SPEED = 100;
  private static final String SUN = "data/sun.jpg";

  //instance variables
  private int mySpeed;
  private int screenWidth;
  private int screenHeight;
  private Group myRoot;
  private Paddle myPaddle;
  private Player myPlayer;
  private PowerUpManager myPowerUpManager;
  private int myXDirection;
  private int myYDirection;
  private boolean paused;
  private Image image;

  public Ball(int screenWidth, int screenHeight, Group myRoot, Paddle myPaddle,
      Player myPlayer, PowerUpManager myPowerUpManager) {
    super(INITIAL_X, INITIAL_Y, BALL_RADIUS);
    mySpeed = INITIAL_BALL_SPEED;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.myRoot = myRoot;
    this.myPaddle = myPaddle;
    this.myPlayer = myPlayer;
    this.myPowerUpManager = myPowerUpManager;
    this.myXDirection = 1;
    this.myYDirection = -1;
    this.paused = false;
    moveToCenter();
    try {
      InputStream stream = new FileInputStream(SUN);
      image = new Image(stream);
    }catch(Exception e){}
    this.setFill(new ImagePattern(image));
  }

  public void checkCollisions() {
    if (this.getBoundsInParent().intersects(myPaddle.getBoundsInParent())) {
      handlePaddleCollision();
    }

    if (this.getCenterY() - BALL_RADIUS >= screenHeight) {
      myPlayer.lostLife();
      moveToCenter();
    }
  }




  private void handlePaddleCollision() {
    if (getCenterY() <= myPaddle.getY()) {
      if(sameSideBounce()){
        bounceX();
      }
      bounceY();
    }
  }

  private boolean sameSideBounce(){
    boolean xDirectionIsRight = myXDirection>0;
    boolean leftSideOfPaddle = getCenterX()< (myPaddle.getX() + (myPaddle.getWidth()/2.0));
    return (xDirectionIsRight == leftSideOfPaddle);
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
  public void moveBall(double elapsedTime) {
    checkCollisions();
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
