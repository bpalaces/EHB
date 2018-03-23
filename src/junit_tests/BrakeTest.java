package junit_tests;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;
import org.junit.Test;

import ehb.Brake;

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
    brake.setPressure(0);
    assertEquals(0,brake.getPressure(),0);
  }

  @Test
  public void testNegativePressure()
  {
    brake.setPressure(-2);
    assertEquals(0,brake.getPressure(),0);
  }

  public void testPositivePressure()
  {
    brake.setPressure(50);
    assertEquals(50, brake.getPressure(), 0);
  }
}
