package junit_tests;

import org.junit.Test;

import ehb.Alarm;

public class AlarmTest
{

  @Test
  public void testPlay() throws InterruptedException
  {
    Alarm alarm = new Alarm();
    alarm.play("engaging");
    alarm.play("disengaging");
    alarm.play("");
  }

}
