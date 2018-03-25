package ehb;

import interfaces.ButtonInterface;

/**
 * Virtual abstraction of the click functionality of the
 * button interface.
 */
public class Button
{
  private boolean _previousState = false;

    /**
     * Note: The button being recently clicked is defined when
     * isDown switches between return values.
     *
     * @return True if the button the button has been recently
     * clicked false otherwise.
     */
  public boolean wasPressed()
  {
      if(_previousState != ButtonInterface.isDown())
      {
        _previousState = ButtonInterface.isDown();
        return true;
      }
      return false;
  }
}
