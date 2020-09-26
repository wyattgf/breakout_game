package breakout.PowerUp;

import breakout.Player;
import javafx.scene.paint.Color;

public class PowerUpLife extends PowerUp {

  private static final String POWER_UP_IMAGE = "data/heart.png";
  private Player myPlayer;

  /**
   * This method is a constructor for a PowerUpLife object.
   *
   * @param initialX         double representing the initial X position of a power up
   * @param initialY         double representing the initial Y position of a power up
   * @param myPowerUpManager PowerUpManager object that is the power up manager associated with this
   *                         specific power up
   */
  public PowerUpLife(double initialX, double initialY, PowerUpManager myPowerUpManager) {
    super(initialX, initialY, myPowerUpManager);
    myPlayer = getPowerUpManager().getPlayer();
    setPowerUpImage(POWER_UP_IMAGE);
    //assignPowerUpColor(POWER_UP_COLOR);
  }

  /**
   * This method creates a new power up that is a copy (same instance variables) as the power up
   * that it is called off of
   *
   * @return a new PowerUpLife object
   */
  @Override
  public PowerUp newCopy() {
    return new PowerUpLife(this.getCenterX(), this.getCenterY(), getPowerUpManager());
  }

  /**
   * This method activates the Life Power Up
   */
  @Override
  public void activatePowerUp() {
    myPlayer.addLife();
  }

}
