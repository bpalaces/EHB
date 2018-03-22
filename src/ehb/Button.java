package ehb;

import interfaces.ButtonInterface;

public class Button
{

  private long deltaT;
  private long currentTime;
  private long previousTime;

  // This is not needed if the button is always going to be sticky now.. additionally if
  // the button is stick there is no need to differentiate between a long and short press.
  private Thread _pooler  = new Thread() {
  public void run() {
    while(true) {
      currentTime = System.nanoTime();
      if (!ButtonInterface.isDown()) {
        deltaT = 0;
      } else {
        deltaT += (currentTime - previousTime);
      }
      previousTime = currentTime;
    }
  }
};

  Button()
  {
    _pooler.start();
  }

  public ButtonStatus getStatus()
  {

    if(deltaT >= 2000000000.0)return ButtonStatus.LONG_PRESS;
    else if(deltaT > 0 && deltaT < 2000000000.0)return ButtonStatus.SHORT_PRESS;
    else return ButtonStatus.NOT_PRESSED;
  }

}
