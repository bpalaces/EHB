package junit_tests;

import org.junit.Test;

import ehb.Brake;
import ehb.Pressure;
import junit.framework.TestCase;

public class BrakeTest extends TestCase
{

  private static InitEngine _init;
  Brake brake;

  @Override
  protected void setUp() throws Exception{
    if (_init == null) {
      _init = new InitEngine();
      _init.init();
    }
    brake = new Brake();
  }

  @Test
  public void testZeroPressure()
  {
    brake.setPressure(new Pressure(0.0));
    assertEquals(0,brake.getPressure().get(),0);
  }

  @Test
  public void testNegativePressure()
  {
    try {
      brake.setPressure(new Pressure(-2));
    }catch(IllegalArgumentException e) {}
  }

  public void testPositivePressure()
  {
    brake.setPressure(new Pressure(50.0));
    assertEquals(50, brake.getPressure().get(), 0);
  }
}
