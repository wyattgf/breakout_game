package breakout;

import javafx.scene.shape.Rectangle;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Player extends Rectangle {

  //instance variables
  private int playerScore;
  private int playerLives;

  public Player() {
    playerScore = 0;
    playerLives = 3;
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

  /**
   * @param score the new score for the player
   */
  public void setPlayerScore(int score) {
    this.playerScore = score;
  }


  private void setPlayerLives(int lives) {
    this.playerLives = lives;
  }

}