package breakout;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LaserBeam extends Rectangle {

  private static final double BLOCK_WIDTH = 10;
  private static final double BLOCK_HEIGHT = 75;
  private static final int LASER_SPEED = 500;
  private static final int MY_Y_DIRECTION = 1;
  private static final List<Color> LASER_COLORS = Arrays
      .asList(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);

  public LaserBeam(double xPos, double yPos) {
    super(xPos, yPos, BLOCK_WIDTH, BLOCK_HEIGHT);
    setColor();
  }

  public void moveLaser(double elapsedTime) {
    this.setY(this.getY() + MY_Y_DIRECTION * LASER_SPEED * elapsedTime);
  }

  private void setColor() {
    this.setFill(LASER_COLORS.get(new Random().nextInt(LASER_COLORS.size())));
  }

}
