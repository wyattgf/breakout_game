package breakout;

import javafx.scene.paint.Color;

public class PowerUpLife extends PowerUp {

  private static final int LIFE_DELTA = 1;
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
    myPlayer.setPlayerLives(LIFE_DELTA + myPlayer.getLives());
  }

}
