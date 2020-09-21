package breakout;

import java.util.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Block extends Rectangle {

  //constants
  private static final double BLOCK_WIDTH = 35;
  private static final double BLOCK_HEIGHT = 15;
  private static final List<Color> BLOCK_COLORS = Arrays.asList(Color.WHITE,Color.LIGHTGREY, Color.DARKGREY,Color.GREY, Color.BLACK);
  //instance variables
  private int myDurability;

  public Block(double myXPos, double myYPos, int myDurability) {
    super(myXPos, myYPos, BLOCK_WIDTH, BLOCK_HEIGHT);
    this.myDurability = myDurability;
    setColor();
  }

  private void setColor() {
    this.setFill(BLOCK_COLORS.get(myDurability));
  }
  public int getBlockDurability(){
    return this.myDurability;
  }

  public void updateBlockDurability(){
    this.myDurability = this.myDurability - 1;
    this.setColor();
  }

  public double getBlockWidth() {
    return BLOCK_WIDTH;
  }

  public double getBlockHeight() {
    return BLOCK_HEIGHT;
  }


}
