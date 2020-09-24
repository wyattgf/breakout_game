package breakout;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest extends DukeApplicationTest {

  // create an instance of our game to be able to call in tests (like step())
  private final Game myGame = new Game();
  private final int INITIAL_BALL_SPEED = 100;
  private final int BALL_RADIUS = 5;
  private final int INITIAL_PADDLE_WIDTH = 75;
  private final int SCORE_ADDITION_PER_BLOCK = 200;
  private static final int INITIAL_PADDLE_SPEED = 175;
  private static final Paint BACKGROUND = Color.AZURE;
  private static final int FRAMES_PER_SECOND = 60;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final int SCREEN_WIDTH = 400;
  public static final int SCREEN_HEIGHT = 400;
  private static final int POWER_UP_PADDLE_DELTA = 10;
  // keep created scene to allow mouse and keyboard events
  private Scene myScene;
  private Paddle myPaddle;
  private Player myPlayer;
  private Ball myBall;
  private Block block1, block5, blockDestroyed;

  /**
   * Start special test version of application that does not animate on its own before each test.
   * <p>
   * Automatically called @BeforeEach by TestFX.
   */
  @Override
  public void start(Stage stage) {
    myScene = myGame.setupScene(SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND);
    stage.setScene(myScene);
    stage.show();

    myPaddle = lookup("#paddle0").query();
    myPlayer = lookup("#player").query();
    myBall = lookup("#ball0").query();
    block1 = lookup("#block1").query();
    block5 = lookup("#block5").query();
    blockDestroyed = lookup("#block22").query();
  }

  @Test
  public void testInitialBallProperties() {
    assertEquals(SCREEN_WIDTH / 2.0, myBall.getCenterX());
    assertEquals(SCREEN_WIDTH / 2.0, myBall.getCenterY());
    assertEquals(BALL_RADIUS, myBall.getRadius());
    assertEquals(INITIAL_BALL_SPEED, myBall.getSpeed());
  }

  @Test
  public void testInitialPaddlePositionAndSize() {
    assertEquals(SCREEN_WIDTH / 2.0 - myPaddle.getWidth() / 2.0, myPaddle.getX());
    assertEquals(SCREEN_HEIGHT - (2 * myPaddle.getHeight()), myPaddle.getY());
    assertEquals(INITIAL_PADDLE_WIDTH, myPaddle.getWidth());
  }

  @Test
  public void testPaddleMovementRight() {
    myPaddle.setX(200);
    myPaddle.setY(200);

    press(myScene, KeyCode.RIGHT);
    javafxRun(() -> myGame.step(SECOND_DELAY));

    assertEquals(200 + (INITIAL_PADDLE_SPEED * SECOND_DELAY), myPaddle.getX());
    assertEquals(200, myPaddle.getY());
  }

  @Test
  public void testPaddleMovementLeft() {
    myPaddle.setX(200);
    myPaddle.setY(200);

    press(myScene, KeyCode.LEFT);
    javafxRun(() -> myGame.step(SECOND_DELAY));

    assertEquals(200 - (INITIAL_PADDLE_SPEED * SECOND_DELAY), myPaddle.getX());
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
  public void testBallBounceOffBlock() {
    myBall.setMyXDirection(0);
    for(int i = 0; i<20; i++){
      javafxRun(() -> myGame.step(SECOND_DELAY));
    }
    assertTrue(myBall.getCenterY()>165);
    assertEquals(SCREEN_HEIGHT/2, myBall.getCenterX());

  }

  @Test
  public void testBlockIsDestroyed(){
    myBall.setMyXDirection(0);
    for(int i = 0; i<20; i++){
      javafxRun(() -> myGame.step(SECOND_DELAY));
    }
    assertTrue(!myScene.getRoot().getChildrenUnmodifiable().contains(blockDestroyed));
  }

  @Test
  public void testScoreIsUpdatedWhenBlockDestroyed() {
    myBall.setMyXDirection(0);
    for(int i = 0; i<20; i++){
      javafxRun(() -> myGame.step(SECOND_DELAY));
    }
    assertEquals(SCORE_ADDITION_PER_BLOCK, myPlayer.getScore());
  }

  @Test
  public void testBounceOffWall(){
    myBall.setMyYDirection(0);
    myBall.setCenterX(394);
    for(int i = 0 ; i <4; i++){
      javafxRun(() -> myGame.step(SECOND_DELAY));
    }
    assertTrue(myBall.getCenterX() < SCREEN_WIDTH);
  }

  @Test
  public void testBallBounceOffCorner() {
    myBall.setCenterX(395);
    myBall.setCenterY(5);
    javafxRun(() -> myGame.step(SECOND_DELAY));
    assertEquals(395 - (INITIAL_BALL_SPEED * SECOND_DELAY), myBall.getCenterX());
    assertEquals(5 + (INITIAL_BALL_SPEED * SECOND_DELAY), myBall.getCenterY());
  }

  @Test
  public void testResetCheatKey() {
    myBall.setCenterX(395);
    myBall.setCenterY(5);
    myPaddle.setX(0);
    myPaddle.setY(0);
    press(myScene, KeyCode.R);
    assertEquals(SCREEN_WIDTH / 2.0, myBall.getCenterX());
    assertEquals(SCREEN_WIDTH / 2.0, myBall.getCenterY());
    assertEquals(SCREEN_WIDTH / 2.0 - myPaddle.getWidth() / 2.0, myPaddle.getX());
    assertEquals(SCREEN_HEIGHT - (2 * myPaddle.getHeight()), myPaddle.getY());
  }

  @Test
  public void testFreezeCheatKey() {
    javafxRun(() -> myGame.step(SECOND_DELAY));
    press(myScene, KeyCode.F);
    assertEquals(200 + (INITIAL_BALL_SPEED * SECOND_DELAY), myBall.getCenterX());
    assertEquals(200 - (INITIAL_BALL_SPEED * SECOND_DELAY), myBall.getCenterY());
    javafxRun(() -> myGame.step(SECOND_DELAY));
    assertEquals(200 + (INITIAL_BALL_SPEED * SECOND_DELAY), myBall.getCenterX());
    assertEquals(200 - (INITIAL_BALL_SPEED * SECOND_DELAY), myBall.getCenterY());
  }

  @Test
  public void testBallBouncesOffPaddle() {
    myBall.setCenterY(SCREEN_HEIGHT - 16);
    myBall.setMyXDirection(0);
    myBall.setMyYDirection(1);
    for(int i = 0; i<4; i++){
      javafxRun(() -> myGame.step(SECOND_DELAY));
    }
    assertEquals(SCREEN_WIDTH / 2, myBall.getCenterX());
    assertEquals((int) (SCREEN_HEIGHT - 16 + (INITIAL_BALL_SPEED * SECOND_DELAY) -
            (INITIAL_BALL_SPEED * 3 * SECOND_DELAY)), (int) myBall.getCenterY());
  }

  @Test
  public void testBallGoesToCenterAfterLeavingScreen() {
    myBall.setCenterY(500);
    javafxRun(() -> myGame.step(SECOND_DELAY));
    assertEquals(200, myBall.getCenterX());
    assertEquals(200, myBall.getCenterY());
  }

  @Test
  public void testPlayerLosesLifeAfterMissingBall() {
    int expected = myPlayer.livesLeft() -1;
    myBall.setCenterY(395);
    for(int i = 0; i< 20; i++){
      javafxRun(() -> myGame.step(SECOND_DELAY));
    }
    assertEquals(expected, myPlayer.livesLeft());
  }


  @Test
  public void testAddLivesPowerUp() {
    double xPos = myPaddle.getX();
    double yPos = myPaddle.getY();
    int expectedLives = myPlayer.livesLeft() + 1;
    PowerUp p = new PowerUpLife(xPos, yPos, myGame.getPowerUpManager());
    p.activatePowerUp();
    assertEquals(expectedLives, myPlayer.livesLeft());
  }

  @Test
  public void testIncreasePaddleSizePowerUp() {
    double xPos = myPaddle.getX();
    double yPos = myPaddle.getY();
    double expectedWidth = myPaddle.getWidth() + POWER_UP_PADDLE_DELTA;
    PowerUp p = new PowerUpPaddleSize(xPos, yPos, myGame.getPowerUpManager());
    p.activatePowerUp();
    assertEquals(expectedWidth, myPaddle.getWidth());
  }

  @Test
  public void testPowerUpCheatKey() {
    List<PowerUp> currentPowerUps = myGame.getPowerUpManager().getCurrentPowerUps();
    int expectedPowerUpCount = currentPowerUps.size() + 1;
    press(myScene, KeyCode.P);
    assertEquals(expectedPowerUpCount, currentPowerUps.size());

  }

  @Test
  public void testPowerUpFreeze() {
    List<PowerUp> currentPowerUps = myGame.getPowerUpManager().getCurrentPowerUps();
    press(myScene, KeyCode.P);
    double expectedPos = currentPowerUps.get(0).getCenterY();
    press(myScene, KeyCode.F);
    javafxRun(() -> myGame.step(SECOND_DELAY));
    double actualPos = currentPowerUps.get(0).getCenterY();
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