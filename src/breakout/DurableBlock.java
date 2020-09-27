package breakout;

import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;

public class DurableBlock extends Block{
  private static final List<Color> BLOCK_COLORS = Arrays
      .asList(Color.WHITE, Color.LIGHTGREY, Color.DARKGREY, Color.GREY, Color.BLACK);
  private static final int DURABLE_BLOCK_STARTING_DURABILITY = 4;

  private int myDurability;

  public DurableBlock(double myXPos, double myYPos) {
    super(myXPos, myYPos);
    myDurability = DURABLE_BLOCK_STARTING_DURABILITY;
    setColor();
  }

  @Override
  public void setColor() {
    this.setFill(BLOCK_COLORS.get(myDurability));
  }

  @Override
  public void updateBlockDurability() {
    this.myDurability = this.myDurability - 1;
    this.setColor();
  }

  @Override
  public int getBlockDurability(){
    return this.myDurability;
  }

}
