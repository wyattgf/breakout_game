package breakout.Block;

import java.io.FileInputStream;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class PowerUpBlock extends Block {
  private static final int POWER_UP_BLOCK_DURABILITY = 1;
  private static final String POWER_UP_BACKGROUND = "data/question.jpg";

  private Image image;

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
    try {
      InputStream stream = new FileInputStream(POWER_UP_BACKGROUND);
      image = new Image(stream);
    }catch(Exception e){}

    this.setFill(new ImagePattern(image));
  }

}
