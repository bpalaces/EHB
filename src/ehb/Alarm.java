package EHB_Software;

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
  }
}
