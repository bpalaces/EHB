package junit_tests;

import org.junit.Test;

import ehb.EHB;
import junit.framework.TestCase;

public class EHBTest extends TestCase
{
  private static InitEngine _init;
  
  @Override
  protected void setUp() throws Exception{
    if (_init == null) {
      _init.init();
    }
  }

  @Test
  public void testUpdate()
  {
    RunTest test = new RunTest();
    test.execute(() -> {
      EHB ehb = new EHB();
      for(int i = 0; i < 50; i ++)
      {
        ehb.update();
      }
    });
  }
}
