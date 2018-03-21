package ehb;

import java.util.*;
import java.util.stream.*;
import java.util.AbstractMap.*;

public class Rules
{
    private Map<StateEventBinding, List<ActionTypes>> _actionsMap;
    private Map<StateTypes, Map<EventTypes, StateTypes>> _eventsMap;

    private Map.Entry<StateEventBinding, List<ActionTypes>> entry(StateTypes state, EventTypes event, ActionTypes... actions)
    {
        return new AbstractMap.SimpleEntry<>(new StateEventBinding(state, event), Arrays.asList(actions));
    }
    private Map.Entry<StateTypes, Map<EventTypes, StateTypes>> entry(StateTypes currState, List<EventTypes> events, List<StateTypes> states)
    {
        return new AbstractMap.SimpleEntry<>(currState, IntStream.range(0, events.size()).boxed().collect(Collectors.toMap(events::get, states::get)));
    }
    
    {
        _actionsMap = Collections.unmodifiableMap(Stream.of
            (
                entry(StateTypes.PARKED_ENGAGED, EventTypes.BUTTON_SHORT_PRESS, ActionTypes.SET_COLOR_BLUE, ActionTypes.DISENGAGE_EHB)
            ).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))
        );

        _eventsMap = Collections.unmodifiableMap(Stream.of
            (
                entry(StateTypes.PARKED_ENGAGED, Arrays.asList(EventTypes.BUTTON_SHORT_PRESS, EventTypes.SHIFT_OUT_OF_PARK), Arrays.asList(StateTypes.PARKED_DISENGAGED, StateTypes.STOPPED_ENGAGED))
            ).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))
        );

    }


    public Map<EventTypes, StateTypes> whatEvents(StateTypes currentState)
    {
        return _eventsMap.get(currentState);
    }

    public List<ActionTypes> whatActions(EventTypes currentEvent, StateTypes currentState) {
        return _actionsMap.get(new StateEventBinding(currentState, currentEvent));
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
            return ((StateEventBinding)obj)._state == this._state && ((StateEventBinding)obj)._event == this._event;
        }
    }

}