package breakout;

import java.util.concurrent.TimeUnit;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class GameTest extends DukeApplicationTest {

  // create an instance of our game to be able to call in tests (like step())
  private final Game myGame = new Game();
  private final int INITIAL_BALL_SPEED = 100;
  private final int BALL_RADIUS = 5;
  private final int INITIAL_PADDLE_WIDTH = 75;
  private static final int INITIAL_PADDLE_SPEED = 175;
  public static final int SCREEN_WIDTH = 400;
  public static final int SCREEN_HEIGHT = 400;
  private static final int POWER_UP_PADDLE_DELTA = 10;
  // keep created scene to allow mouse and keyboard events
  private Scene myScene;
  private Paddle myPaddle;
  private Player myPlayer;
  private Ball myBall;
  private Block block1, block5;

  /**
   * Start special test version of application that does not animate on its own before each test.
   * <p>
   * Automatically called @BeforeEach by TestFX.
   */
  @Override
  public void start(Stage stage) {
    myScene = myGame.setupScene(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, Game.BACKGROUND);
    stage.setScene(myScene);
    stage.show();

    myPaddle = lookup("#paddle").query();
    myPlayer = lookup("#player").query();
    myBall = lookup("#ball").query();
    block1 = lookup("#block1").query();
    block5 = lookup("#block5").query();
  }

  @Test
  public void testInitialBallProperties() {
    assertEquals(Game.SCREEN_WIDTH / 2.0, myBall.getCenterX());
    assertEquals(Game.SCREEN_WIDTH / 2.0, myBall.getCenterY());
    assertEquals(BALL_RADIUS, myBall.getRadius());
    assertEquals(INITIAL_BALL_SPEED, myBall.getSpeed());
  }

  @Test
  public void testInitialPaddlePositionAndSize() {
    assertEquals(Game.SCREEN_WIDTH / 2.0 - myPaddle.getWidth() / 2.0, myPaddle.getX());
    assertEquals(Game.SCREEN_HEIGHT - (2 * myPaddle.getHeight()), myPaddle.getY());
    assertEquals(INITIAL_PADDLE_WIDTH, myPaddle.getWidth());
  }

  @Test
  public void testPaddleMovementRight() {
    myPaddle.setX(200);
    myPaddle.setY(200);

    press(myScene, KeyCode.RIGHT);
    javafxRun(() -> myGame.step(Game.SECOND_DELAY));

    assertEquals(200 + (INITIAL_PADDLE_SPEED * Game.SECOND_DELAY), myPaddle.getX());
    assertEquals(200, myPaddle.getY());
  }

  @Test
  public void testPaddleMovementLeft() {
    myPaddle.setX(200);
    myPaddle.setY(200);

    press(myScene, KeyCode.LEFT);
    javafxRun(() -> myGame.step(Game.SECOND_DELAY));

    assertEquals(200 - (INITIAL_PADDLE_SPEED * Game.SECOND_DELAY), myPaddle.getX());
    assertEquals(200, myPaddle.getY());
  }

  @Test
  public void testBlockPositionsBasedOnFile() {
    assertEquals(0, block1.getX());
    assertEquals(0, block1.getY());
    assertEquals(0, block5.getX());
    assertEquals(15, block5.getY());
  }

  @Test
  public void testBallBounceOffCorner() {
    myBall.setCenterX(395);
    myBall.setCenterY(5);
    javafxRun(() -> myGame.step(Game.SECOND_DELAY));
    assertEquals(395 - (INITIAL_BALL_SPEED * Game.SECOND_DELAY), myBall.getCenterX());
    assertEquals(5 + (INITIAL_BALL_SPEED * Game.SECOND_DELAY), myBall.getCenterY());
  }

  @Test
  public void testResetCheatKey() {
    myBall.setCenterX(395);
    myBall.setCenterY(5);
    myPaddle.setX(0);
    myPaddle.setY(0);
    press(myScene, KeyCode.R);
    assertEquals(Game.SCREEN_WIDTH / 2.0, myBall.getCenterX());
    assertEquals(Game.SCREEN_WIDTH / 2.0, myBall.getCenterY());
    assertEquals(Game.SCREEN_WIDTH / 2.0 - myPaddle.getWidth() / 2.0, myPaddle.getX());
    assertEquals(Game.SCREEN_HEIGHT - (2 * myPaddle.getHeight()), myPaddle.getY());
  }

  @Test
  public void testPauseCheatKey() {
    javafxRun(() -> myGame.step(Game.SECOND_DELAY));
    press(myScene, KeyCode.SPACE);
    assertEquals(200 + (INITIAL_BALL_SPEED * Game.SECOND_DELAY), myBall.getCenterX());
    assertEquals(200 - (INITIAL_BALL_SPEED * Game.SECOND_DELAY), myBall.getCenterY());
    javafxRun(() -> myGame.step(Game.SECOND_DELAY));
    assertEquals(200 + (INITIAL_BALL_SPEED * Game.SECOND_DELAY), myBall.getCenterX());
    assertEquals(200 - (INITIAL_BALL_SPEED * Game.SECOND_DELAY), myBall.getCenterY());
  }

  @Test
  public void testBallBouncesOffPaddle() {
    myBall.setCenterY(Game.SCREEN_HEIGHT - 36);
    myBall.setMyXDirection(0);
    myBall.setMyYDirection(1);
    javafxRun(() -> myGame.step(Game.SECOND_DELAY));
    assertEquals(Game.SCREEN_WIDTH / 2, myBall.getCenterX());
    assertEquals(Game.SCREEN_HEIGHT - 36 + (INITIAL_BALL_SPEED * Game.SECOND_DELAY),
        myBall.getCenterY());
  }

  @Test
  public void testBallGoesToCenterAfterLeavingScreen() {
    myBall.setCenterY(500);
    javafxRun(() -> myGame.step(Game.SECOND_DELAY));
    assertEquals(200 + (INITIAL_BALL_SPEED * Game.SECOND_DELAY), myBall.getCenterX());
    assertEquals(200 - (INITIAL_BALL_SPEED * Game.SECOND_DELAY), myBall.getCenterY());
  }

  @Test
  public void testPlayerLosesLifeAfterMissingBall() {
    myBall.setCenterY(405);
    javafxRun(() -> myGame.step(Game.SECOND_DELAY));
    assertEquals(1, myBall.livesLost());
  }

  @Test
  public void testAddLivesPowerUp() {
    Group root = new Group();
    double xPos = myPaddle.getX();
    double yPos = myPaddle.getY();
    int expectedLives = myPlayer.getLives() + 1;
    PowerUp p = new PowerUp(xPos, yPos, root, SCREEN_HEIGHT, myPlayer, myPaddle);
    p.manuallySetType(1);
    p.activatePowerUp();
    assertEquals(expectedLives, myPlayer.getLives());
  }

  @Test
  public void testIncreasePaddleSizePowerUp() {
    Group root = new Group();
    double xPos = myPaddle.getX();
    double yPos = myPaddle.getY();
    double expectedWidth = myPaddle.getWidth() + POWER_UP_PADDLE_DELTA;
    PowerUp p = new PowerUp(xPos, yPos, root, SCREEN_HEIGHT, myPlayer, myPaddle);
    p.manuallySetType(0);
    p.activatePowerUp();
    assertEquals(expectedWidth, myPaddle.getWidth());
  }

  @Test
  public void testPowerUpCheatKey() {
    int expectedPowerUpCount = myGame.getCurrentPowerUps().size() + 1;
    press(myScene, KeyCode.P);
    assertEquals(expectedPowerUpCount, myGame.getCurrentPowerUps().size());

  }

  @Test
  public void testPowerUpPause() {
    press(myScene, KeyCode.P);
    double expectedPos = myGame.getCurrentPowerUps().get(0).getCenterY();
    press(myScene, KeyCode.SPACE);
    javafxRun(() -> myGame.step(Game.SECOND_DELAY));
    double actualPos = myGame.getCurrentPowerUps().get(0).getCenterY();
    assertEquals(expectedPos, actualPos);

  }

  @Test
  public void testIncreasePaddleSizeCheatKey() {
    double expectedWidth = myPaddle.getWidth() + POWER_UP_PADDLE_DELTA;
    press(myScene, KeyCode.W);
    assertEquals(expectedWidth, myPaddle.getWidth());
  }

  @Test
  public void testPaddleSizeUponReset() {
    double expectedWidth = myPaddle.getWidth();
    press(myScene, KeyCode.W);
    press(myScene, KeyCode.W);
    press(myScene, KeyCode.R);
    assertEquals(expectedWidth, myPaddle.getWidth());
  }
}