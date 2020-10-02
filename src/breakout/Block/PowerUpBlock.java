package breakout.Block;

import java.io.FileInputStream;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class PowerUpBlock extends Block {

  private static final int POWER_UP_BLOCK_DURABILITY = 1;
  private static final String POWER_UP_BLOCK_BACKGROUND = "data/question.jpg";

  public PowerUpBlock(double myXPos, double myYPos) {
    super(myXPos, myYPos, POWER_UP_BLOCK_DURABILITY);
    setColor(POWER_UP_BLOCK_DURABILITY);
  }

  /**
   * This method creates a new block that is a copy (same instance variables) as the PowerUpBlock
   *
   * @return a new PowerUpBlock object
   */
  @Override
  public Block newBlock() {
    return new PowerUpBlock(this.getX(), this.getY());
  }

  /**
   * Sets the background image for the PowerUpBlock or the color red in case the file is invalid
   *
   * @param myDurability the current durability of the block
   */
  @Override
  public void setColor(int myDurability) {
    try {
      InputStream stream = new FileInputStream(POWER_UP_BLOCK_BACKGROUND);
      Image image = new Image(stream);
      this.setFill(new ImagePattern(image));
    } catch (Exception e) {
      this.setFill(Color.RED);
    }
  }

}
