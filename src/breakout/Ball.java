package breakout;

import breakout.PowerUp.PowerUpManager;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.InputStream;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
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
  private static final String SUN = "data/sun.jpg";

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
    this.fieryBall = false;
    this.fieryBallTimer = 0;
    this.paused = false;
    moveToCenter();
    try {
      InputStream stream = new FileInputStream(SUN);
      powerUpImage = new Image(stream);
    }catch(Exception e){}
  }

  public void checkCollisions() {
    if (!paused) {
      if (this.getCenterY() - this.getRadius() <= 0) {
        myYDirection *= -1;
      }
      if (this.getCenterX() - this.getRadius() <= 0 ||
          this.getCenterX() + this.getRadius() >= screenWidth) {
        myXDirection *= -1;
      }
      if (this.getBoundsInParent().intersects(myPaddle.getBoundsInParent())) {
        handlePaddleCollision();
      }
      if (this.getCenterY() - BALL_RADIUS >= screenHeight) {
        myPlayer.lostLife();
        moveToCenter();
      }
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
  public void controlBall(double elapsedTime) {
    checkCollisions();
    timeBallPowerUp();
    if(!paused){
      this.setCenterX(this.getCenterX() + myXDirection * mySpeed * elapsedTime);
      this.setCenterY(this.getCenterY() + myYDirection * mySpeed * elapsedTime);
    }
  }

  private void timeBallPowerUp(){
    fieryBallTimer ++;
    if(fieryBallTimer == FIERY_BALL_END_TIME){
      ballPowerUp(false);
    }
  }

  public void ballPowerUp(boolean isFiery){
    fieryBall = isFiery;
    if(isFiery){
      fieryBallTimer = 0;
      this.setRadius(POWER_UP_RADIUS);
      this.setFill(new ImagePattern(powerUpImage));
    }else{
      this.setRadius(BALL_RADIUS);
      this.setFill(Color.BLACK);
    }
  }

  public boolean isFiery(){
    return fieryBall;
  }

  public void bounceX() {
    myXDirection *=-1;
  }

  public void bounceY() {
    myYDirection *=-1;
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
