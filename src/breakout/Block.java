package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
/**
 *
 *
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Block extends Rectangle {
  //constants
  private static final int BLOCK_WIDTH = 1;
  private static final int BLOCK_HEIGHT = 1;
  //instance variables
  private int myDurability;
  private Paint myColor;

  public Block(int xPos, int yPos, int durability ){
    super(xPos,yPos,BLOCK_WIDTH,BLOCK_HEIGHT);
    myDurability = durability;
    setColor();
  }

  private void setColor() {
    switch (myDurability) {
      case -1 -> this.setFill(Color.BLACK);
      case 3 -> this.setFill(Color.DARKGREY);
      case 2 -> this.setFill(Color.WHITESMOKE);
      case 1 -> this.setFill(Color.RED);
    }
  }


}
