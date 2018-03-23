package ehb;

import interfaces.ButtonColorTypes;
import interfaces.ButtonInterface;
import interfaces.ButtonSoundTypes;

public class Alarm
{
  long currTime = 0;
  long lastTime = 0;
  double oneSecond = Math.pow(10, 9);
  
  public void setColor(ButtonColorTypes color)
  {
    ButtonInterface.setColor(color);
  }

  public void play(String currentState)
  {
    currTime = System.nanoTime();
    if(lastTime == 0 || ((currTime - lastTime) >= oneSecond))
    {
      if(currentState.equals("engaging"))
      {
        ButtonInterface.play(ButtonSoundTypes.ENGAGED);
      }
      else if(currentState.equals("disengaging"))
      {
        ButtonInterface.play(ButtonSoundTypes.DISENGAGED);
      }
      else // warning, this is for when we are out of park and still have the ehb engaged, it warns the user so they don't go drive off with it on.
      {
        ButtonInterface.play(ButtonSoundTypes.SHORT_BEEP_A);
      }
      lastTime = currTime;
    }
  }
}
