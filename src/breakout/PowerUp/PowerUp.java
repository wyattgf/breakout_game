package breakout.PowerUp;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */

public abstract class PowerUp extends Circle {

  //constants
  private static final double POWER_UP_RADIUS = 9;
  private static final int POWER_UP_SPEED = 50;
  private static final int MY_Y_DIRECTION = 1;

  //instance variables
  private PowerUpManager myPowerUpManager;
  private Image image;


  /**
   * This method is a constructor for a PowerUp object.
   *
   * @param initialX         double representing the initial X position of a power up
   * @param initialY         double representing the initial Y position of a power up
   * @param myPowerUpManager PowerUpManager object that is the power up manager associated with this
   *                         specific power up
   */
  public PowerUp(double initialX, double initialY, PowerUpManager myPowerUpManager) {
    super(initialX, initialY, POWER_UP_RADIUS);
    this.myPowerUpManager = myPowerUpManager;
  }

  /**
   * This method creates a new power up that is a copy (same instance variables) as the power up
   * that it is called off of
   *
   * @return a new power up object
   */
  public abstract PowerUp newCopy();

  /**
   * This method sets the color fill in a PowerUp
   * @param color Color corresponding to the desired color of a PowerUp
   */
  public void assignPowerUpColor(Color color) {
    this.setFill(color);
  }

  /**
   * This method sets the image fill in a PowerUp
   * @param powerUpImage String corresponding to the file that contains the desired image for the
   *                     PowerUp
   */
  public void setPowerUpImage(String powerUpImage){
    try{
      InputStream stream = new FileInputStream(powerUpImage);
      image = new Image(stream);
    }catch(FileNotFoundException e){

    }
    this.setFill(new ImagePattern(image));
  }
  /**
   * This method activates the power up corresponding to the type of power up object that it is
   * called off of
   */
  public abstract void activatePowerUp();

  /**
   * This method is a getter method for the speed in which a power up object is moving
   *
   * @return int corresponding to the speed in which a power up object is moving
   */
  public int getPowerUpSpeed() {
    return POWER_UP_SPEED;
  }

  /**
   * This method is a getter method for the Y direction in which a power up object is moving
   *
   * @return int corresponding to the Y direction in which a power up object is moving
   */
  public int getYDirection() {
    return MY_Y_DIRECTION;
  }

  /**
   * This method is a getter method for the power up manager that is associated with this specific
   * power up
   *
   * @return PowerUpManager referring to the associated power up manager object with this specific
   * power up
   */
  public PowerUpManager getPowerUpManager() {
    return myPowerUpManager;
  }
}
