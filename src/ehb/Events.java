package ehb;

import interfaces.GearTypes;

import java.util.HashMap;

/**
 * Class used to check if events have occurred by querying components on the
 * virtual layer.
 */
public class Events
{
	private Motion _motion;
	private Button _button;
	// Mapping of EventTypes to wrapped lambdas that check for said events.
    @SuppressWarnings("serial")
	private HashMap<EventTypes, IEvent> _events = new HashMap<EventTypes, IEvent>() {
		{
		    put(EventTypes.NO_OP, () -> true);
			put(EventTypes.SHIFT_OUT_OF_PARK , () -> _motion.getCurrentGear() != GearTypes.PARK && _motion.getPreviousGear() == GearTypes.PARK);
			put(EventTypes.SHIFT_INTO_PARK , () -> _motion.getCurrentGear() == GearTypes.PARK && _motion.getPreviousGear() != GearTypes.PARK);
			put(EventTypes.BUTTON_PRESS , () -> _button.wasPressed());
			put(EventTypes.SPEED_ZERO , () -> _motion.getSpeed() == 0.0);
			put(EventTypes.SPEED_GREATER_THAN_ZERO , () -> _motion.getSpeed() > 0.0);
		}
	};

    public Events(Motion motion, Button button)
	{
		_motion = motion;
		_button = button;
	}

	/**
	 * Check if an event has occurred.
	 *
	 * @param event Event to check for.
	 * @return True if the event occurred, false otherwise.
	 */
	public boolean didEventOccur(EventTypes event)
	{
		if(_events.get(event) == null) throw new IllegalArgumentException("Invalid Event.");
		return _events.get(event).check();
	}

	// Functional interface to wrap lambdas so they can be placed
	// in a map.
	@FunctionalInterface
	public interface IEvent{
		boolean check();
	}
}