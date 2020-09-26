package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Paddle extends Rectangle {

  //constants
  private static final double PADDLE_HEIGHT = 5;
  private static final double INITIAL_WIDTH = 75;
  private static final double MAX_WIDTH = 100;
  private static final double MAX_SPEED = 400;
  private static final double INITIAL_X = 0;
  private static final double INITIAL_Y = 0;
  private static final int PADDLE_SPEED_AT_REST = 0;
  private static final int INITIAL_PADDLE_SPEED_AT_KEY_PRESS = 175;
  private static final int WIDTH_DELTA = 10;
  private static final int SPEED_DELTA = 10;
  //instance variables
  private int mySpeed;
  private int paddleSpeedAtKeyPress;
  private double myWidth;
  private int screenWidth;
  private int screenHeight;
  private boolean paused;

  public Paddle(int screenWidth, int screenHeight) {
    super(INITIAL_X, INITIAL_Y, INITIAL_WIDTH, PADDLE_HEIGHT);
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.myWidth = INITIAL_WIDTH;
    paddleSpeedAtKeyPress = INITIAL_PADDLE_SPEED_AT_KEY_PRESS;
    this.paused = false;
    moveToStartingPosition();
    this.setFill(Color.BLACK);
  }

  /**
   * This method moves a Paddle object to the center of the screen
   */
  public void moveToStartingPosition() {
    myWidth = INITIAL_WIDTH;
    paddleSpeedAtKeyPress = INITIAL_PADDLE_SPEED_AT_KEY_PRESS;
    changeWidth(myWidth);
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
      this.setWidth(newWidth);
    }
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
    mySpeed = -paddleSpeedAtKeyPress;
  }

  /**
   * This method causes a Paddle object to move right
   */
  public void moveRight() {
    mySpeed = paddleSpeedAtKeyPress;
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


  public void setSpeedFromDelta() {
    if (paddleSpeedAtKeyPress < MAX_SPEED) {
      paddleSpeedAtKeyPress += SPEED_DELTA;
    }
  }

  public void setWidthFromDelta() {
    changeWidth(WIDTH_DELTA + getWidth());
  }

  public int getKeyPressSpeed() {
    return paddleSpeedAtKeyPress;
  }

}
