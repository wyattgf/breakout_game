package breakout.Display;

import breakout.Player;
import javafx.scene.Group;
import javafx.scene.text.Text;


public class LivesDisplay extends StatusDisplay {

  private static final int LIVES_DISPLAY_YPOS = 150;
  private static final int LIVES_LABEL_YPOS = 175;

  private Text livesRemaining;


  public LivesDisplay() {
    super(LIVES_DISPLAY_YPOS);
  }

  @Override
  public void update(Group root, Player myPlayer) {
    root.getChildren().remove(livesRemaining);
    livesRemaining = newText(LIVES_LABEL_YPOS, myPlayer.livesLeft());
    root.getChildren().add(livesRemaining);
  }

}