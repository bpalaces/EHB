package ehb;

import interfaces.ButtonColorTypes;
import interfaces.ButtonInterface;
import interfaces.ButtonSoundTypes;

public class Alarm
{
  public void setColor(ButtonColorTypes color)
  {
    ButtonInterface.setColor(color);
  }

  private long _delay = System.currentTimeMillis();

  public void play(String currentState)
  {
    if(System.currentTimeMillis() - _delay < 1000) return;
    _delay = System.currentTimeMillis();
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
  }
}
