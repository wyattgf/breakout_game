package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Paddle extends Rectangle {
  //constants
  private static final double BLOCK_HEIGHT = 50;
  private static final double INITIAL_WIDTH = 150;
  private static final double INITIAL_X = 0;
  private static final double INITIAL_Y = 0;
  private static final int INITIAL_PADDLE_SPEED = 5;
  //instance variables
  private int mySpeed;
  private double myWidth;
  private int screenWidth;
  private int screenHeight;

  public Paddle(int screenWidth, int screenHeight){
    super(INITIAL_X,INITIAL_Y,INITIAL_WIDTH,BLOCK_HEIGHT);
    mySpeed = INITIAL_PADDLE_SPEED;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.myWidth = INITIAL_WIDTH;
    moveToCenter();
    this.setFill(Color.BLACK);
  }

  /**
   * This method moves a Paddle object to the center of the screen
   */
  public void moveToCenter() {
    this.setX(screenWidth/2.0 - myWidth/2.0);
    this.setY(2.0/3*screenHeight);
  }

  /**
   * This method changes the myWidth of a Paddle object to a new, desired double
   * @param newWidth double representing the new desired width of a Paddle object
   */
  public void changeWidth(double newWidth){
    myWidth = newWidth;
    this.setWidth(newWidth);
  }

  private void movePaddle(){
    this.setX(this.getX() + mySpeed);
  }
  /**
   * This method causes a Paddle object to move left
   */
  public void moveLeft(){
    if (this.getX()>0) {
      mySpeed = Math.abs(mySpeed) * -1;
      movePaddle();
    }
  }

  /**
   * This method causes a Paddle object to move right
   */
  public void moveRight(){
    if (this.getX()+this.getWidth()<screenWidth) {
      mySpeed = Math.abs(mySpeed);
      movePaddle();
    }
  }

  /**
   * This method returns the mySpeed instance variable of a Paddle
   * @return int representing the mySpeed of a Paddle object
   */
  public int getSpeed(){
    return mySpeed;
  }
  /**
   * This method sets mySpeed of a Paddle to the given parameter
   * @param speed int representing desired speed to set mySpeed to
   */
  public void setSpeed(int speed){
    mySpeed = speed;
  }

}
