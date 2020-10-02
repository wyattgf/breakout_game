package breakout.Display;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */

import breakout.Player;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class HighScoreDisplay extends StatusDisplay {

  private static final int HIGH_SCORE_DISPLAY_YPOS = 290;
  private static final int HIGH_SCORE_LABEL_YPOS = 315;
  private static final String HIGH_SCORE_FILE_LOCATION = "data/highScore.txt";

  private Text currentHighScore;
  private int highScoreFromFile;

  public HighScoreDisplay() {

    super(HIGH_SCORE_DISPLAY_YPOS);
    try {
      File file = new File(HIGH_SCORE_FILE_LOCATION);
      BufferedReader br = new BufferedReader(new FileReader(file));
      highScoreFromFile = Integer.parseInt(br.readLine());
    } catch (Exception e) {
      highScoreFromFile = 0;
    }

  }

  /**
   * Updates the high score depending on the current value of the player score and the highest score
   * found in the high score file, writes the new high score to the file
   *
   * @param root     the root to add the new value to
   * @param myPlayer the current player to extract the data from
   */
  @Override
  public void update(Group root, Player myPlayer) {
    root.getChildren().remove(currentHighScore);
    if (highScoreFromFile < myPlayer.getScore()) {
      try {
        FileWriter highScoreFile = new FileWriter(HIGH_SCORE_FILE_LOCATION);
        highScoreFile.write(Integer.toString(myPlayer.getScore()));
        highScoreFile.close();
      } catch (Exception e) {
        //do nothing
      }
      currentHighScore = newText(HIGH_SCORE_LABEL_YPOS, myPlayer.getScore());
    } else {
      currentHighScore = newText(HIGH_SCORE_LABEL_YPOS, highScoreFromFile);
    }
    root.getChildren().add(currentHighScore);
  }

  /**
   * @return the high score from the file
   */
  public int getCurrentHighScore() {
    return highScoreFromFile;
  }

}