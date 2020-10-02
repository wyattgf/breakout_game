package breakout.PowerUp;

import breakout.Ball;
import java.util.List;

/**
 * @author Hosam Tageldin, Wyatt Focht
 */
public class PowerUpFireBall extends PowerUp {

  private static final String POWER_UP_IMAGE = "data/sun.jpg";
  private static final boolean MAKE_FIERY_BALL = true;
  private List<Ball> myBalls;

  /**
   * This method is a constructor for a PowerUpFireBall object.
   *
   * @param initialX         double representing the initial X position of a power up
   * @param initialY         double representing the initial Y position of a power up
   * @param myPowerUpManager PowerUpManager object that is the power up manager associated with this
   *                         specific power up
   */
  public PowerUpFireBall(double initialX, double initialY, PowerUpManager myPowerUpManager) {
    super(initialX, initialY, myPowerUpManager);
    myBalls = getPowerUpManager().getMyBalls();
    setPowerUpImage(POWER_UP_IMAGE);
  }

  /**
   * This method creates a new power up that is a copy (same instance variables) as the power up
   * that it is called off of
   *
   * @return a new PowerUpMultiple Balls object
   */
  @Override
  public PowerUp newCopy() {
    return new PowerUpFireBall(this.getCenterX(), this.getCenterY(), getPowerUpManager());
  }

  /**
   * This method activates the Multiple Balls Power Up
   */
  @Override
  public void activatePowerUp() {
    myBalls.get(0).ballPowerUp(MAKE_FIERY_BALL);
  }

}
