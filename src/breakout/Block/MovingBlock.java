package breakout.Block;

import breakout.Block.Block;
import javafx.scene.paint.Color;

public class MovingBlock extends Block {
  private static final Color MOVING_BLOCK_COLOR = Color.BLUE;
  private static final int MOVING_BLOCK_DURABILITY = 1;
  private static final int MOVING_BLOCK_SPEED = 10;
  private static final int INITIAL_DIRECTION = 1;

  private int movingBlockXDirection;

  public MovingBlock(double myXPos, double myYPos) {
    super(myXPos, myYPos, MOVING_BLOCK_DURABILITY);
    setColor(MOVING_BLOCK_DURABILITY);
    movingBlockXDirection = INITIAL_DIRECTION;
  }

  public void moveBlockHorizontally(double elapsedTime,int screenWidth){
    if(getX() < 0 ||
        getX() + getWidth() >= screenWidth){
      movingBlockXDirection *= -1;
    }
    this.setX(this.getX() +  MOVING_BLOCK_SPEED * movingBlockXDirection * elapsedTime);
  }

  @Override
  public void setColor(int myDurability) {
    this.setFill(MOVING_BLOCK_COLOR);
  }



}
