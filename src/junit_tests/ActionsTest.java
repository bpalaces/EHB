package junit_tests;

import org.junit.Test;

import ehb.ActionTypes;
import ehb.Actions;
import junit.framework.TestCase;
import simulation.engine.Engine;

public class ActionsTest extends TestCase
{

  @Test
  public void testExecute()
  {
    RunTest test = new RunTest();
    test.execute(() ->
    {
      Actions actions = new Actions();
      new Thread(() -> Engine.main(new String[0])).start();
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
    });
    // Stall until the test is complete
    while (!test.isComplete())
      ;
  }
  
  @Override
  protected void setUp() throws Exception {
    InitEngine init = new InitEngine();
    init.init();
    // Stall until the engine has finished starting up
    while (!init.isInitialized())
      ;
  }

}
