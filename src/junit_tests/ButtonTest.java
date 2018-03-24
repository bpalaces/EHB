package junit_tests;

import org.junit.Test;

import ehb.Button;
import junit.framework.TestCase;

public class ButtonTest extends TestCase
{
  private static InitEngine _init;
  // This method is called before every single test!!!
  @Override
  public void setUp() throws Exception {
    // Do this so that the engine is only initialized once
    if (_init == null) {
      _init.init();
    }
  }
  
  @Test
  public void testWasPressed()
  {
    RunTest test = new RunTest();
    test.execute(() ->{     
      Button button = new Button();
      assertEquals(false, button.wasPressed());
    });
  }

}
