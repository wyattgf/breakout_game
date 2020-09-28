package breakout.Block;

import breakout.Block.Block;
import javafx.scene.paint.Color;

public class PowerUpBlock extends Block {
  private static final Color POWER_UP_BLOCK_COLOR = Color.RED;
  private static final int POWER_UP_BLOCK_DURABILITY = 1;

  public PowerUpBlock(double myXPos, double myYPos) {
    super(myXPos, myYPos, POWER_UP_BLOCK_DURABILITY);
    setColor(POWER_UP_BLOCK_DURABILITY);
  }

  @Override
  public Block newBlock(){
    return new PowerUpBlock(this.getX(), this.getY());
  }

  @Override
  public void setColor(int myDurability) {
    this.setFill(POWER_UP_BLOCK_COLOR);
  }

}
