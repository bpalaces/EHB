package ehb;

import java.util.List;
import java.util.Map;

/**
 * State controller that drives all control logic.
 */
public class EHB
{
  private Rules _rules;
  private Events _events;
  private Actions _actions;
  private StateTypes _currentState;
  // Map that keeps track of the current in scope events and potential
  // state changes given the current state we are in.
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
    // Start in the moving disengaged state, regardless of how the
    // simulation is started the correct state will be moved into immediately if it differs.
    _currentState = StateTypes.MOVING_DISENGAGED;
  }


  /**
   * Main EHB algorithm, called up to 60 time a second by the engine.
   * Algorithm works as follows:
   * 1. Get a list of all in scope events and their corresponding state changes
   * for the current state we are in.
   * 2. For each of the in scope events check if any have occurred.
   * 3. If an event has occurred then get the actions that should be performed
   * given the event that has occurred in our current state.
   * 4. Execute all relevent actions.
   * 5. Repeat.
   *
   */
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
          if(!event.equals(EventTypes.NO_OP)) break;
        }
      }
  }
}
