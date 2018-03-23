package ehb;

import java.util.concurrent.TimeUnit;

import interfaces.ButtonColorTypes;
import interfaces.ButtonInterface;
import interfaces.ButtonSoundTypes;

public class Alarm
{
  
  public void setColor(ButtonColorTypes color)
  {
    ButtonInterface.setColor(color);
  }

  public void play(String currentState)
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
    try
    {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
