import static org.junit.Assert.*;

import org.junit.Test;

import ehb.Brake;

public class BrakeTest
{

  @Test
  public void testSetPressure()
  {
    Brake brake = new Brake();
    brake.setPressure(-2);
    assertEquals(Double.NaN,brake.getPressure(),0);
    brake.setPressure(50);
    assertEquals(50, brake.getPressure(), 0);
    brake.setPressure(150);
    assertEquals(Double.NaN, brake.getPressure(), 0);
    brake.setPressure(40);
    assertEquals(40, brake.getPressure(), 0);
  }
}
