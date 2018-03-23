package ehb;

import ehb.Pressure.WrongPressureException;
import interfaces.BrakeInterface;

public class Brake
{
  private Pressure currentPressure = null;
  
  public void setPressure(double pressure)
  {
    try
    {
      currentPressure = new Pressure(pressure);
      BrakeInterface.setPressure(currentPressure.getPressure());
    }
    catch(WrongPressureException ex)
    {
      ex.printStackTrace();
    }
  }
  
  public double getPressure()
  {
    double pressureVal = 0;
    try
    {
      pressureVal = currentPressure.getPressure();
    }
    catch(NullPointerException ex)
    {
      ex.printStackTrace();
    }
    return pressureVal;
  }
}
