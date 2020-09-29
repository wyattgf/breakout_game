package breakout.Display;

import breakout.Player;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


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

  public abstract void update(Group root, Player myPlayer);

  public Text newText(double yPos, int displayText) {
    return newDisplay(STATUS_TEXT_XPOS, yPos, Integer.toString(displayText));
  }

  public Text newDisplay(double xPos, double yPos, String displayText) {
    Text statusField = new Text(xPos, yPos, displayText);
    statusField.setFont(Font.font(FONT_TYPE_VERDANA, FontWeight.BOLD, FONT_SIZE));
    return statusField;
  }
}
