package breakout;

import javafx.scene.shape.Rectangle;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Player extends Rectangle {
  private static final int POINTS_FROM_BLOCK = 200;
  private static final int PLAYER_START_LIVES = 3;
  private static final int PLAYER_START_SCORE = 0;

  //instance variables
  private int playerScore;
  private int playerLives;

  public Player() {
    playerScore = PLAYER_START_SCORE;
    playerLives = PLAYER_START_LIVES;
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
  public void addLife(){
    this.updateLives(1);
  }

  public void lostLife(){
    this.updateLives(-1);
  }
  private void updateLives(int lives){
    this.playerLives += lives;
  }

  public void blockDestroyed() {
    this.playerScore = playerScore + POINTS_FROM_BLOCK;
  }


  private void setPlayerLives(int lives) {
    this.playerLives = lives;
  }

}