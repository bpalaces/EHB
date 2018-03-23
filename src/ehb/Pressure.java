package ehb;

public class Pressure
{
  Double pressureValue = null;
  
  public Pressure(double pressureVal)
  {
    if(pressureVal >= 0.0 && pressureVal <= 100.0)
    {
      pressureValue = new Double(pressureVal);
    }
    else
    {
      System.err.println("Invalid pressure value. Number between 0-100 expected.");
    }
  }
  
  public double getPressure()
  {
    return pressureValue.doubleValue();
  }
}
