package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 *
 *
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Game extends Application {
  public static final String TITLE = "Breakout JavaFX";
  public static final int SCREEN_WIDTH = 400;
  public static final int SCREEN_HEIGHT = 400;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.AZURE;
  public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
  public static final int VERTICAL_OFFSET = 80;

  // some things needed to remember during game
  private Scene myScene;
  private Paddle myPaddle;
  private Ball myBall;



  /**
   * Initialize what will be displayed and how it will be updated.
   */
  @Override
  public void start (Stage stage) {
    // attach scene to the stage and display it
    myScene = setupScene(SCREEN_WIDTH, SCREEN_HEIGHT, BACKGROUND);
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  // Create the game's "scene": what shapes will be in the game and their starting properties
  Scene setupScene (int width, int height, Paint background) {
    // create one top level collection to organize the things in the scene
    Group root = new Group();
    myPaddle = new Paddle(SCREEN_WIDTH,SCREEN_HEIGHT);
    myBall = new Ball(SCREEN_WIDTH,SCREEN_HEIGHT);
    root.getChildren().add(myPaddle);
    root.getChildren().add(myBall);
    // create a place to see the shapes
    Scene scene = new Scene(root, width, height, background);
    // respond to input
    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    return scene;
  }

  // Handle the game's "rules" for every "moment":
  // - movement, how do things change over time
  // - collisions, did things intersect and, if so, what should happen
  // - goals, did the game or level end?
  void step (double elapsedTime) {
    myBall.moveBall(elapsedTime);
  }


  private void handleKeyInput (KeyCode code) {
    switch (code) {
      case LEFT -> myPaddle.moveLeft();
      case RIGHT -> myPaddle.moveRight();
      case R -> resetPositions();
    }
  }

  private void resetPositions() {
    myPaddle.moveToCenter();
    myBall.moveToCenter();
  }


  /**
   * Start the program.
   */
  public static void main (String[] args) {
    launch(args);
  }
}
