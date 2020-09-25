package breakout;


import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LevelDisplay extends StatusDisplay{

  private static final int LEVEL_DISPLAY_YPOS =  80;
  private static final int LEVEL_LABEL_YPOS =  105;

  private Text currentLevel;

  public LevelDisplay(){
    super(LEVEL_DISPLAY_YPOS);
  }

  @Override
  public void update(Group root, Player myPlayer){
    root.getChildren().remove(currentLevel);
    currentLevel = newText(LEVEL_LABEL_YPOS, 1);
    root.getChildren().add(currentLevel);
  }

}