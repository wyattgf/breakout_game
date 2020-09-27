package breakout;

import breakout.PowerUp.PowerUp;
import java.util.Arrays;
import java.util.List;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Block extends Rectangle {

  //constants
  private static final double BLOCK_WIDTH = 35;
  private static final double BLOCK_HEIGHT = 15;
  private static final int INITIAL_BLOCK_SPEED = 0;
  private static final int MOVING_BLOCK_SPEED = 2;
  private static final int MY_Y_DIRECTION = 20;
  private static final int MOVE_THIS_OFTEN = 50;
  private static final List<Color> BLOCK_COLORS = Arrays
      .asList(Color.WHITE, Color.LIGHTGREY, Color.DARKGREY, Color.GREY, Color.BLACK);
  //instance variables
  private int myDurability;
  private int mySpeed;
  private int whenToMove;

  public Block(double myXPos, double myYPos, int myDurability) {
    super(myXPos, myYPos, BLOCK_WIDTH, BLOCK_HEIGHT);
    this.myDurability = myDurability;
    this.mySpeed = INITIAL_BLOCK_SPEED;
    whenToMove = 0;
    setColor();
  }

  private void setColor() {
    this.setFill(BLOCK_COLORS.get(myDurability));
  }

  public int getBlockDurability() {
    return this.myDurability;
  }

  public void updateBlockDurability() {
    this.myDurability = this.myDurability - 1;
    this.setColor();
  }

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
