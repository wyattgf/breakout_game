package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Block extends Rectangle {

  //constants
  private static final double BLOCK_WIDTH = 35;
  private static final double BLOCK_HEIGHT = 15;
  //instance variables
  private int myDurability;

  public Block(double myXPos, double myYPos, int myDurability) {
    super(myXPos, myYPos, BLOCK_WIDTH, BLOCK_HEIGHT);
    this.myDurability = myDurability;
    setColor();
  }

  private void setColor() {
    switch (myDurability) {
      case -1 -> this.setFill(Color.BLACK);
      case 3 -> this.setFill(Color.DARKGREY);
      case 2 -> this.setFill(Color.BLUE);
      case 1 -> this.setFill(Color.RED);
    }
  }

  public double getBlockWidth() {
    return BLOCK_WIDTH;
  }

  public double getBlockHeight() {
    return BLOCK_HEIGHT;
  }


}
