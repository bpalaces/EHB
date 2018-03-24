package ehb;

import interfaces.GearTypes;

import java.util.HashMap;

public class Events
{
	private Motion _motion;
	private Button _button;
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

    Events(Motion motion, Button button)
	{
		_motion = motion;
		_button = button;
	}


	public boolean didEventOccur(EventTypes event)
	{
	    System.out.println("Checking for event: " + event.toString());
		if(_events.get(event) == null) throw new IllegalArgumentException("Invalid Event.");
		return _events.get(event).check();
	}

	@FunctionalInterface
	public interface IEvent{
		boolean check();
	}
}