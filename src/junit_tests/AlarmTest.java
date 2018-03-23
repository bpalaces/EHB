package junit_tests;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import ehb.Alarm;
import interfaces.ButtonColorTypes;
import junit.framework.TestCase;

public class AlarmTest extends TestCase
{
  Alarm alarm = null;
  private static InitEngine _init;

  // This method is called before every single test!!!
  @Override
  public void setUp() throws Exception {
    // Do this so that the engine is only initialized once
    if (_init == null) {
      _init = new InitEngine();
      _init.init();
    }
    alarm = new Alarm();
  }

  @Test
  public void testSetColor()
  {
    RunTest test = new RunTest();
    test.execute(() ->{
      alarm.setColor(ButtonColorTypes.BLUE);
      alarm.setColor(ButtonColorTypes.GREEN);
      alarm.setColor(ButtonColorTypes.LIGHTBLUE);
      alarm.setColor(ButtonColorTypes.ORANGE);
      alarm.setColor(ButtonColorTypes.RED);
      alarm.setColor(ButtonColorTypes.YELLOW);
    });
  }
  
  @Test
  public void testPlay() throws InterruptedException
  {
    alarm.play("engaging");
    TimeUnit.SECONDS.sleep(1);
    alarm.play("disengaging");
    TimeUnit.SECONDS.sleep(1);
    alarm.play("");
    TimeUnit.SECONDS.sleep(1);
  }

}
