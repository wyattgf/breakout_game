package breakout;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Game extends Application {

  private static final String TITLE = "Breakout JavaFX";
  private static final String GAME_OVER = "Game is over!\nFinal Score: ";
  public static final int SCREEN_WIDTH = 400;
  public static final int SCREEN_HEIGHT = 400;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.AZURE;
  private static final double POWER_UP_FREQ = 0.9;
  private static final int PADDLE_DELTA = 10;

  // some things needed to remember during game
  private Scene myScene;
  private Paddle myPaddle;
  private Player myPlayer;
  private Group root;
  private Text scoreBoard;
  private BlockReader blockReader;
  private Ball myBall;
  private List<Block> level1Blocks;
  private List<PowerUp> currentPowerUps;
  private int numberOfPowerUps;
  private Timeline animation;


  /**
   * Initialize what will be displayed and how it will be updated.
   */
  @Override
  public void start(Stage stage) {
    // attach scene to the stage and display it
    myScene = setupScene(SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND);
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  // Create the game's "scene": what shapes will be in the game and their starting properties
  Scene setupScene(int width, int height, Paint background) {
    // create one top level collection to organize the things in the scene
    root = new Group();
    myPlayer = new Player();
    myPlayer.setId("player");
    myPaddle = new Paddle(SCREEN_WIDTH, SCREEN_HEIGHT);
    myPaddle.setId("paddle");
    myBall = new Ball(SCREEN_WIDTH, SCREEN_HEIGHT);
    myBall.setId("ball");
    blockReader = new BlockReader();
    currentPowerUps = new ArrayList<>();
    level1Blocks = blockReader.getBlocks();
    int i = 1;
    for (Block block : level1Blocks) {
      root.getChildren().add(block);
      block.setId("block" + i++);
    }
    root.getChildren().add(myPaddle);
    root.getChildren().add(myBall);
    root.getChildren().add(myPlayer);
    // create a place to see the shapes
    Scene scene = new Scene(root, width, height, background);
    // respond to input
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    scene.setOnKeyReleased(e -> handleKeyRelease(e.getCode()));
    return scene;
  }

  // Handle the game's "rules" for every "moment":
  // - movement, how do things change over time
  // - collisions, did things intersect and, if so, what should happen
  // - goals, did the game or level end?
  void step(double elapsedTime) {
    myBall.moveBall(elapsedTime);
    myPaddle.movePaddle(elapsedTime);
    checkCollisions();
    updatePowerUps(elapsedTime);
    updateScoreBoard();
    endGame();
  }

  private void updatePowerUps(double elapsedTime) {
    if (currentPowerUps != null) {
      for (PowerUp p : currentPowerUps) {
        p.movePowerUp(elapsedTime);
      }
    }
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
        myPlayer.setPlayerLives(myPlayer.getLives() + 1);
        break;
      case P:
        createPowerUp(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, 1);
        break;
      case W:
        myPaddle.changeWidth(myPaddle.getWidth() + PADDLE_DELTA);
        break;
    }
  }

  private void handleKeyRelease(KeyCode code) {
    if (code == KeyCode.RIGHT || code == KeyCode.LEFT) {
      myPaddle.setSpeed(0);
    }
  }

  private void checkCollisions() {
    if (myBall.getBoundsInParent().intersects(myPaddle.getBoundsInParent())) {
      handlePaddleCollision();
    }
    for (Block block : level1Blocks) {
      if (myBall.getBoundsInParent().intersects(block.getBoundsInParent())) {
        handleBlockCollisions(block);
        break;
      }
    }
    for (PowerUp powerup : currentPowerUps) {
      if (powerup.getBoundsInParent().intersects(myPaddle.getBoundsInParent())) {
        handlePowerUpPaddleCollision(powerup);
        break;
      }
    }
  }

  private void handlePowerUpPaddleCollision(PowerUp p) {
    p.activatePowerUp();
    currentPowerUps.remove(p);
  }

  private void updateScoreBoard() {
    root.getChildren().remove(scoreBoard);
    scoreBoard = new Text(10, 150, "Lives: " + (myPlayer.getLives() - myBall.livesLost())
        + "  Score: " + myPlayer.getScore());
    scoreBoard.setFont(new Font(20));
    root.getChildren().add(scoreBoard);
  }

  private void handleBlockCollisions(Block block) {
    if (myBall.getCenterX() <= block.getX()
        || myBall.getCenterX() >= block.getX() + block.getBlockWidth()) {
      myBall.bounceX();
      createPowerUp(block.getX(), block.getY(), POWER_UP_FREQ);
      root.getChildren().remove(block);
      level1Blocks.remove(block);


    } else if (myBall.getCenterY() + myBall.getRadius() <= block.getY()
        || myBall.getCenterY() + myBall.getRadius() >= block.getY()) {
      myBall.bounceY();
      createPowerUp(block.getX(), block.getY(), POWER_UP_FREQ);
      root.getChildren().remove(block);
      level1Blocks.remove(block);
    }
    myPlayer.setPlayerScore(myPlayer.getScore() + 200);
  }

  private void handlePaddleCollision() {
    if (myBall.getCenterY() <= myPaddle.getY()) {
      myBall.bounceY();
    } else if (myBall.getCenterX() + myBall.getRadius() <= myPaddle.getX()
        || myBall.getCenterX() >= myPaddle.getX()) {
      myBall.bounceX();
    }
  }

  private void pauseGame() {
    myPaddle.controlPause();
    myBall.controlPause();
    for (PowerUp p : currentPowerUps) {
      p.controlPause();
    }
  }

  private void resetPositions() {
    myPaddle.moveToStartingPosition();
    myBall.moveToCenter();
    for (PowerUp p : currentPowerUps) {
      p.removePowerUpFromRoot();
    }
    currentPowerUps.clear();
  }

  private void endGame() {
    if (myPlayer.getLives() - myBall.livesLost() == 0 || level1Blocks.size() == 0) {
      animation.stop();
      root.getChildren().clear();
      Text t = new Text(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, GAME_OVER + myPlayer.getScore());
      t.setFont(new Font(20));
      root.getChildren().add(t);
    }
  }

  private void createPowerUp(double initialX, double initialY, double powerUpFreq) {
    if (Math.random() <= powerUpFreq) {
      PowerUp newPowerUp = new PowerUp(initialX, initialY, root, SCREEN_HEIGHT, myPlayer, myPaddle);
      newPowerUp.setId("powerup" + numberOfPowerUps);
      numberOfPowerUps++;
      currentPowerUps.add(newPowerUp);

    }
  }

  public List<PowerUp> getCurrentPowerUps() {
    return currentPowerUps;
  }

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
