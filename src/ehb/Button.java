package ehb;

import interfaces.ButtonInterface;

public class Button
{


  private boolean _previousState = false;

  public boolean wasPressed()
  {
      if(_previousState != ButtonInterface.isDown())
      {
        _previousState = ButtonInterface.isDown();
        return true;
      }
      return false;
  }
  
  public long getDeltaT()
  {
    return deltaT;
  }

}
