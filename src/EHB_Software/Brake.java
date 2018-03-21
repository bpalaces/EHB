package EHB_Software;

import interfaces.BrakeInterface;

//This needs the isEngaged method, but that method also needs to be added to
//BrakeInterface.
public class Brake
{
  private double currentPressure = 0;
  
  public void setPressure(double pressure)
  {
    if(pressure >= 0.0 && pressure <= 100.0)
    {
      currentPressure = pressure;
      BrakeInterface.setPressure(pressure);
    }
    else System.err.println("Invalid pressure percentage entered.");
  }
  
  public double getPressure()
  {
    return currentPressure;
  }
}
