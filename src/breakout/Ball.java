package breakout;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
/**
 *
 *
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Ball extends Circle{
  //constants
  private static final double BALL_RADIUS = 20;
  private static final double INITIAL_X = 200;
  private static final double INITIAL_Y = 400;
  private static final int INITIAL_BALL_SPEED = 25;
  //instance variables
  private int mySpeed;
  private int screenWidth;
  private int screenHeight;

  public Ball(int screenWidth, int screenHeight){
    super(INITIAL_X,INITIAL_Y,BALL_RADIUS);
    mySpeed = INITIAL_BALL_SPEED;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    moveToCenter();
    this.setFill(Color.BLACK);
  }

  /**
   * This method moves a Ball object to the center of the screen
   */
  public void moveToCenter() {
    this.setCenterX(screenWidth/2.0);
    this.setCenterY(screenHeight/2.0);
  }

  public void moveBall(double elapsedTime){
    this.setCenterY(this.getCenterY() + mySpeed * elapsedTime);
  }

  /**
   * This method returns the mySpeed instance variable of a Ball
   * @return int representing the mySpeed of a Ball object
   */
  public int getSpeed(){
    return mySpeed;
  }

  /**
   * This method sets mySpeed of a Ball to the given parameter
   * @param speed int representing desired speed to set mySpeed to
   */
  public void setSpeed(int speed){
    mySpeed = speed;
  }
}
