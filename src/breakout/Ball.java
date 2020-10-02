package breakout;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.InputStream;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * Implements the functionality of the ball
 *
 * @author Hosam Tageldin, Wyatt Focht
 */

public class Ball extends Circle {

  //constants
  private static final double BALL_RADIUS = 5;
  private static final double POWER_UP_RADIUS = 7;
  private static final double INITIAL_X = 200;
  private static final double INITIAL_Y = 200;
  private static final int INITIAL_BALL_SPEED = 100;
  private static final int FIERY_BALL_END_TIME = 500;
  private static final String SUN_FILE_LOCATION = "data/sun.jpg";

  //instance variables
  private int mySpeed;
  private int screenWidth;
  private int screenHeight;
  private Paddle myPaddle;
  private Player myPlayer;
  private int myXDirection;
  private int myYDirection;
  private boolean paused;
  private boolean fieryBall;
  private int fieryBallTimer;
  private Image powerUpImage;

  public Ball(int screenWidth, int screenHeight, Paddle myPaddle, Player myPlayer) {
    super(INITIAL_X, INITIAL_Y, BALL_RADIUS);
    mySpeed = INITIAL_BALL_SPEED;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.myPaddle = myPaddle;
    this.myPlayer = myPlayer;
    this.myXDirection = 1;
    this.myYDirection = -1;
    moveToCenter();
    try {
      InputStream stream = new FileInputStream(SUN_FILE_LOCATION);
      powerUpImage = new Image(stream);
    } catch (Exception e) {
      powerUpImage = null;
    }
  }

  /**
   * Controls the movements of the Ball
   *
   * @param elapsedTime the length of time that has passed in the game
   */
  public void controlBall(double elapsedTime) {
    checkCollisions();
    timeBallPowerUp();
    if (!paused) {
      this.setCenterX(this.getCenterX() + myXDirection * mySpeed * elapsedTime);
      this.setCenterY(this.getCenterY() + myYDirection * mySpeed * elapsedTime);
    }
  }

  /**
   * Checks the collisions of the ball with the paddle, wall and whether it is out of the screen
   */
  public void checkCollisions() {
    if (!paused) {
      if (this.getBoundsInParent().intersects(myPaddle.getBoundsInParent())) {
        handlePaddleCollision();
      }
      bounceBallOffWalls();
      checkBallOutOfScreen();
    }
  }

  private void bounceBallOffWalls() {
    if (this.getCenterY() - this.getRadius() <= 0) {
      myYDirection *= -1;
    }
    if (this.getCenterX() - this.getRadius() <= 0 ||
        this.getCenterX() + this.getRadius() >= screenWidth) {
      myXDirection *= -1;
    }
  }

  private void checkBallOutOfScreen() {
    if (this.getCenterY() - BALL_RADIUS >= screenHeight) {
      myPlayer.lostLife();
      moveToCenter();
    }
  }

  private void handlePaddleCollision() {
    if (getCenterY() <= myPaddle.getY()) {
      if (sameSideBounce()) {
        bounceX();
      }
      bounceY();
    }
  }

  private boolean sameSideBounce() {
    boolean xDirectionIsRight = myXDirection > 0;
    boolean leftSideOfPaddle = getCenterX() < (myPaddle.getX() + (myPaddle.getWidth() / 2.0));
    return (xDirectionIsRight == leftSideOfPaddle);
  }

  /**
   * This method moves a Ball object to the center of the screen as a reset
   */
  public void moveToCenter() {
    this.setCenterX(screenWidth / 2.0);
    this.setCenterY(screenHeight / 2.0);
  }

  private void timeBallPowerUp() {
    fieryBallTimer++;
    if (fieryBallTimer == FIERY_BALL_END_TIME) {
      ballPowerUp(false);
    }
  }

  /**
   * Turns the ball into a fire ball when the power up is activated
   *
   * @param isFiery boolean to see if the ball should be a fire ball
   */
  public void ballPowerUp(boolean isFiery) {
    fieryBall = isFiery;
    fieryBallTimer = 0;
    if (isFiery && powerUpImage != null) {
      this.setRadius(POWER_UP_RADIUS);
      this.setFill(new ImagePattern(powerUpImage));
    } else {
      this.setRadius(BALL_RADIUS);
      this.setFill(Color.BLACK);
    }
  }

  /**
   * Method is used for other classes to know if the ball should be functioning as a fire ball
   *
   * @return boolean regarding whether the ball is fiery or not
   */
  public boolean isFiery() {
    return fieryBall;
  }

  /**
   * Bounce the ball in the X direction
   */
  public void bounceX() {
    myXDirection *= -1;
  }

  /**
   * Bounce the ball in the Y direction
   */
  public void bounceY() {
    myYDirection *= -1;
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

  /**
   * Sets the X direction of the balls movement
   *
   * @param direction requested direction for the X movement of the ball
   */
  public void setMyXDirection(int direction) {
    myXDirection = direction;
  }

  /**
   * Sets the Y direction of the balls movement
   *
   * @param direction requested direction for the Y movement of the ball
   */
  public void setMyYDirection(int direction) {
    myYDirection = direction;
  }

  /**
   * Used to freeze the ball from moving if the Freeze cheat key has been activated
   */
  public void controlFreeze() {
    paused = !paused;
  }

}
