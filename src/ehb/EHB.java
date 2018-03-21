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
    Brake brake = new Brake();
    Alarm alarm = new Alarm();
    Motion motion = new Motion();
    Button button = new Button();
    _rules = new Rules();
    _events = new Events(brake, motion, button);
    _actions = new Actions(brake, alarm);
    _currentState = StateTypes.MOVING_DISENGAGED; // I assume this it the correct starting state?
  }


  public void update()
  {
      _inScopeStateChanges = _rules.whatEvents(_currentState); // Get in scope potential state changes.
      for (EventTypes event : _inScopeStateChanges.keySet()) // Check if any of the events have occurred.
      {
        if(_events.didEventOccur(event)) // Event occurred.
        {
          _inScopeActions = _rules.whatActions(event, _currentState); // Get actions to perform.
          for(ActionTypes action : _inScopeActions) _actions.execute(action); // Execute actions.
          _currentState = _inScopeStateChanges.get(event); // Perform state transition.
          break;
        }
      }
  }
}
