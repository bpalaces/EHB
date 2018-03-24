package junit_tests;

import ehb.ActionTypes;
import ehb.Actions;
import ehb.Brake;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ActionsTest extends TestCase
{

  private Actions _actions;
  private Brake _brake;

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
  public void testDisengageEhb()
  {
    RunTest test = new RunTest();
    test.execute(() ->
    {
      _actions.execute(ActionTypes.DISENGAGE_EHB);
      assert(_brake.getPressure().get() == 0.0);
    });
  }

  @Test
  public void testFullyEngageEhb()
  {
    RunTest test = new RunTest();
    test.execute(() ->
    {
      _actions.execute(ActionTypes.FULLY_ENGAGE_EHB);
      assert(_brake.getPressure().get() == 100.0);
    });
  }

  @Test
  public void testSounds()
  {
    RunTest test = new RunTest();
    test.execute(() ->
    {
      _actions.execute(ActionTypes.PLAY_CONTINUOUS_ALERT);
      _actions.execute(ActionTypes.PLAY_DISENGAGED_SOUND);
      _actions.execute(ActionTypes.PLAY_ENGAGED_SOUND);
    });
  }

  @Test
  public void testColors()
  {
    RunTest test = new RunTest();
    test.execute(() ->
    {
      _actions.execute(ActionTypes.SET_COLOR_BLUE);
      _actions.execute(ActionTypes.SET_COLOR_ORANGE);
      _actions.execute(ActionTypes.SET_COLOR_RED);
    });
  }

}
