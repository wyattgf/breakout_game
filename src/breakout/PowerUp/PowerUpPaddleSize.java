package breakout.PowerUp;

import breakout.Paddle;
import javafx.scene.paint.Color;

public class PowerUpPaddleSize extends PowerUp {

  private static final int PADDLE_DELTA = 10;
  private static final Color POWER_UP_COLOR = Color.CHARTREUSE;

  private Paddle myPaddle;

  public PowerUpPaddleSize(double initialX, double initialY, PowerUpManager myPowerUpManager) {
    super(initialX, initialY, myPowerUpManager);
    myPaddle = getPowerUpManager().getPaddle();
    assignPowerUpColor(POWER_UP_COLOR);
  }

  @Override
  public PowerUp newCopy() {
    return new PowerUpPaddleSize(this.getCenterX(),this.getCenterY(),getPowerUpManager());
  }

  @Override
  public void activatePowerUp() {
    myPaddle.changeWidth(myPaddle.getWidth() + PADDLE_DELTA);
  }

}
