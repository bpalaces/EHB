package junit_tests;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import ehb.Alarm;

public class AlarmTest
{

  @Test
  public void testPlay() throws InterruptedException
  {
    Alarm alarm = new Alarm();
    alarm.play("engaging");
    TimeUnit.SECONDS.sleep(1);
    alarm.play("disengaging");
    TimeUnit.SECONDS.sleep(1);
    alarm.play("");
    TimeUnit.SECONDS.sleep(1);
  }

}
