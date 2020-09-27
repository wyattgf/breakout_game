package breakout;

import breakout.PowerUp.PowerUp;
import java.util.Arrays;
import java.util.List;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public abstract class Block extends Rectangle {

  //constants
  private static final double BLOCK_WIDTH = 35;
  private static final double BLOCK_HEIGHT = 15;
  private static final int INITIAL_BLOCK_SPEED = 0;
  private static final int MOVING_BLOCK_SPEED = 2;
  private static final int MY_Y_DIRECTION = 20;
  private static final int MOVE_THIS_OFTEN = 50;

  //instance variables
  private int mySpeed;
  private int whenToMove;

  public Block(double myXPos, double myYPos) {
    super(myXPos, myYPos, BLOCK_WIDTH, BLOCK_HEIGHT);
    this.mySpeed = INITIAL_BLOCK_SPEED;
    whenToMove = 0;
  }

  public abstract void setColor();

  public abstract void updateBlockDurability();

  public abstract int getBlockDurability();

  public double getBlockWidth() {
    return BLOCK_WIDTH;
  }

  public double getBlockHeight() {
    return BLOCK_HEIGHT;
  }

  public void setSpeed() {
    mySpeed = MOVING_BLOCK_SPEED;
  }

  public int getYDirection() {
    return MY_Y_DIRECTION;
  }

  public void moveBlock(double elapsedTime) {
    whenToMove++;
    if (whenToMove%MOVE_THIS_OFTEN==0) {
      this.setY(this.getY() + this.getYDirection() * mySpeed * elapsedTime);
    }
  }


  public void changeFallingSpeed(int speed) {
    mySpeed = speed;
  }
}
