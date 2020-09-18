package breakout;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Player {
  //instance variables
  private int playerScore;
  private int playerLives;

  public Player() {
    playerScore = 0;
    playerLives = 3;
  }

  /**
   * 
   * @return the current score of the player
   */
  public int getScore(){
    return this.playerScore;
  }

  /**
   * 
   * @return player's current number of lives 
   */
  public int getLives(){
    return this.playerLives;
  }

  /**
   * 
   * @param score the new score for the player
   */
  public void setPlayerScore(int score){
    this.playerScore = score;
  }

  /**
   *
   * @param lives the new number of lives for the player
   */
  public void setPlayerLives(int lives){
    this.playerLives = lives;
  }

}