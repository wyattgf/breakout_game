package breakout;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * This class implements the shooting laser beam that is found on Level Three. It is responsible for
 * what the laser looks like
 *
 * @author Hosam Tageldin, Wyatt Focht
 */

public class LaserBeam extends Rectangle {

  private static final String LASER_BEAM_IMAGE = "data/laserBeam.png";
  private static final double BLOCK_WIDTH = 10;
  private static final double BLOCK_HEIGHT = 75;
  private static final int LASER_SPEED = 500;
  private static final int MY_Y_DIRECTION = 1;

  /**
   * Create the LaserBeam based off the given x and y position
   *
   * @param xPos of the LaserBeam
   * @param yPos of the LaserBeam
   */
  public LaserBeam(double xPos, double yPos) {
    super(xPos, yPos, BLOCK_WIDTH, BLOCK_HEIGHT);
    setImage();
  }

  /**
   * This method moves the laser towards the paddle
   *
   * @param elapsedTime length of time that has passed in the game
   */
  public void moveLaser(double elapsedTime) {
    this.setY(this.getY() + MY_Y_DIRECTION * LASER_SPEED * elapsedTime);
  }

  private void setImage() {
    try {
      InputStream stream = new FileInputStream(LASER_BEAM_IMAGE);
      Image image = new Image(stream);
      this.setFill(new ImagePattern(image));
    } catch (FileNotFoundException e) {
      this.setFill(Color.RED);
    }
  }


}
