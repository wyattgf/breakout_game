package breakout.Display;

import breakout.Player;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * The abstract StatusDisplay organizes the shared characteristics of each Status Display and allows
 * for extension to develop the many different types of display
 *
 * @author Hosam Tageldin, Wyatt Focht
 */
public abstract class StatusDisplay extends Rectangle {

  private static final double STATUS_DISPLAY_HEIGHT = 35;
  private static final double STATUS_DISPLAY_WIDTH = 100;
  private static final double STATUS_DISPLAY_XPOS = 450;
  private static final double STATUS_TEXT_XPOS = 455;
  private static final String FONT_TYPE_VERDANA = "Verdana";
  private static final int FONT_SIZE = 20;

  public StatusDisplay(int yPos) {
    super(STATUS_DISPLAY_XPOS, yPos, STATUS_DISPLAY_WIDTH, STATUS_DISPLAY_HEIGHT);
    this.setFill(Color.WHITE);
  }

  /**
   * Allow the displayed value to update with the change in time
   *
   * @param root     the root to add the new value to
   * @param myPlayer the current player to extract the data from
   */
  public abstract void update(Group root, Player myPlayer);

  /**
   * Utilizes the newDisplay method to create Text from the parameters and shared instance
   * variables
   *
   * @param yPos        the YPositioning of the text
   * @param displayText the text that is to be displayed
   * @return a Text Object to be displayed on the scoreboard
   */
  public Text newText(double yPos, int displayText) {
    return newDisplay(STATUS_TEXT_XPOS, yPos, Integer.toString(displayText));
  }

  /**
   * Takes in the positions and the text to be displayed at a particular location on the screen
   *
   * @param xPos        the X positioning of the display
   * @param yPos        the Y positioning of the display
   * @param displayText the text to Display
   * @return the Text Object to be displayed on the scoreboard
   */
  public Text newDisplay(double xPos, double yPos, String displayText) {
    Text statusField = new Text(xPos, yPos, displayText);
    statusField.setFont(Font.font(FONT_TYPE_VERDANA, FontWeight.BOLD, FONT_SIZE));
    return statusField;
  }
}
