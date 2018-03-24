package ehb;

import interfaces.BrakeInterface;

public class Brake
{
  private static Pressure _currentPressure = new Pressure(0.0);
  
  public void setPressure(Pressure pressure)
  {
    _currentPressure = pressure;
    BrakeInterface.setPressure(_currentPressure.get());
  }
  
  public Pressure getPressure()
  {
     return _currentPressure;
  }
}
