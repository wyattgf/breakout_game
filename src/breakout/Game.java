package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.assertj.core.internal.bytebuddy.implementation.bytecode.ByteCodeAppender;


/**
 *
 *
 * @author Hosam Tageldin, Wyatt Focht
 */
public class Game extends Application {
  public static final String TITLE = "Breakout JavaFX";
  public static final int SIZE = 400;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.AZURE;
  public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
  public static final Paint RACER_COLOR = Color.HOTPINK;
  public static final int RACER_SIZE = 30;
  public static final int RACER_SPEED = 40;
  public static final Paint MOVER_COLOR = Color.PLUM;
  public static final int MOVER_SIZE = 50;
  public static final int MOVER_ROUNDING = 15;
  public static final int MOVER_SPEED = 5;
  public static final Paint GROWER_COLOR = Color.BISQUE;
  public static final double GROWER_RATE = 1.1;
  public static final int GROWER_SIZE = 50;
  public static final int VERTICAL_OFFSET = 80;

  // some things needed to remember during game
  private Scene myScene;
  private Rectangle myMover;
  private Rectangle myGrower;
  private Circle myRacer;
  private Paddle myPaddle;
  private Ball myBall;



  /**
   * Initialize what will be displayed and how it will be updated.
   */
  @Override
  public void start (Stage stage) {
    // attach scene to the stage and display it
    myScene = setupScene(SIZE, SIZE, BACKGROUND);
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
    // make some shapes and set their properties
    myRacer = new Circle(width / 2, height / 2, RACER_SIZE / 2);
    myRacer.setFill(RACER_COLOR);
    myRacer.setId("racer");
    // x and y represent the top left corner, so center it in window
    myMover = new Rectangle(width / 2 - MOVER_SIZE / 2, height / 2 - VERTICAL_OFFSET, MOVER_SIZE, MOVER_SIZE);
    myMover.setArcWidth(MOVER_ROUNDING);
    myMover.setArcHeight(MOVER_ROUNDING);
    myMover.setFill(MOVER_COLOR);
    myMover.setId("mover");
    myGrower = new Rectangle(width / 2 - GROWER_SIZE / 2, height / 2 + VERTICAL_OFFSET, GROWER_SIZE, GROWER_SIZE);
    myGrower.setFill(GROWER_COLOR);
    myGrower.setId("grower");
    // order added to the group is the order in which they are drawn (so last one is on top)
    root.getChildren().add(myMover);
    root.getChildren().add(myGrower);
    root.getChildren().add(myRacer);
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
    // update "actors" attributes
    updateShapes(elapsedTime);
    // check for collisions (order may matter! and should be its own method if lots of kinds of collisions)
    checkRacerGrowerCollision();
    checkMoverGrowerCollision(myMover, myGrower);
  }

  // Change properties of shapes in small ways to animate them over time
  private void updateShapes (double elapsedTime) {
    // there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    if(myRacer.getCenterX() - myRacer.getRadius()>= SIZE){
      myRacer.setCenterX(-myRacer.getRadius() + RACER_SPEED * elapsedTime);
    }else{
      myRacer.setCenterX(myRacer.getCenterX() + RACER_SPEED * elapsedTime);
    }
    myMover.setRotate(myMover.getRotate() - 1);
    myGrower.setRotate(myGrower.getRotate() + 1);
  }

  // Determine if specific things collided and respond appropriately
  private void checkRacerGrowerCollision () {
    // can check bounding box (for some kinds of shapes, like images, that is the only option)
    if (myGrower.getBoundsInParent().intersects(myRacer.getBoundsInParent())) {
      myGrower.setFill(HIGHLIGHT);
    }
    else {
      myGrower.setFill(GROWER_COLOR);
    }
  }

  // Determine if any two shapes collided, might be useful to check different instances that react similarly
  private void checkMoverGrowerCollision (Shape hitter, Shape hittee) {
    // with shapes, can check precisely
    Shape intersection = Shape.intersect(hitter, hittee);
    if (intersection.getBoundsInLocal().getWidth() != -1) {
      hitter.setFill(HIGHLIGHT);
    }
    else {
      hitter.setFill(MOVER_COLOR);
    }
  }

  private void handleKeyInput (KeyCode code) {
    switch (code) {
      case LEFT -> myPaddle.moveLeft();
      case RIGHT -> myPaddle.moveRight();
    }
  }


  /**
   * Start the program.
   */
  public static void main (String[] args) {
    launch(args);
  }
}
