package ehb;

import interfaces.BrakeInterface;

/**
 * Virtual abstraction of the brake interface that maintains the current pressure.
 */
public class Brake
{
  // Default start pressure of 0.0.
  private static Pressure _currentPressure = new Pressure(0.0);

  /**
   *
   * @param pressure The pressure percentage to apply to the brake
   */
  public void setPressure(Pressure pressure)
  {
    _currentPressure = pressure;
    BrakeInterface.setPressure(_currentPressure.get());
  }

  /**
   *
   * @return The current pressure being applied to the brake.
   */
  public Pressure getPressure()
  {
     return _currentPressure;
  }
}
