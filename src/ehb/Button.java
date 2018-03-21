package ehb;

import interfaces.ButtonInterface;

public class Button
{

  private long deltaT;
  private long currentTime;
  private long previousTime;
  
  public enum ButtonStatus
  {
    LONG_PRESS,
    SHORT_PRESS,
    NOT_PRESSED;
  }
  
  public ButtonStatus getStatus()
  {
    currentTime = System.nanoTime();
    if(!ButtonInterface.isDown())
    {
      deltaT = 0;
    }
    else
    {
      deltaT += (currentTime - previousTime);
    }
    previousTime = currentTime;
    if(deltaT >= 2.0)return ButtonStatus.LONG_PRESS;
    else if(deltaT > 0 && deltaT < 2.0)return ButtonStatus.SHORT_PRESS;
    else return ButtonStatus.NOT_PRESSED;
  }

}
