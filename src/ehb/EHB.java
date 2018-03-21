package ehb;

import interfaces.ButtonColorTypes;
import interfaces.ButtonInterface;

public class EHB
{
  /**
   *
   * TODO: Implement me
   *
   * Method called by the engine with each simulation tick.
   * Do not place any infinite loops in this method.
   *
   */

  private Rules _rules;
  private Events _events;
  private Actions _actions;

  public EHB()
  {
    Brake brake = new Brake();
    Alarm alarm = new Alarm();
    Motion motion = new Motion();
    Button button = new Button();
    _rules = new Rules();
    _events = new Events(brake, motion, button);
    _actions = new Actions(brake, alarm);
  }




  public void update()
  {

  }
}
