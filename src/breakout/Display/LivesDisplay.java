package breakout.Display;

import breakout.Player;
import javafx.scene.Group;
import javafx.scene.text.Text;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class LivesDisplay extends StatusDisplay {

  private static final int LIVES_DISPLAY_YPOS = 150;
  private static final int LIVES_LABEL_YPOS = 175;

  private Text livesRemaining;


  public LivesDisplay() {
    super(LIVES_DISPLAY_YPOS);
  }

  /**
   * Updates the LivesDisplay depending on the current number of lives of the player
   *
   * @param root     the root to add the new value to
   * @param myPlayer the current player to extract the data from
   */
  @Override
  public void update(Group root, Player myPlayer) {
    root.getChildren().remove(livesRemaining);
    livesRemaining = newText(LIVES_LABEL_YPOS, myPlayer.livesLeft());
    root.getChildren().add(livesRemaining);
  }

}