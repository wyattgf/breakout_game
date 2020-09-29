package breakout;

import breakout.Level.LevelManager;
import breakout.Level.LevelThree;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class LevelThreeTest extends DukeApplicationTest {

  // create an instance of our game to be able to call in tests (like step())
  private final Game myGame = new Game();
  private final int LEVEL_THREE = 3;
  private static final Paint BACKGROUND = Color.AZURE;
  private static final int SCREEN_WIDTH = 400;
  private static final int SCORE_BOARD_WIDTH = 200;
  private static final int SCREEN_HEIGHT = 400;
  private static final String LEVEL_THREE_TEXT_FILE = "levelThree.txt";
  private static final int NUMBER_OF_BLOCKS_IN_LEVEL = 30;
  // keep created scene to allow mouse and keyboard events
  private Scene myScene;
  private LevelManager levelManager;
  private LevelThree levelThree;

  /**
   * Start special test version of application that does not animate on its own before each test.
   * <p>
   * Automatically called @BeforeEach by TestFX.
   */
  @Override
  public void start(Stage stage) {
    myScene = myGame.setupScene(SCREEN_WIDTH + SCORE_BOARD_WIDTH, SCREEN_HEIGHT, BACKGROUND);
    stage.setScene(myScene);
    stage.show();
    myGame.getLevelManager().setForTesting();
    levelManager = myGame.getLevelManager();
    press(myScene, KeyCode.DIGIT3);
    levelThree = (LevelThree) levelManager.getLevelForTesting(LEVEL_THREE);
  }

  @Test
  public void testProperLevelThreeTextFile() {
    String textFile = levelManager.getLevelForTesting(LEVEL_THREE).getTextFileForTesting();
    assertEquals(LEVEL_THREE_TEXT_FILE, textFile);
  }

  @Test
  public void testLevelSwitchCheatKey() {
    press(myScene, KeyCode.DIGIT0);
    press(myScene, KeyCode.DIGIT3);
    assertEquals(LEVEL_THREE, myGame.getLevelManager().getCurrentLevelNumberForTesting());
  }

  @Test
  public void checkALLBlocksInitialized() {
    int numberOfBlocksRead = levelThree.getBlocks().size();
    assertEquals(NUMBER_OF_BLOCKS_IN_LEVEL, numberOfBlocksRead);
  }

}