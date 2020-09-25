package breakout;

import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;

import javafx.scene.Group;

public class ScoreDisplay extends StatusDisplay{

  private static final int SCORE_DISPLAY_YPOS =  220;
  private static final int SCORE_LABEL_YPOS =  245;

  private Text currentScore;

  public ScoreDisplay(){
    super(SCORE_DISPLAY_YPOS);
  }

  @Override
  public void update(Group root, Player myPlayer){
    root.getChildren().remove(currentScore);
    currentScore = newText(SCORE_LABEL_YPOS, myPlayer.getScore());
    root.getChildren().add(currentScore);
  }
}