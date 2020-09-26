package breakout.Level;

import breakout.Ball;
import breakout.Paddle;
import breakout.Player;
import breakout.PowerUp.PowerUp;
import breakout.PowerUp.PowerUpLife;
import breakout.PowerUp.PowerUpPaddleSize;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;

public class LevelManager {

  private final List<Level> POSSIBLE_LEVELS = List
      .of(new LevelOne(), new LevelTwo(), new LevelThree());

  private Group myRoot;
  private List<Paddle> myPaddles;
  private List<Ball> myBalls;
  private Player myPlayer;
  private List<PowerUp> currentBlocks;
  private boolean paused;

  public LevelManager(Group myRoot, List<Paddle> myPaddles, List<Ball> myBalls, Player myPlayer) {
    this.myRoot = myRoot;
    this.myPaddles = myPaddles;
    this.myBalls = myBalls;
    this.myPlayer = myPlayer;
    paused = false;
  }
}
