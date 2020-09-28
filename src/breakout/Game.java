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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Game extends Application {

  private static final String TITLE = "Breakout JavaFX";
  private static final String GAME_OVER = "Game is over!\nFinal Score: ";
  private static final int SCREEN_WIDTH = 400;
  private static final int SCORE_BOARD_WIDTH = 200;
  private static final int SCREEN_HEIGHT = 400;
  private static final int FRAMES_PER_SECOND = 60;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static final Paint BACKGROUND = Color.AZURE;
  private static final int PADDLE_SPEED_CHANGE = 10;
  private static final int LEVEL_THREE  = 3;
  private static final int LEVEL_TWO = 2;
  private static final int LEVEL_ONE = 1;

  // some things needed to remember during game
  private List<Paddle> myPaddles;
  private Paddle myPaddle; //refactor name later when with Hosam to currentPaddle, same as below
  private Ball myBall;
  private Player myPlayer;
  private Group root;
  private ScoreBoard myScoreBoard;
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
    // attach scene to the stage and display it
    Scene myScene = setupScene(SCREEN_WIDTH + SCORE_BOARD_WIDTH, SCREEN_HEIGHT, BACKGROUND);
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
    paused = false;
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  // Create the game's "scene": what shapes will be in the game and their starting properties
  Scene setupScene(int width, int height,Paint background) {
    // create one top level collection to organize the things in the scene
    root = new Group();
    myScoreBoard = new ScoreBoard();
    createPlayer();
    createPaddle();
    myPaddle = myPaddles.get(0);
    createBall();
    powerUpManager = new PowerUpManager(root, myPaddles, myBalls, myPlayer, SCREEN_HEIGHT);
    levelManager = new LevelManager(root, myPaddles, myBalls, myPlayer, powerUpManager, SCREEN_WIDTH);
    myBall = myBalls.get(0);




    root.getChildren().add(myPaddle);
    root.getChildren().add(myBall);
    root.getChildren().add(myPlayer);
    root.getChildren().add(myScoreBoard);
    myScoreBoard.addDisplaysToRoot(root);
    // create a place to see the shapes
    Scene scene = new Scene(root, width, height,background);
    // respond to input
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    scene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
    return scene;
  }

  private void createPlayer() {
    myPlayer = new Player();
    myPlayer.setId("player");
  }

  private void createBall() {
    if (myBalls == null){
      myBalls = new ArrayList<>();
    }
    Ball b = new Ball(SCREEN_WIDTH, SCREEN_HEIGHT,root,myPaddle,myPlayer, powerUpManager);
    b.setId("ball" + myBalls.size());
    myBalls.add(b);
  }

  private void createPaddle() {
    if (myPaddles == null){
      myPaddles = new ArrayList<>();
    }
    Paddle p = new Paddle(SCREEN_WIDTH, SCREEN_HEIGHT);
    p.setId("paddle" + myPaddles.size());
    myPaddles.add(p);
  }

  // Handle the game's "rules" for every "moment":
  // - movement, how do things change over time
  // - collisions, did things intersect and, if so, what should happen
  // - goals, did the game or level end?
  void step(double elapsedTime) {
    myPaddle.movePaddle(elapsedTime);
    levelManager.levelFunctionality(elapsedTime);
    levelManager.controlMovingBlocks(elapsedTime);
    powerUpManager.updatePowerUps(elapsedTime);
    powerUpManager.handlePowerUpPaddleCollision();
    levelManager.determineBallCollision();
    myBall.moveBall(elapsedTime);
    myScoreBoard.updateScoreBoard(root,myPlayer);
    endGame();
  }


  private void handleKeyInput(KeyCode code) {
    switch (code) {
      case LEFT:
        myPaddle.moveLeft();
        break;
      case RIGHT:
        myPaddle.moveRight();
        break;
      case R:
        resetPositions();
        break;
      case SPACE:
        pauseGame();
        break;
      case L:
        myPlayer.addLife();
        break;
      case P:
        powerUpManager.createPowerUp(SCREEN_WIDTH / 2.0, SCREEN_HEIGHT / 2.0);
        break;
      case W:
        myPaddle.setWidthFromDelta();
        break;
      case D:
        levelManager.removeFirst();
        break;
      case F:
        freezeGame();
        break;
      case T:
        myPaddle.teleportPaddle();
        break;
      case UP:
        myPaddle.incrementPaddleSpeed(PADDLE_SPEED_CHANGE);
        break;
      case DOWN:
        myPaddle.incrementPaddleSpeed(-PADDLE_SPEED_CHANGE);
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
        levelManager.setLevel(0);
        break;
    }
  }

  private void handleKeyRelease(KeyCode code) {
    if (code == KeyCode.RIGHT || code == KeyCode.LEFT) {
      myPaddle.setSpeed(0);
    }
  }

  private void pauseGame() {
    if(!paused){
      animation.pause();
    }else{
      animation.play();
    }
    paused = !paused;
  }
  private void freezeGame() {
    myBall.controlFreeze();
    powerUpManager.controlFreeze();
  }

  private void resetPositions() {
    myPaddle.moveToStartingPosition();
    myBall.moveToCenter();
    powerUpManager.resetPositions();
  }

  private void endGame() {
    if (myPlayer.livesLeft() == 0 || (levelManager.getCurrentBlocks().size() == 0 && levelManager.getNumberOfLevels() == levelManager.currentLevel())) {
      animation.stop();
      root.getChildren().clear();
      Text t = new Text(SCREEN_WIDTH / 2.0, SCREEN_HEIGHT / 2.0, GAME_OVER + myPlayer.getScore());
      root.getChildren().add(t);
    }
  }


  public PowerUpManager getPowerUpManager() {
    return powerUpManager;
  }
  public LevelManager getLevelManager(){ return levelManager;}

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
