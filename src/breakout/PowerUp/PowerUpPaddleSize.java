package breakout.PowerUp;

import breakout.Paddle;
import javafx.scene.paint.Color;

public class PowerUpPaddleSize extends PowerUp {

  private static final int PADDLE_DELTA = 10;
  private static final String POWER_UP_IMAGE = "data/plus.png";

  private Paddle myPaddle;

  /**
   * This method is a constructor for a PowerUpPaddleSize object.
   *
   * @param initialX         double representing the initial X position of a power up
   * @param initialY         double representing the initial Y position of a power up
   * @param myPowerUpManager PowerUpManager object that is the power up manager associated with this
   *                         specific power up
   */
  public PowerUpPaddleSize(double initialX, double initialY, PowerUpManager myPowerUpManager) {
    super(initialX, initialY, myPowerUpManager);
    myPaddle = getPowerUpManager().getPaddle();
    setPowerUpImage(POWER_UP_IMAGE);
    //assignPowerUpColor(POWER_UP_COLOR);
  }

  /**
   * This method creates a new power up that is a copy (same instance variables) as the power up
   * that it is called off of
   *
   * @return a new PowerUpPaddleSize object
   */
  @Override
  public PowerUp newCopy() {
    return new PowerUpPaddleSize(this.getCenterX(), this.getCenterY(), getPowerUpManager());
  }

  /**
   * This method activates the Paddle Size Power Up
   */
  @Override
  public void activatePowerUp() {
    myPaddle.changeWidth(myPaddle.getWidth() + PADDLE_DELTA);
  }

}
