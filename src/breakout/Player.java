package breakout;

import javafx.scene.shape.Rectangle;

/**
 * This player class will control all of the data involved with the player currently player the
 * game
 *
 * @author Hosam Tageldin, Wyatt Focht
 */

public class Player extends Rectangle {

  private static final int POINTS_FROM_BLOCK = 200;
  private static final int PLAYER_START_LIVES = 3;
  private static final int PLAYER_START_SCORE = 0;
  private static final int PLAYER_START_LEVEL = 0;

  //instance variables
  private int playerScore;
  private int playerLives;
  private int currentLevel;

  public Player() {
    playerScore = PLAYER_START_SCORE;
    playerLives = PLAYER_START_LIVES;
    currentLevel = PLAYER_START_LEVEL;
  }

  /**
   * @return the current score of the player
   */
  public int getScore() {
    return this.playerScore;
  }

  /**
   * @return player's current number of lives
   */
  public int livesLeft() {

    return this.playerLives;
  }

  /**
   * Adds a life to the players inventory
   */
  public void addLife() {
    this.updateLives(1);
  }

  /**
   * Removes a life from the players inventory
   */
  public void lostLife() {
    this.updateLives(-1);
  }

  /**
   * Updates the number of lives of the current player
   *
   * @param lives number of lives to add to the players inventory
   */
  private void updateLives(int lives) {
    this.playerLives += lives;
  }

  /**
   * Increments the score of the player when they destroy a block
   */
  public void blockDestroyed() {
    this.playerScore = playerScore + POINTS_FROM_BLOCK;
  }

  /**
   * This method is a setter method to set the level that the player is on
   *
   * @param level the level that the player is currently on
   */
  public void setLevel(int level) {
    currentLevel = level;
  }

  /**
   * This is a getter method to get the level that the player is currently on
   *
   * @return the level that the player is currently on
   */
  public int getCurrentLevel() {
    return currentLevel;
  }

}