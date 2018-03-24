package ehb;

import java.util.List;
import java.util.Map;

public class EHB
{
  private Rules _rules;
  private Events _events;
  private Actions _actions;
  private StateTypes _currentState;
  private Map<EventTypes, StateTypes> _inScopeStateChanges;
  private List<ActionTypes> _inScopeActions;

  public EHB()
  {
    Motion motion = new Motion();
    Button button = new Button();
    Brake brake = new Brake();
    Alarm alarm = new Alarm();
    _rules = new Rules();
    _events = new Events(motion, button);
    _actions = new Actions(brake, alarm, motion);
    _currentState = StateTypes.MOVING_DISENGAGED; // I assume this it the correct starting state?
  }


  public void update()
  {
      System.out.println("State: " + _currentState.toString());
      _inScopeStateChanges = _rules.whatEvents(_currentState); // Get in scope potential state changes.
      for (EventTypes event : _inScopeStateChanges.keySet()) // Check if any of the events have occurred.
      {
        if(_events.didEventOccur(event)) // Event occurred.
        {
          _inScopeActions = _rules.whatActions(event, _currentState); // Get actions to perform.
          for(ActionTypes action : _inScopeActions) _actions.execute(action); // Execute actions.
          _currentState = _inScopeStateChanges.get(event); // Perform state transition.
          if(!event.equals(EventTypes.NO_OP)) break;
        }
      }
  }
}
