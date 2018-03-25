package ehb;

/**
 * Class that holds a pressure value, used for named typing.
 */
public class Pressure
{
  private double _pressure;

  /**
   *
   * @param pressureVal Pressure to set. (0.0-100.0)
   */
  public Pressure(double pressureVal)
  {
    if(pressureVal >= 0.0 && pressureVal <= 100.0) _pressure = pressureVal;
    else throw new IllegalArgumentException("Pressure out of range, must be (0.0-100.0).");
  }

  /**
   *
   * @return Pressure value as a double.
   */
  public double get() { return _pressure; }
}
