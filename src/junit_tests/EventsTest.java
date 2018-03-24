package junit_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import ehb.EventTypes;
import ehb.Events;
import junit.framework.TestCase;
import org.junit.Test;

public class EventsTest extends TestCase
{
  private static InitEngine _init;
  Events eventToTest;

  @Override
  protected void setUp() throws Exception{
    if (_init == null) {
      _init = new InitEngine();
      _init.init();
    }
    eventToTest = new Events();
  }

//  public void testDidEventOccur()
//  {
//    fail("Not yet implemented");
//  }

  @Test
  public void testNoOP()
  {
     assertEquals(eventToTest.didEventOccur(EventTypes.NO_OP), true);
  }

  @Test
  public void testShiftOutOfPark()
  {
    assertEquals(eventToTest.didEventOccur(EventTypes.SHIFT_OUT_OF_PARK), false);
  }

  @Test
  public void testShftIntoPark()
  {
    assertEquals(eventToTest.didEventOccur(EventTypes.SHIFT_INTO_PARK), false);
  }

  @Test
  public void testButtonPress()
  {
    assertEquals(eventToTest.didEventOccur(EventTypes.BUTTON_PRESS), false);
  }

  @Test
  public void testSpeedZero()
  {
    assertEquals(eventToTest.didEventOccur(EventTypes.BUTTON_PRESS),false);
  }

  @Test
  public void testSpeedGreaterThanZero ()
  {
    assertEquals(eventToTest.didEventOccur(EventTypes.SPEED_GREATER_THAN_ZERO), false);
  }
}
