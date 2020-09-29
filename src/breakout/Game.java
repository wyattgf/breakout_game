package breakout;

import breakout.Display.ScoreBoard;
import breakout.Level.LevelManager;
import breakout.PowerUp.PowerUpManager;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Game extends Application {

  private static final String TITLE = "Breakout JavaFX";
  private static final String GAME_LOST = "You Lose!";
  private static final String GAME_WON = "You Win!";
  private static final String FINAL_SCORE = "\nFinal Score: ";
  private static final String FONT_TYPE_VERDANA = "Verdana";
  private static final int FONT_SIZE = 50;
  private static final int SCREEN_WIDTH = 400;
  private static final int SCORE_BOARD_WIDTH = 200;
  private static final int SCREEN_HEIGHT = 400;
  private static final int FRAMES_PER_SECOND = 60;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static final Paint BACKGROUND = Color.AZURE;
  private static final int PADDLE_SPEED_CHANGE = 10;
  private static final int LEVEL_THREE = 3;
  private static final int LEVEL_TWO = 2;
  private static final int LEVEL_ONE = 1;
  private static final int TESTING_LEVEL = 0;

  private Paddle currentPaddle;
  private List<Paddle> myPaddles;
  private Ball currentBall;
  private Player currentPlayer;
  private Group root;
  private ScoreBoard currentScoreBoard;
  private List<Ball> myBalls;
  private Timeline animation;
  private PowerUpManager powerUpManager;
  private LevelManager levelManager;
  private boolean paused;


  /**
   * Initialize what will be displayed and how it will be updated.
   */
  @Override
  public void start(Stage stage) {
    Scene myScene = setupScene(SCREEN_WIDTH + SCORE_BOARD_WIDTH, SCREEN_HEIGHT, BACKGROUND);
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
    paused = false;
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  Scene setupScene(int width, int height, Paint background) {
    root = new Group();
    createGameComponents();
    root.getChildren().add(currentPaddle);
    root.getChildren().add(currentBall);
    root.getChildren().add(currentPlayer);
    root.getChildren().add(currentScoreBoard);
    currentScoreBoard.addDisplaysToRoot(root);
    Scene scene = new Scene(root, width, height, background);
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    scene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
    return scene;
  }

  private void createGameComponents() {
    currentScoreBoard = new ScoreBoard();
    createPlayer();
    createPaddle();
    currentPaddle = myPaddles.get(0);
    createBall();
    powerUpManager = new PowerUpManager(root, myPaddles, myBalls, currentPlayer, SCREEN_HEIGHT);
    levelManager = new LevelManager(root, myPaddles, myBalls, currentPlayer, powerUpManager,
        SCREEN_WIDTH, SCREEN_HEIGHT);
    currentBall = myBalls.get(0);
  }

  private void createPlayer() {
    currentPlayer = new Player();
    currentPlayer.setId("player");
  }

  public void createBall() {
    if (myBalls == null) {
      myBalls = new ArrayList<>();
    }
    Ball b = new Ball(SCREEN_WIDTH, SCREEN_HEIGHT, currentPaddle, currentPlayer);
    b.setId("ball" + myBalls.size());
    myBalls.add(b);
  }

  private void createPaddle() {
    if (myPaddles == null) {
      myPaddles = new ArrayList<>();
    }
    Paddle p = new Paddle(SCREEN_WIDTH, SCREEN_HEIGHT);
    p.setId("paddle" + myPaddles.size());
    myPaddles.add(p);
  }


  void step(double elapsedTime) {
    currentPaddle.movePaddle(elapsedTime);
    levelManager.levelFunctionality(elapsedTime);
    levelManager.controlMovingBlocks(elapsedTime);
    powerUpManager.updatePowerUps(elapsedTime);
    powerUpManager.handlePowerUpPaddleCollision();
    levelManager.determineBallCollision();
    currentBall.controlBall(elapsedTime);
    currentScoreBoard.updateScoreBoard(root, currentPlayer);
    endGame();
  }

  private void handleKeyInput(KeyCode code) {
    switch (code) {
      case LEFT:
        currentPaddle.moveLeft();
        break;
      case RIGHT:
        currentPaddle.moveRight();
        break;
      case R:
        resetPositions();
        break;
      case SPACE:
        pauseGame();
        break;
      case L:
        currentPlayer.addLife();
        break;
      case P:
        powerUpManager.createPowerUp(SCREEN_WIDTH / 2.0, SCREEN_HEIGHT / 2.0);
        break;
      case W:
        currentPaddle.setWidthFromDelta();
        break;
      case D:
        levelManager.removeFirst();
        break;
      case F:
        freezeGame();
        break;
      case T:
        currentPaddle.teleportPaddle();
        break;
      case UP:
        currentPaddle.incrementPaddleSpeed(PADDLE_SPEED_CHANGE);
        break;
      case DOWN:
        currentPaddle.incrementPaddleSpeed(-PADDLE_SPEED_CHANGE);
        break;
      case DIGIT1:
        levelManager.setLevel(LEVEL_ONE);
        break;
      case DIGIT2:
        levelManager.setLevel(LEVEL_TWO);
        break;
      case DIGIT3:
        levelManager.setLevel(LEVEL_THREE);
        break;
      case DIGIT0:
        levelManager.setLevel(TESTING_LEVEL);
        break;
    }
  }

  private void handleKeyRelease(KeyCode code) {
    if (code == KeyCode.RIGHT || code == KeyCode.LEFT) {
      currentPaddle.setSpeed(0);
    }
  }

  private void pauseGame() {
    if (!paused) {
      animation.pause();
    } else {
      animation.play();
    }
    paused = !paused;
  }

  private void freezeGame() {
    currentBall.controlFreeze();
    powerUpManager.controlFreeze();
    levelManager.freezeBlocks();
  }

  private void resetPositions() {
    currentPaddle.moveToStartingPosition();
    currentBall.moveToCenter();
    powerUpManager.resetPositions();
  }

  private void endGame() {
    if (currentPlayer.livesLeft() <= 0 || (levelManager.getCurrentBlocks().size() == 0
        && levelManager.getNumberOfLevels() <= levelManager.currentLevel())) {
      animation.stop();
      root.getChildren().clear();
      Text endGameMessage = endScreen();
      endGameMessage.setFont(Font.font(FONT_TYPE_VERDANA, FontWeight.BOLD, FONT_SIZE));
      root.getChildren().add(endGameMessage);
    }
  }

  private Text endScreen() {
    String gameResult;
    if (currentPlayer.livesLeft() <= 0) {
      gameResult = GAME_LOST;
    } else {
      gameResult = GAME_WON;
    }
    return new Text(0, SCREEN_HEIGHT/2.0, gameResult + FINAL_SCORE + currentPlayer.getScore());
  }

  public PowerUpManager getPowerUpManager() {
    return powerUpManager;
  }

  public LevelManager getLevelManager() {
    return levelManager;
  }

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
