package breakout.Display;

import breakout.Player;
import javafx.scene.text.Text;

import javafx.scene.Group;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class ScoreDisplay extends StatusDisplay {

  private static final int SCORE_DISPLAY_YPOS = 220;
  private static final int SCORE_LABEL_YPOS = 245;

  private Text currentScore;

  public ScoreDisplay() {
    super(SCORE_DISPLAY_YPOS);
  }

  /**
   * Updates the score on the screen depending on the current score of the player
   *
   * @param root     the root to add the new value to
   * @param myPlayer the current player to extract the data from
   */
  @Override
  public void update(Group root, Player myPlayer) {
    root.getChildren().remove(currentScore);
    currentScore = newText(SCORE_LABEL_YPOS, myPlayer.getScore());
    root.getChildren().add(currentScore);
  }
}