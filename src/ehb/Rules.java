package ehb;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Rules
{
    private Map<StateEventBinding, List<ActionTypes>> _actionsMap = Collections.unmodifiableMap(Stream.of
            (
                    entry(StateTypes.PARKED_ENGAGED, EventTypes.BUTTON_SHORT_PRESS, ActionTypes.SET_COLOR_BLUE, ActionTypes.DISENGAGE_EHB),
                    entry(StateTypes.PARKED_ENGAGED, EventTypes.SHIFT_OUT_OF_PARK, ActionTypes.PLAY_ENGAGED_SOUND),
                    entry(StateTypes.PARKED_DISENGAGED, EventTypes.BUTTON_SHORT_PRESS, ActionTypes.SET_COLOR_RED, ActionTypes.FULLY_ENGAGE_EHB),
                    entry(StateTypes.PARKED_DISENGAGED, EventTypes.SHIFT_OUT_OF_PARK, ActionTypes.NO_OP),
                    entry(StateTypes.STOPPED_DISENGAGED, EventTypes.SHIFT_INTO_PARK, ActionTypes.NO_OP),
                    entry(StateTypes.STOPPED_DISENGAGED, EventTypes.SPEED_GREATER_THAN_ZERO, ActionTypes.NO_OP),
                    entry(StateTypes.STOPPED_DISENGAGED, EventTypes.BUTTON_LONG_PRESS, ActionTypes.FULLY_ENGAGE_EHB, ActionTypes.PLAY_ENGAGED_SOUND),
                    entry(StateTypes.MOVING_ENGAGING, EventTypes.BUTTON_LONG_PRESS, ActionTypes.DISENGAGE_EHB, ActionTypes.PLAY_DISENGAGED_SOUND),
                    entry(StateTypes.MOVING_ENGAGING, EventTypes.EHB_FULLY_ENGAGED, ActionTypes.SET_COLOR_RED, ActionTypes.PLAY_ENGAGED_SOUND),
                    entry(StateTypes.MOVING_DISENGAGED, EventTypes.BUTTON_LONG_PRESS, ActionTypes.SET_COLOR_ORANGE),
                    entry(StateTypes.MOVING_DISENGAGED, EventTypes.SPEED_ZERO, ActionTypes.NO_OP),
                    entry(StateTypes.MOVING_ENGAGED, EventTypes.SPEED_ZERO, ActionTypes.NO_OP),
                    entry(StateTypes.MOVING_ENGAGED, EventTypes.BUTTON_LONG_PRESS, ActionTypes.DISENGAGE_EHB, ActionTypes.SET_COLOR_RED),
                    entry(StateTypes.MOVING_DISENGAGING, EventTypes.EHB_FULLY_DISENGAGED, ActionTypes.PLAY_DISENGAGED_SOUND, ActionTypes.SET_COLOR_BLUE),
                    entry(StateTypes.MOVING_DISENGAGING, EventTypes.BUTTON_LONG_PRESS, ActionTypes.SET_COLOR_ORANGE),
                    entry(StateTypes.STOPPED_ENGAGED, EventTypes.SPEED_GREATER_THAN_ZERO, ActionTypes.NO_OP),
                    entry(StateTypes.STOPPED_ENGAGED, EventTypes.SHIFT_INTO_PARK,ActionTypes.PLAY_ENGAGED_SOUND),
                    entry(StateTypes.STOPPED_ENGAGED, EventTypes.BUTTON_LONG_PRESS, ActionTypes.DISENGAGE_EHB, ActionTypes.PLAY_DISENGAGED_SOUND, ActionTypes.SET_COLOR_BLUE)
            ).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))
    );
    private Map<StateTypes, Map<EventTypes, StateTypes>> _eventsMap = Collections.unmodifiableMap(Stream.of
            (
                    entry(StateTypes.PARKED_ENGAGED,
                            Arrays.asList(EventTypes.BUTTON_SHORT_PRESS, EventTypes.SHIFT_OUT_OF_PARK),
                            Arrays.asList(StateTypes.PARKED_DISENGAGED, StateTypes.STOPPED_ENGAGED)),
                    entry(StateTypes.PARKED_DISENGAGED,
                            Arrays.asList(EventTypes.BUTTON_SHORT_PRESS, EventTypes.SHIFT_OUT_OF_PARK),
                            Arrays.asList(StateTypes.PARKED_ENGAGED, StateTypes.STOPPED_DISENGAGED)),
                    entry(StateTypes.STOPPED_DISENGAGED,
                            Arrays.asList(EventTypes.SHIFT_INTO_PARK, EventTypes.SPEED_GREATER_THAN_ZERO, EventTypes.BUTTON_LONG_PRESS),
                            Arrays.asList(StateTypes.PARKED_DISENGAGED, StateTypes.MOVING_DISENGAGED, StateTypes.STOPPED_ENGAGED)),
                    entry(StateTypes.MOVING_ENGAGING,
                            Arrays.asList(EventTypes.BUTTON_LONG_PRESS, EventTypes.EHB_FULLY_ENGAGED),
                            Arrays.asList(StateTypes.MOVING_DISENGAGING, StateTypes.MOVING_ENGAGED)),
                    entry(StateTypes.MOVING_DISENGAGED,
                            Arrays.asList(EventTypes.BUTTON_LONG_PRESS, EventTypes.SPEED_ZERO),
                            Arrays.asList(StateTypes.MOVING_ENGAGING, StateTypes.STOPPED_ENGAGED)),
                    entry(StateTypes.MOVING_ENGAGED,
                            Arrays.asList(EventTypes.BUTTON_LONG_PRESS, EventTypes.SPEED_ZERO),
                            Arrays.asList(StateTypes.MOVING_DISENGAGING, StateTypes.STOPPED_ENGAGED)),
                    entry(StateTypes.MOVING_DISENGAGING,
                            Arrays.asList(EventTypes.BUTTON_LONG_PRESS, EventTypes.EHB_FULLY_DISENGAGED),
                            Arrays.asList(StateTypes.MOVING_ENGAGING, StateTypes.MOVING_DISENGAGED)),
                    entry(StateTypes.STOPPED_ENGAGED,
                            Arrays.asList(EventTypes.SHIFT_INTO_PARK, EventTypes.SPEED_GREATER_THAN_ZERO, EventTypes.BUTTON_LONG_PRESS),
                            Arrays.asList(StateTypes.PARKED_DISENGAGED, StateTypes.MOVING_ENGAGED, StateTypes.STOPPED_DISENGAGED))
            ).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))
    );

    public Map<EventTypes, StateTypes> whatEvents(StateTypes currentState)
    {
        if(_eventsMap.get(currentState) == null) throw new IllegalArgumentException("Invalid State.");
        return _eventsMap.get(currentState);
    }

    public List<ActionTypes> whatActions(EventTypes currentEvent, StateTypes currentState)
    {
        StateEventBinding binding = new StateEventBinding(currentState,currentEvent);
        if(_actionsMap.get(binding) == null) throw new IllegalArgumentException("Invalid State/Event Binding.");
        return _actionsMap.get(new StateEventBinding(currentState, currentEvent));
    }

    private Map.Entry<StateEventBinding, List<ActionTypes>> entry(StateTypes state, EventTypes event, ActionTypes... actions)
    {
        return new AbstractMap.SimpleEntry<>(new StateEventBinding(state, event), Arrays.asList(actions));
    }
    private Map.Entry<StateTypes, Map<EventTypes, StateTypes>> entry(StateTypes currState, List<EventTypes> events, List<StateTypes> states)
    {
        return new AbstractMap.SimpleEntry<>(currState, IntStream.range(0, events.size()).boxed().collect(Collectors.toMap(events::get, states::get)));
    }

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