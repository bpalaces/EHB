package ehb;

public class Pressure
{
  Double pressureVal = null;
  
  public Pressure(double pressureVal)throws WrongPressureException
  {
    if(pressureVal >= 0 && pressureVal <= 100)
    {
      pressureVal = new Double(pressureVal);
    }
    else
    {
      throw new WrongPressureException("Invalid pressure value. Number between 0-100 expected.");
    }
  }
  
  public double getPressure() throws NullPointerException
  {
    if(pressureVal != null)
    {
      return pressureVal.doubleValue();
    }
    else
    {
      throw new NullPointerException("The pressure is null. Please enter a pressure value between 0-100.");
    }
  }
  
  @SuppressWarnings("serial")
  public class WrongPressureException extends Exception
  {
    public WrongPressureException(String message)
    {
      super(message);
    }
  }
}
