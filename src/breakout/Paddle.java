package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Paddle extends Rectangle {

  //constants
  private static final double PADDLE_HEIGHT = 15;
  private static final double INITIAL_WIDTH = 75;
  private static final double MAX_WIDTH = 150;
  private static final double INITIAL_X = 0;
  private static final double INITIAL_Y = 0;
  private static final int PADDLE_SPEED_AT_REST = 0;
  //instance variables
  private int mySpeed;
  private double myWidth;
  private int screenWidth;
  private int screenHeight;
  private boolean paused;

  public Paddle(int screenWidth, int screenHeight) {
    super(INITIAL_X, INITIAL_Y, INITIAL_WIDTH, PADDLE_HEIGHT);
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.myWidth = INITIAL_WIDTH;
    this.paused = false;
    moveToStartingPosition();
    this.setFill(Color.BLACK);
  }

  /**
   * This method moves a Paddle object to the center of the screen
   */
  public void moveToStartingPosition() {
    this.setX(screenWidth / 2.0 - myWidth / 2.0);
    this.setY(screenHeight - (2 * PADDLE_HEIGHT));
    mySpeed = PADDLE_SPEED_AT_REST;
  }

  /**
   * This method changes the myWidth of a Paddle object to a new, desired double
   *
   * @param newWidth double representing the new desired width of a Paddle object
   */
  public void changeWidth(double newWidth) {
    if (myWidth < MAX_WIDTH) {
      myWidth = newWidth;
    }
    this.setWidth(newWidth);
  }

  public void movePaddle(double elapsedTime) {
    if (!paused) {
      if ((this.getX() > 0 || mySpeed > 0) &&
          (this.getX() + this.getWidth() < screenWidth || mySpeed < 0)) {
        this.setX(this.getX() + elapsedTime * mySpeed);
      }
    }
  }

  /**
   * This method causes a Paddle object to move left
   */
  public void moveLeft() {
    mySpeed = -175;
  }

  /**
   * This method causes a Paddle object to move right
   */
  public void moveRight() {
    mySpeed = 175;
  }

  public void controlPause() {
    paused = !paused;
  }

  /**
   * This method returns the mySpeed instance variable of a Paddle
   *
   * @return int representing the mySpeed of a Paddle object
   */
  public int getSpeed() {
    return mySpeed;
  }

  /**
   * This method sets mySpeed of a Paddle to the given parameter
   *
   * @param speed int representing desired speed to set mySpeed to
   */
  public void setSpeed(int speed) {
    mySpeed = speed;
  }

}
