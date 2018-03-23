package ehb;

import interfaces.BrakeInterface;

public class Brake
{
  private Pressure currentPressure = null;
  
  public void setPressure(double pressure)
  {
    currentPressure = new Pressure(pressure);
    try
    {
      BrakeInterface.setPressure(currentPressure.getPressure());
    }
    catch(NullPointerException ex)
    {
      System.err.println("Pressure is null. Please enter a valid pressure.");
    }
  }
  
  public double getPressure()
  {
    double pressureVal = Double.NaN;
    try
    {
      pressureVal = currentPressure.getPressure();
    }
    catch(NullPointerException ex)
    {
      System.err.println("Pressure is null. Please enter a valid pressure.");
    }
    return pressureVal;
  }
}
