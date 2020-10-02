package breakout.Display;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */

import breakout.Player;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class LevelDisplay extends StatusDisplay {

  private static final int LEVEL_DISPLAY_YPOS = 80;
  private static final int LEVEL_LABEL_YPOS = 105;

  private Text currentLevel;

  public LevelDisplay() {
    super(LEVEL_DISPLAY_YPOS);
  }

  /**
   * Updates the Level Display depending on the current level of the player
   *
   * @param root     the root to add the new value to
   * @param myPlayer the current player to extract the data from
   */
  @Override
  public void update(Group root, Player myPlayer) {
    root.getChildren().remove(currentLevel);
    currentLevel = newText(LEVEL_LABEL_YPOS, myPlayer.getCurrentLevel());
    root.getChildren().add(currentLevel);
  }

}