package breakout;


import javafx.scene.Group;
import javafx.scene.text.Text;

public class HighScoreDisplay extends StatusDisplay{

  private static final int HIGH_SCORE_DISPLAY_YPOS =  290;
  private static final int HIGH_SCORE_LABEL_YPOS =  315;

  private Text currentHighScore;


  public HighScoreDisplay(){
    super(HIGH_SCORE_DISPLAY_YPOS);
  }

  @Override
  public void update(Group root, Player myPlayer){
    root.getChildren().remove(currentHighScore);
    currentHighScore = newText(HIGH_SCORE_LABEL_YPOS, myPlayer.getScore());
    root.getChildren().add(currentHighScore);
  }

}