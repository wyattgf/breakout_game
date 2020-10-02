package breakout.Block;

import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;

/**
 * DurableBlock creates an implementation of Block that requires multiple hits until the block is
 * destroyed
 *
 * @author Hosam Tageldin, Wyatt Focht
 */
public class DurableBlock extends Block {

  private static final List<Color> BLOCK_COLORS = Arrays
      .asList(Color.WHITE, Color.LIGHTGREY, Color.DARKGREY, Color.GREY, Color.BLACK);
  private static final int DURABLE_BLOCK_STARTING_DURABILITY = 4;


  public DurableBlock(double myXPos, double myYPos) {
    super(myXPos, myYPos, DURABLE_BLOCK_STARTING_DURABILITY);
    setColor(DURABLE_BLOCK_STARTING_DURABILITY);
  }

  /**
   * This method creates a new block that is a copy (same instance variables) as the DurableBlock
   *
   * @return a new DurableBlock object
   */
  @Override
  public Block newBlock() {
    return new DurableBlock(this.getX(), this.getY());
  }

  /**
   * Sets the color of the block depending on the current durability of the block
   *
   * @param myDurability the current durability of the block
   */
  @Override
  public void setColor(int myDurability) {
    this.setFill(BLOCK_COLORS.get(myDurability));
  }

}
