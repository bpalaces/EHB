package junit_tests;

import org.junit.Test;

import ehb.ActionTypes;
import ehb.Actions;
import ehb.Alarm;
import ehb.Brake;
import ehb.Motion;
import junit.framework.TestCase;

public class ActionsTest extends TestCase
{
  private Brake _brake = new Brake();
  private Alarm _alarm = new Alarm();
  private Motion _motion = new Motion();
  private Actions _actions = new Actions(_brake, _alarm, _motion);

  private static InitEngine _init;

  // This method is called before every single test!!!
  @Override
  public void setUp() throws Exception {
    // Do this so that the engine is only initialized once
    if (_init == null) {
      _init.init();
      _brake = new Brake();
      _actions = new Actions(_brake, new Alarm(), new Motion());
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

  @Test
  public void testUpdatePressure()
  {
    RunTest test = new RunTest();
    test.execute(() ->
    {
      double prevPressure = _brake.getPressure().get();
      _actions.execute(ActionTypes.UPDATE_APPLIED_FORCE);
      assert(_brake.getPressure().get() > prevPressure);
    });
  }


}
