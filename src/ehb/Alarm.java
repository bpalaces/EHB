package ehb;

import interfaces.ButtonColorTypes;
import interfaces.ButtonInterface;
import interfaces.ButtonSoundTypes;

/**
 * Virtual abstraction of non-click information from the
 * ButtonInterface.
 */
public class Alarm
{
  long currTime = 0;
  long lastTime = 0;
  double oneSecond = Math.pow(10, 9);

  /**
   * Sets the button color.
   *
   * @param color Color to set the button
   */
  public void setColor(ButtonColorTypes color)
  {
    ButtonInterface.setColor(color);
  }

  /**
   * Play the sound corresponding to the specified value.
   * There is a one second time delay to prevent sounds from
   * being played on top of each other.
   *
   * @param sound Sound to play
   */
  public void play(ValidSoundTypes sound)
  {
    currTime = System.nanoTime();
    if(lastTime == 0 || ((currTime - lastTime) >= oneSecond))
    {
      switch(sound)
      {
        case ENGAGED:
          ButtonInterface.play(ButtonSoundTypes.ENGAGED);
          break;
        case DISENGAGED:
          ButtonInterface.play(ButtonSoundTypes.DISENGAGED);
          break;
        default: // Sound warning, purpose is to warn the driver before they are about to drive that the ehb is engaged.
          ButtonInterface.play(ButtonSoundTypes.SHORT_BEEP_A);
          break;
      }
      lastTime = currTime;
    }
  }
}
