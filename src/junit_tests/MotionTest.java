package junit_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import ehb.Motion;
import interfaces.GearTypes;
import junit.framework.TestCase;
import org.junit.Test;

public class MotionTest extends TestCase
{

  private static InitEngine _init;
  Motion motion;

  @Override
  protected void setUp() throws Exception{
    if (_init == null) {
      _init.init();
    }
    motion = new Motion();
  }

  @Test
  public void testGetSpeed()
  {
    assertEquals(motion.getSpeed(),0,0);
  }

  @Test
  public void testGetCurrentGear()
  {
    assertEquals(motion.getCurrentGear(), GearTypes.DRIVE);
  }

  @Test
  public void testGetPreviousGear()
  {
    assertEquals(motion.getCurrentGear(), GearTypes.DRIVE);
  }

}
