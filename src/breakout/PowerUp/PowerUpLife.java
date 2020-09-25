package breakout.PowerUp;

import breakout.Player;
import javafx.scene.paint.Color;

public class PowerUpLife extends PowerUp {

  private static final Color POWER_UP_COLOR = Color.MEDIUMAQUAMARINE;
  private Player myPlayer;

  public PowerUpLife(double initialX, double initialY, PowerUpManager myPowerUpManager) {
    super(initialX, initialY, myPowerUpManager);
    myPlayer = getPowerUpManager().getPlayer();
    assignPowerUpColor(POWER_UP_COLOR);
  }

  @Override
  public PowerUp newCopy() {
    return new PowerUpLife(this.getCenterX(),this.getCenterY(),getPowerUpManager());
  }

  @Override
  public void activatePowerUp() {
    myPlayer.addLife();
  }

}
