package ehb;

public class Pressure
{
  private double _pressure;
  public Pressure(double pressureVal)
  {
    if(pressureVal >= 0.0 && pressureVal <= 100.0) _pressure = pressureVal;
    else throw new IllegalArgumentException("Pressure out of range, must be (0.0-100.0).");
  }
  
  public double get() { return _pressure; }
}
