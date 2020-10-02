package breakout.Block;

import javafx.scene.shape.Rectangle;


/**
 * The purpose of this class is to create the base characteristics of a Block
 * and allow for manipulation when extended to be used as actual blocks found
 * in the game.
 *
 * @author Hosam Tageldin, Wyatt Focht
 */
public abstract class Block extends Rectangle {

  //constants
  private static final double BLOCK_WIDTH = 35;
  private static final double BLOCK_HEIGHT = 15;
  private static final int BLOCK_ROUNDED_EDGES = 10;
  private static final int MOVING_BLOCK_SPEED = 5;
  private static final int MY_Y_DIRECTION = 1;

  //instance variables
  private int mySpeed;
  private int myDurability;


  /**
   * This constructor sets up all the properties of the Block regarding the
   * location and the durability of the block.
   *
   * @param myXPos X positioning of the Block
   * @param myYPos Y positioning of the Block
   * @param blockDurability the number of hits until block is destroyed
   */
  public Block(double myXPos, double myYPos, int blockDurability) {
    super(myXPos, myYPos, BLOCK_WIDTH, BLOCK_HEIGHT);
    this.setArcHeight(BLOCK_ROUNDED_EDGES);
    this.setArcWidth(BLOCK_ROUNDED_EDGES);
    this.mySpeed = MOVING_BLOCK_SPEED;
    this.myDurability = blockDurability;
  }

  /**
   * This method creates a new block that is a copy (same instance variables) as the
   * block that it is called off of
   *
   * @return a new Block object
   */
  public abstract Block newBlock();

  /**
   * Sets the color of the block depending on the current durability of the block
   *
   * @param myDurability the current durability of the block
   */
  public abstract void setColor(int myDurability);

  /**
   * Updates the blocks durability on impact, decrementing it by one
   */
  public void updateBlockDurability() {
    this.myDurability = this.myDurability - 1;
    setColor(myDurability);
  }

  /**
   * @return an integer representing the blocks durability
   */
  public int getBlockDurability() {
    return this.myDurability;
  }

  /**
   * @return the width of the block
   */
  public double getBlockWidth() {
    return BLOCK_WIDTH;
  }

  /**
   * @return the height of the block
   */
  public double getBlockHeight() {
    return BLOCK_HEIGHT;
  }

  /**
   * Sets the speed of the block to the moving block speed
   */
  public void setSpeed() {
    mySpeed = MOVING_BLOCK_SPEED;
  }

  /**
   * @return an integer representing the speed of the moving block
   */
  public int getSpeed() {
    return mySpeed;
  }

  /**
   * @return the Y direction for which the blocks should move in
   */
  public int getYDirection() {
    return MY_Y_DIRECTION;
  }

  /**
   * Moves the block downward for the levels that want to implement blocks
   * that act in such a way to utilize
   *
   * @param elapsedTime the length of time that has passed
   */
  public void moveBlock(double elapsedTime) {
    this.setY(this.getY() + this.getYDirection() * mySpeed * elapsedTime);
  }


  /**
   * Changes the falling speed for the blocks to make the level more difficult
   *
   * @param speed the new falling speed for the blocks
   */
  public void changeFallingSpeed(int speed) {
    mySpeed = speed;
  }

}
