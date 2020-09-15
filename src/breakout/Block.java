package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


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
      case -1:
        this.setFill(Color.BLACK);
        break;
      case 3:
        this.setFill(Color.DARKGREY);
        break;
      case 2:
        this.setFill(Color.BLUE);
        break;
      case 1:
        this.setFill(Color.RED);
        break;
    }
  }

  public double getBlockWidth() {
    return BLOCK_WIDTH;
  }

  public double getBlockHeight() {
    return BLOCK_HEIGHT;
  }


}
