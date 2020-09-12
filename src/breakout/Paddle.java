package breakout;

import javafx.scene.paint.Paint;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Paddle extends Rectangle {
  //constants
  private static final double BLOCK_HEIGHT = 1;
  private static final double INITIAL_WIDTH = 5;
  private static final double INITIAL_X = 50;
  private static final double INITIAL_Y = 50;
  private static final int INITIAL_PADDLE_SPEED = 5;
  //instance variables
  private int mySpeed;
  private int screenWidth;

  public Paddle(int screenWidth){
    super(INITIAL_X,INITIAL_Y,INITIAL_WIDTH,BLOCK_HEIGHT);
    mySpeed = INITIAL_PADDLE_SPEED;
    this.screenWidth = screenWidth;
    this.setFill(Color.BLACK);
  }

  public void changeWidth(double newWidth){
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
