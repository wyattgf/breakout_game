package breakout;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
/**
 *
 *
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Ball extends Circle{
  //constants
  private static final double BALL_RADIUS = 5;
  private static final double INITIAL_X = 200;
  private static final double INITIAL_Y = 200;
  private static final int INITIAL_BALL_SPEED = 100;
  //instance variables
  private int mySpeed;
  private int screenWidth;
  private int screenHeight;
  private int myXDirection;
  private int myYDirection;

  public Ball(int screenWidth, int screenHeight){
    super(INITIAL_X,INITIAL_Y,BALL_RADIUS);
    mySpeed = INITIAL_BALL_SPEED;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
    this.myXDirection = 1;
    this.myYDirection = -1;
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

  /**
   *
   * @param elapsedTime
   */
  public void moveBall(double elapsedTime){
    if(this.getCenterY() - this.getRadius() <= 0){
      myYDirection *= -1;
    }
    if(this.getCenterY() - this.getRadius() >= screenHeight){
      moveToCenter();
    }
    if(this.getCenterX() - this.getRadius() <= 0 ||
        this.getCenterX() + this.getRadius() >= screenWidth){
      myXDirection *= -1;
    }
    this.setCenterX(this.getCenterX() +  myXDirection * mySpeed * elapsedTime);
    this.setCenterY(this.getCenterY() +  myYDirection * mySpeed * elapsedTime);
  }

  public void bounce(){
    myYDirection *= -1;
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

  public void setMyXDirection(int direction){
    myXDirection = direction;
  }
  public void setMyYDirection(int direction){
    myYDirection = direction;
  }
}
