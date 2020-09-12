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

  public void moveToCenter() {
    this.setX(screenWidth/2.0 - myWidth/2.0);
    this.setY(2.0/3*screenHeight);
  }

  public void changeWidth(double newWidth){
    myWidth = newWidth;
    this.setWidth(newWidth);
  }

  private void movePaddle(){
    this.setX(this.getX() + mySpeed);
  }
  public void moveLeft(){
    if (this.getX()>0) {
      mySpeed = Math.abs(mySpeed) * -1;
      movePaddle();
    }
  }
  public void moveRight(){
    if (this.getX()+this.getWidth()<screenWidth) {
      mySpeed = Math.abs(mySpeed);
      movePaddle();
    }
  }

}
