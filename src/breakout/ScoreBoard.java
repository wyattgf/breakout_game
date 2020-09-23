package breakout;

import javafx.scene.text.Font;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class ScoreBoard {
  private Text statusDisplay;

  public void updateScoreBoard(Group root, Player myPlayer) {
    root.getChildren().remove(statusDisplay);
    statusDisplay = new Text(10, 150, "Lives: " + (myPlayer.livesLeft())
        + "  Score: " + myPlayer.getScore());
    statusDisplay.setFont(new Font(20));
    root.getChildren().add(statusDisplay);
  }
}
