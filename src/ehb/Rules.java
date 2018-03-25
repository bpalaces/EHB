package ehb;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Class that contains all the rules for the state controller.
 * There are two main sets of rules:
 * 1. Rules that define what actions should be taken when an event occurs in a given state.
 * 2. Events that should be listened for in a given state and the state transformations that
 * should occur giving any of said Events occur.
 */
public class Rules
{
    // Continual actions defined in the list below will get executed with each run of the update loop.
    // REGARDLESS if we have the same state/event binding as before. All others will be acted upon
    // once per state/event binding. Events in the event/state bindings that correspond to continual actions
    // should be no-ops.
    private List<ActionTypes> _continualActions = Arrays.asList(ActionTypes.UPDATE_APPLIED_FORCE, ActionTypes.PLAY_CONTINUOUS_ALERT);
    private StateEventBinding _previous;

    // Mapping State/Events bindings to actions to be performed.
    private Map<StateEventBinding, List<ActionTypes>> _actionsMap = Collections.unmodifiableMap(Stream.of
            (
                    entry(StateTypes.PARKED_ENGAGED, EventTypes.BUTTON_PRESS, ActionTypes.SET_COLOR_BLUE, ActionTypes.DISENGAGE_EHB, ActionTypes.PLAY_DISENGAGED_SOUND),
                    entry(StateTypes.PARKED_ENGAGED, EventTypes.SHIFT_OUT_OF_PARK, ActionTypes.NO_OP),
                    entry(StateTypes.PARKED_DISENGAGED, EventTypes.BUTTON_PRESS, ActionTypes.SET_COLOR_RED, ActionTypes.FULLY_ENGAGE_EHB, ActionTypes.PLAY_ENGAGED_SOUND),
                    entry(StateTypes.PARKED_DISENGAGED, EventTypes.SHIFT_OUT_OF_PARK, ActionTypes.NO_OP),
                    entry(StateTypes.STOPPED_DISENGAGED, EventTypes.SHIFT_INTO_PARK, ActionTypes.NO_OP),
                    entry(StateTypes.STOPPED_DISENGAGED, EventTypes.SPEED_GREATER_THAN_ZERO, ActionTypes.NO_OP),
                    entry(StateTypes.STOPPED_DISENGAGED, EventTypes.BUTTON_PRESS, ActionTypes.FULLY_ENGAGE_EHB, ActionTypes.PLAY_ENGAGED_SOUND, ActionTypes.SET_COLOR_RED),
                    entry(StateTypes.MOVING_DISENGAGED, EventTypes.BUTTON_PRESS, ActionTypes.SET_COLOR_ORANGE, ActionTypes.PLAY_ENGAGED_SOUND),
                    entry(StateTypes.MOVING_DISENGAGED, EventTypes.SPEED_ZERO, ActionTypes.NO_OP),
                    entry(StateTypes.MOVING_ENGAGED, EventTypes.SPEED_ZERO, ActionTypes.NO_OP),
                    entry(StateTypes.MOVING_ENGAGED, EventTypes.BUTTON_PRESS, ActionTypes.DISENGAGE_EHB, ActionTypes.PLAY_DISENGAGED_SOUND, ActionTypes.SET_COLOR_BLUE),
                    entry(StateTypes.MOVING_ENGAGED, EventTypes.NO_OP, ActionTypes.UPDATE_APPLIED_FORCE),
                    entry(StateTypes.STOPPED_ENGAGED, EventTypes.SPEED_GREATER_THAN_ZERO, ActionTypes.NO_OP),
                    entry(StateTypes.STOPPED_ENGAGED, EventTypes.SHIFT_INTO_PARK, ActionTypes.SET_COLOR_RED, ActionTypes.FULLY_ENGAGE_EHB),
                    entry(StateTypes.STOPPED_ENGAGED, EventTypes.BUTTON_PRESS, ActionTypes.DISENGAGE_EHB, ActionTypes.PLAY_DISENGAGED_SOUND, ActionTypes.SET_COLOR_BLUE),
                    entry(StateTypes.STOPPED_ENGAGED, EventTypes.NO_OP, ActionTypes.PLAY_CONTINUOUS_ALERT)
            ).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))
    );
    // Mapping of states to maps of events to listen to and the states to transition to
    // if any of said events occur.
    private Map<StateTypes, Map<EventTypes, StateTypes>> _eventsMap = Collections.unmodifiableMap(Stream.of
            (
                    entry(StateTypes.PARKED_ENGAGED,
                            Arrays.asList(EventTypes.BUTTON_PRESS, EventTypes.SHIFT_OUT_OF_PARK),
                            Arrays.asList(StateTypes.PARKED_DISENGAGED, StateTypes.STOPPED_ENGAGED)),
                    entry(StateTypes.PARKED_DISENGAGED,
                            Arrays.asList(EventTypes.BUTTON_PRESS, EventTypes.SHIFT_OUT_OF_PARK),
                            Arrays.asList(StateTypes.PARKED_ENGAGED, StateTypes.STOPPED_DISENGAGED)),
                    entry(StateTypes.STOPPED_DISENGAGED,
                            Arrays.asList(EventTypes.SHIFT_INTO_PARK, EventTypes.SPEED_GREATER_THAN_ZERO, EventTypes.BUTTON_PRESS),
                            Arrays.asList(StateTypes.PARKED_DISENGAGED, StateTypes.MOVING_DISENGAGED, StateTypes.STOPPED_ENGAGED)),
                    entry(StateTypes.MOVING_DISENGAGED,
                            Arrays.asList(EventTypes.BUTTON_PRESS, EventTypes.SPEED_ZERO),
                            Arrays.asList(StateTypes.MOVING_ENGAGED, StateTypes.STOPPED_DISENGAGED)),
                    entry(StateTypes.MOVING_ENGAGED,
                            Arrays.asList(EventTypes.BUTTON_PRESS, EventTypes.SPEED_ZERO, EventTypes.NO_OP),
                            Arrays.asList(StateTypes.MOVING_DISENGAGED, StateTypes.STOPPED_ENGAGED, StateTypes.MOVING_ENGAGED)),
                    entry(StateTypes.STOPPED_ENGAGED,
                            Arrays.asList(EventTypes.SHIFT_INTO_PARK, EventTypes.SPEED_GREATER_THAN_ZERO, EventTypes.BUTTON_PRESS, EventTypes.NO_OP),
                            Arrays.asList(StateTypes.PARKED_ENGAGED, StateTypes.MOVING_ENGAGED, StateTypes.STOPPED_DISENGAGED, StateTypes.STOPPED_ENGAGED))
            ).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))
    );

    /**
     *
     * @param currentState The current state we are in.
     * @return Maping of events to listen to and the states to transition if any of said events occur.
     */
    public Map<EventTypes, StateTypes> whatEvents(StateTypes currentState)
    {
        if(_eventsMap.get(currentState) == null) throw new IllegalArgumentException("Invalid State.");
        return _eventsMap.get(currentState);
    }

    /**
     *
     * @param currentEvent Event that has just occurred
     * @param currentState State we are in.
     * @return Actions to take given the event that has occurred in the state we are in.
     */
    public List<ActionTypes> whatActions(EventTypes currentEvent, StateTypes currentState)
    {
        StateEventBinding binding = new StateEventBinding(currentState,currentEvent);
        List<ActionTypes> actions = _actionsMap.get(binding);
        if(actions == null) throw new IllegalArgumentException("Invalid State/Event Binding.");
        if(binding.equals(_previous))
        {
            List<ActionTypes> continualActionIntersection = actions.stream().filter(_continualActions::contains).collect(Collectors.toList());
            // We have already performed the actions for this binding, and no continual actions are specified.
            if(continualActionIntersection.size() == 0) return new ArrayList<>();
            // Otherwise we have continual actions that should keep getting performed.
            else actions = continualActionIntersection;
        }
        _previous = binding;
        return actions;
    }

    /**
     *
     * @param state Current state.
     * @param event Current event
     * @param actions Variadic amount of actions.
     * @return New entry in the _actionsMap.
     */
    private Map.Entry<StateEventBinding, List<ActionTypes>> entry(StateTypes state, EventTypes event, ActionTypes... actions)
    {
        return new AbstractMap.SimpleEntry<>(new StateEventBinding(state, event), Arrays.asList(actions));
    }

    /**
     *
     * @param currState Current state
     * @param events List of in scope events.
     * @param states List of in scope state transitions.
     * @return New entry in _eventsMap
     */
    private Map.Entry<StateTypes, Map<EventTypes, StateTypes>> entry(StateTypes currState, List<EventTypes> events, List<StateTypes> states)
    {
        return new AbstractMap.SimpleEntry<>(currState, IntStream.range(0, events.size()).boxed().collect(Collectors.toMap(events::get, states::get)));
    }

    /**
     * Bindings for State and Event pairings, basically a tuple.
     */
    private class StateEventBinding
    {

        private StateTypes _state;
        private EventTypes _event;

        StateEventBinding(StateTypes state, EventTypes event)
        {
            _state = state;
            _event = event;
        }

        @Override
        public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_state == null) ? 0 : _state.hashCode());
        result = prime * result + ((_event == null) ? 0 : _event.hashCode());
        return result;
        }
     
        @Override
        public boolean equals(Object obj) {

            if(obj == null) return false;
            if(obj == this) return true;
            if(getClass() != obj.getClass()) return false;
            StateEventBinding binding = (StateEventBinding) obj;
            return (binding._state == this._state && binding._event == this._event);
        }
    }

}