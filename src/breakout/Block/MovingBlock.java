package breakout.Block;

import javafx.scene.paint.Color;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
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

  /**
   * This method creates a new block that is a copy (same instance variables) as the MovingBlock
   *
   * @return a new MovingBlock object
   */
  @Override
  public Block newBlock() {
    return new MovingBlock(this.getX(), this.getY());
  }

  /**
   * Implements the functionality of this block which is moving the block horizontally
   *
   * @param elapsedTime the length of time that has passed
   * @param screenWidth the width of the screen
   */
  public void moveBlockHorizontally(double elapsedTime, int screenWidth) {
    if (getX() < 0 ||
        getX() + getWidth() >= screenWidth) {
      movingBlockXDirection *= -1;
    }
    this.setX(this.getX() + MOVING_BLOCK_SPEED * movingBlockXDirection * elapsedTime);
  }

  /**
   * Sets the color of the block depending on the current durability of the block
   *
   * @param myDurability the current durability of the block
   */
  @Override
  public void setColor(int myDurability) {
    this.setFill(MOVING_BLOCK_COLOR);
  }


}
