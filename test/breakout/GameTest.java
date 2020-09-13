package breakout;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class GameTest extends DukeApplicationTest{
  // create an instance of our game to be able to call in tests (like step())
  private final Game myGame = new Game();
  // keep created scene to allow mouse and keyboard events
  private Scene myScene;
  private Paddle myPaddle;
  private Ball myBall;
  private Block block1, block5;

  /**
   * Start special test version of application that does not animate on its own before each test.
   *
   * Automatically called @BeforeEach by TestFX.
   */
  @Override
  public void start (Stage stage) {
    myScene = myGame.setupScene(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, Game.BACKGROUND);
    stage.setScene(myScene);
    stage.show();

    myPaddle = lookup("#paddle").query();
    myBall = lookup("#ball").query();
    block1 = lookup("#block1").query();
    block5 = lookup("#block5").query();
  }

  @Test
  public void testInitialBallProperties () {
    assertEquals(Game.SCREEN_WIDTH/2.0, myBall.getCenterX());
    assertEquals(Game.SCREEN_WIDTH/2.0, myBall.getCenterY());
    assertEquals(20, myBall.getRadius());
    assertEquals(25,myBall.getSpeed());
  }

  @Test
  public void testInitialPaddlePositionAndSize () {
    assertEquals(Game.SCREEN_WIDTH/2.0 - myPaddle.getWidth()/2.0 , myPaddle.getX());
    assertEquals(2.0/3 * Game.SCREEN_HEIGHT, myPaddle.getY());
    assertEquals( 150,myPaddle.getWidth());
  }

  @Test
  public void testPaddleMovementRight () {
    myPaddle.setX(200);
    myPaddle.setY(200);

    press(myScene, KeyCode.RIGHT);

    assertEquals(205, myPaddle.getX());
    assertEquals(200, myPaddle.getY());
  }

  @Test
  public void testPaddleMovementLeft () {
    myPaddle.setX(200);
    myPaddle.setY(200);

    press(myScene, KeyCode.LEFT);

    assertEquals(195, myPaddle.getX());
    assertEquals(200, myPaddle.getY());
  }

  @Test
  public void testBlockPositionsBasedOnFile () {
    assertEquals(0, block1.getX());
    assertEquals(0, block1.getY());
    assertEquals(0, block5.getX());
    assertEquals(15, block5.getY());
  }

}