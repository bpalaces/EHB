package ehb;

import interfaces.GearInterface;
import interfaces.GearTypes;
import interfaces.SpeedInterface;

/**
 * Virtual abstraction of the SpeedInterface and GearInterface.
 */
public class Motion
{
  private GearTypes currentGear = null;
  private GearTypes previousGear = null;

  /**
   *
   * @return The current speed.
   */
  public double getSpeed()
  {
    return SpeedInterface.getSpeed();
  }

  /**
   *
   * @return The current gear.
   */
  public GearTypes getCurrentGear()
  {
    GearTypes currGear = GearInterface.getGear();
    if(currentGear == null || !currGear.equals(currentGear))
    {
      previousGear = currentGear;
      currentGear = currGear;
    }
    return currentGear;
  }

  /**
   *
   * @return The previous gear.
   */
  public GearTypes getPreviousGear()
  {
    if(previousGear == null)
    {
      return GearInterface.getGear();
    }
    else return previousGear;

  }
}
