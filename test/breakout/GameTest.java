package breakout;

import breakout.Block.Block;
import breakout.Block.PowerUpBlock;
import breakout.Display.HighScoreDisplay;
import breakout.Display.ScoreBoard;
import breakout.PowerUp.PowerUp;
import breakout.PowerUp.PowerUpFireBall;
import breakout.PowerUp.PowerUpLife;
import breakout.PowerUp.PowerUpPaddleSize;
import breakout.PowerUp.PowerUpPaddleSpeed;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

  private final Game myGame = new Game();
  private final int INITIAL_BALL_SPEED = 100;
  private final int BALL_RADIUS = 5;
  private final int INITIAL_PADDLE_WIDTH = 75;
  private final int SCORE_ADDITION_PER_BLOCK = 200;
  private static final int INITIAL_PADDLE_SPEED = 175;
  private static final Paint BACKGROUND = Color.AZURE;
  private static final int FRAMES_PER_SECOND = 60;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static final String HIGH_SCORE_FILE_LOCATION = "data/highScore.txt";
  private static final int SCREEN_WIDTH = 400;
  private static final int SCORE_BOARD_WIDTH = 200;
  private static final int SCREEN_HEIGHT = 400;
  private static final int POWER_UP_WIDTH_DELTA = 10;
  private static final int POWER_UP_SPEED_DELTA = 10;

  private Scene myScene;
  private Paddle myPaddle;
  private Player myPlayer;
  private HighScoreDisplay myHighScore;
  private Ball myBall;
  private Block block1, block5, blockDestroyed, movingBlock;

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
    myHighScore = new HighScoreDisplay();

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
    assertEquals(SCREEN_HEIGHT/2.0, myBall.getCenterX());

  }

  @Test
  public void testBlockIsDestroyed(){
    myBall.setMyXDirection(0);
    for(int i = 0; i<20; i++){
      javafxRun(() -> myGame.step(SECOND_DELAY));
    }
    assertFalse(myScene.getRoot().getChildrenUnmodifiable().contains(blockDestroyed));
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
    assertEquals(SCREEN_WIDTH / 2.0, myBall.getCenterX());
    assertEquals((int) (SCREEN_HEIGHT - 16 + (INITIAL_BALL_SPEED * SECOND_DELAY) -
            (INITIAL_BALL_SPEED * 3 * SECOND_DELAY)), (int) myBall.getCenterY());
  }

  @Test
  public void testBallGoesToCenterAfterLeavingScreen() {
    myBall.setCenterY(500);
    javafxRun(() -> myGame.step(SECOND_DELAY));
    assertEquals(200 + (INITIAL_BALL_SPEED * SECOND_DELAY), myBall.getCenterX());
    assertEquals(200 - (INITIAL_BALL_SPEED * SECOND_DELAY), myBall.getCenterY());
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
    double expectedWidth = myPaddle.getWidth() + POWER_UP_WIDTH_DELTA;
    PowerUp p = new PowerUpPaddleSize(xPos, yPos, myGame.getPowerUpManager());
    p.activatePowerUp();
    assertEquals(expectedWidth, myPaddle.getWidth());
  }

  @Test
  public void testIncreasePaddleSpeedPowerUp() {
    double xPos = myPaddle.getX();
    double yPos = myPaddle.getY();
    double expectedSpeed = myPaddle.getKeyPressSpeed() + POWER_UP_SPEED_DELTA;
    PowerUp p = new PowerUpPaddleSpeed(xPos, yPos, myGame.getPowerUpManager());
    p.activatePowerUp();
    assertEquals(expectedSpeed, myPaddle.getKeyPressSpeed());
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
    double expectedWidth = myPaddle.getWidth() + POWER_UP_WIDTH_DELTA;
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

  @Test
  public void testFallingBlock() {
    press(myScene,KeyCode.DIGIT2);
    movingBlock = lookup("#block1").query();
    double originalPos = movingBlock.getY();
    movingBlock.changeFallingSpeed(10000);
      javafxRun(() -> myGame.step(SECOND_DELAY));

    assertTrue(originalPos < movingBlock.getY());
  }
  @Test
  public void testPaddleTeleportation(){
    myPaddle.setX(0);
    double expectedX = SCREEN_WIDTH - myPaddle.getWidth();
    press(myScene, KeyCode.T);
    assertEquals(expectedX, myPaddle.getX());

  }

  @Test
  public void testLevelDisplay(){
    press(myScene, KeyCode.DIGIT3);
    int expectedLevel = 3;
    assertEquals(expectedLevel, myPlayer.getCurrentLevel());
  }

  @Test
  public void testLivesDisplay(){
    int expected = myPlayer.livesLeft() -1;
    myBall.setCenterY(415);
    javafxRun(() -> myGame.step(SECOND_DELAY));
    assertEquals(expected, myPlayer.livesLeft());
  }

  @Test
  public void testScoreDisplay(){
    myBall.setMyXDirection(0);
    for(int i = 0; i<20; i++) {
      javafxRun(() -> myGame.step(SECOND_DELAY));
    }
    assertEquals(SCORE_ADDITION_PER_BLOCK, myPlayer.getScore());
  }

  @Test
  public void testHighScoreDisplay() throws Exception{
    File file = new File(HIGH_SCORE_FILE_LOCATION);
    BufferedReader br = new BufferedReader(new FileReader(file));
    assertEquals(myHighScore.getCurrentHighScore(),Integer.parseInt(br.readLine()));
  }

  @Test
  public void testLaserLifeLoss()  {
    press(myScene, KeyCode.DIGIT3);
    double expectedLives = myPlayer.livesLeft() - 1;
    List<LaserBeam> currentLasers = myGame.getLevelManager().getLaserBeamsForTesting();
    while (currentLasers.size() == 0) {
      javafxRun(() -> myGame.step(SECOND_DELAY));
    }
    LaserBeam laser = currentLasers.get(0);
    myPaddle.setX(laser.getX());
    myPaddle.setY(laser.getY()+1);
    javafxRun(() -> myGame.step(SECOND_DELAY));
    assertEquals(expectedLives, myPlayer.livesLeft());
    myPlayer.addLife();

  }

  @Test
  public void testLevelSwitchCheatKeyForTestLevel() {
    press(myScene, KeyCode.DIGIT0);
    assertEquals(0, myGame.getLevelManager().getCurrentLevelNumberForTesting());
  }
  @Test
  public void testFieryBallPowerUpChangesBallDesign(){
    PowerUp p = new PowerUpFireBall(0,0,myGame.getPowerUpManager());
    assertEquals(myBall.getFill(), Color.BLACK);
    p.activatePowerUp();
    assertNotEquals(myBall.getFill(), Color.BLACK);
  }

  //for this test to pass, change the SUN_FILE_LOCATION constant in the Ball class to an invalid
  //file name
  /*@Test
  public void testBallRemainsBlackWithInvalidFileImage(){
    PowerUp p = new PowerUpFireBall(0,0,myGame.getPowerUpManager());
    p.activatePowerUp();
    assertEquals(Color.BLACK,myBall.getFill());
  }
   */

  /*
  //for this test, change the POWER_UP_IMAGE file location in each PowerUp subclass to an invalid
  //file name for the power ups to become black
  @Test
  public void testPowerUpsTurnBlackWithInvalidFileImage(){
    PowerUp fireBall = new PowerUpFireBall(0,0, myGame.getPowerUpManager());
    fireBall.activatePowerUp();
    assertEquals(Color.BLACK, fireBall.getFill());
    PowerUp extraLife = new PowerUpFireBall(0,0, myGame.getPowerUpManager());
    extraLife.activatePowerUp();
    assertEquals(Color.BLACK, extraLife.getFill());
    PowerUp widerPaddle = new PowerUpFireBall(0,0, myGame.getPowerUpManager());
    widerPaddle.activatePowerUp();
    assertEquals(Color.BLACK, widerPaddle.getFill());
    PowerUp speedyPaddle = new PowerUpFireBall(0,0, myGame.getPowerUpManager());
    speedyPaddle.activatePowerUp();
    assertEquals(Color.BLACK, speedyPaddle.getFill());
  }

   */

  //for this test, change the POWER_UP_BACKGROUND file location in the PowerUpBlock class to
  //an invalid file name
  /*@Test
  public void testPowerUpBlockIsRedWithInvalidImageFile(){
    Block b = new PowerUpBlock(0,0);
    assertEquals(Color.RED,b.getFill());
  }
   */

  //for this test, change the file name in the ScoreBoard class to an invalid file
  //and the error is handled by the scoreboard turning purple
  /*
  @Test
  public void testScoreBoardIsPurpleWithInvalidImageFile(){
    ScoreBoard board = new ScoreBoard();
    assertEquals(Color.PURPLE,board.getFill());
  }
   */

  //for this test to pass, change the filename in LaserBeam to an invalid file name
  /*
  @Test
  public void testLaserBeamIsRedBlockWithInvalidImageFile(){
    LaserBeam beam = new LaserBeam(0,0);
    assertEquals(Color.RED,beam.getFill());
  }
   */

  //for this test to pass, change the filename in the LevelOne class to an incorrect file and the
  //error is handled by skipping that level
  /*
  @Test
  public void testGameSkipsLevelsWithInvalidBlockFiles(){
    //if level 1 has invalid file
    int expectedLevel = 2;
    press(myScene,KeyCode.DIGIT1);
    javafxRun(() -> myGame.step(SECOND_DELAY));
    assertEquals(expectedLevel,myPlayer.getCurrentLevel());
  }
   */
}