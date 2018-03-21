package ehb;

import java.util.function.*;
import java.util.*;


private HashMap<EventType, IEvent> events;

// TODO: Update lambdas. 
static 
{
	events = new HashMap<>();
	events.put(EventType.SHIFT_OUT_OF_PARK , () -> 1 > 0);
	events.put(EventType.SHIFT_INTO_PARK , () -> 1 > 0);
	events.put(EventType.BUTTON_SHORT_PRESS , () -> 1 > 0);
	events.put(EventType.BUTTON_LONG_PRESS , () -> 1 > 0);
	events.put(EventType.SPEED_ZERO , () -> 1 > 0);
	events.put(EventType.SPEED_GREATER_THAN_ZERO , () -> 1 > 0);
	events.put(EventType.EHB_FULLY_DISENGAGED , () -> 1 > 0);
	events.put(EventType.PLAY_DISENGAGED_SOUND , () -> 1 > 0);
	events.put(EventType.BRAKE_ENGAGED_IN_PARK , () -> 1 > 0);
	events.put(EventType.BRAKE_DISENGAGED_IN_PARK , () -> 1 > 0);
}


public static boolean didEventOccur(EventType event)
{
	return events.get(event).check();
}

@FunctionalInterface
public interface IEvent{
	public boolean check();
}
