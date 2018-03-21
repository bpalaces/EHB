package ehb;

import java.util.function.*;
import java.util.*;

public class Events
{
	private static HashMap<EventTypes, IEvent> events;

	// TODO: Update lambdas. 
	static 
	{
		events = new HashMap<>() {
			{
				put(EventTypes.SHIFT_OUT_OF_PARK , () -> 1 > 0);
				put(EventTypes.SHIFT_INTO_PARK , () -> 1 > 0);
				put(EventTypes.BUTTON_SHORT_PRESS , () -> 1 > 0);
				put(EventTypes.BUTTON_LONG_PRESS , () -> 1 > 0);
				put(EventTypes.SPEED_ZERO , () -> 1 > 0);
				put(EventTypes.SPEED_GREATER_THAN_ZERO , () -> 1 > 0);
				put(EventTypes.EHB_FULLY_DISENGAGED , () -> 1 > 0);
				put(EventTypes.EHB_FULLY_ENGAGED , () -> 1 > 0);
				put(EventTypes.PLAY_DISENGAGED_SOUND , () -> 1 > 0);
				put(EventTypes.BRAKE_ENGAGED_IN_PARK , () -> 1 > 0);
				put(EventTypes.BRAKE_DISENGAGED_IN_PARK , () -> 1 > 0);
			}
		};
	}


	public boolean didEventOccur(EventTypes event)
	{
		return events.get(event).check();
	}

	@FunctionalInterface
	public interface IEvent{
		public boolean check();
	}
}