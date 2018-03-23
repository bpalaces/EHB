package junit_tests;

import org.junit.Test;

import ehb.ActionTypes;
import ehb.Actions;
import junit.framework.TestCase;
import simulation.engine.Engine;
import org.junit.*;

public class ActionsTest extends TestCase
{
  private static InitEngine _init;

  // This method is called before every single test!!!
  @Override
  public void setUp() throws Exception {
    // Do this so that the engine is only initialized once
    if (_init == null) {
      _init = new InitEngine();
      _init.init();
    }
  }

  @Test
  public void testExecute()
  {
    RunTest test = new RunTest();
    test.execute(() ->
    {
      Actions actions = new Actions();
      actions.execute(ActionTypes.DISENGAGE_EHB);
      actions.execute(ActionTypes.FULLY_ENGAGE_EHB);
      actions.execute(ActionTypes.NO_OP);
      actions.execute(ActionTypes.PLAY_CONTINUOUS_ALERT);
      actions.execute(ActionTypes.PLAY_DISENGAGED_SOUND);
      actions.execute(ActionTypes.PLAY_ENGAGED_SOUND);
      actions.execute(ActionTypes.SET_COLOR_BLUE);
      actions.execute(ActionTypes.SET_COLOR_ORANGE);
      actions.execute(ActionTypes.SET_COLOR_RED);
      actions.execute(ActionTypes.UPDATE_APPLIED_FORCE);
      //assertEquals(false, true);
    });
  }
}
