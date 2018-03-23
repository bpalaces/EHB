package ehb;

import interfaces.GearInterface;
import interfaces.GearTypes;
import interfaces.SpeedInterface;

public class Motion
{
  private GearTypes currentGear = null;
  private GearTypes previousGear = null;
  
  public double getSpeed()
  {
    return SpeedInterface.getSpeed();
  }
  
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
  
  public GearTypes getPreviousGear()
  {
    if(previousGear == null)
    {
      return GearInterface.getGear();
    }
    else return previousGear;

  }
}
