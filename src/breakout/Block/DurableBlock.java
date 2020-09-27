package breakout.Block;

import breakout.Block.Block;
import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;

public class DurableBlock extends Block {
  private static final List<Color> BLOCK_COLORS = Arrays
      .asList(Color.WHITE, Color.LIGHTGREY, Color.DARKGREY, Color.GREY, Color.BLACK);
  private static final int DURABLE_BLOCK_STARTING_DURABILITY = 4;


  public DurableBlock(double myXPos, double myYPos) {
    super(myXPos, myYPos, DURABLE_BLOCK_STARTING_DURABILITY);
    setColor(DURABLE_BLOCK_STARTING_DURABILITY);
  }

  @Override
  public void setColor(int myDurability) {
    this.setFill(BLOCK_COLORS.get(myDurability));
  }

}
