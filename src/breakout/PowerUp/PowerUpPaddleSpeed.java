package breakout.PowerUp;

import breakout.Paddle;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class PowerUpPaddleSpeed extends PowerUp {

  private static final String POWER_UP_IMAGE = "data/wind.png";

  private Paddle myPaddle;

  /**
   * This method is a constructor for a PowerUpPaddleSize object.
   *
   * @param initialX         double representing the initial X position of a power up
   * @param initialY         double representing the initial Y position of a power up
   * @param myPowerUpManager PowerUpManager object that is the power up manager associated with this
   *                         specific power up
   */
  public PowerUpPaddleSpeed(double initialX, double initialY, PowerUpManager myPowerUpManager) {
    super(initialX, initialY, myPowerUpManager);
    myPaddle = getPowerUpManager().getPaddle();
    setPowerUpImage(POWER_UP_IMAGE);
  }

  /**
   * This method creates a new power up that is a copy (same instance variables) as the power up
   * that it is called off of
   *
   * @return a new PowerUpPaddleSpeed object
   */
  @Override
  public PowerUp newCopy() {
    return new PowerUpPaddleSpeed(this.getCenterX(), this.getCenterY(), getPowerUpManager());
  }

  /**
   * This method activates the Paddle Speed Power Up
   */
  @Override
  public void activatePowerUp() {
    myPaddle.setSpeedFromDelta();
  }

}
