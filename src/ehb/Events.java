package ehb;

import interfaces.GearTypes;

import java.util.HashMap;

public class Events
{
	private Motion _motion;
	private Brake _brake;
	private Button _button;
    @SuppressWarnings("serial")
	private HashMap<EventTypes, IEvent> _events = new HashMap<>() {
		{
		    put(EventTypes.NO_OP, () -> true);
			put(EventTypes.SHIFT_OUT_OF_PARK , () -> _motion.getCurrentGear() != GearTypes.PARK && _motion.getPreviousGear() == GearTypes.PARK);
			put(EventTypes.SHIFT_INTO_PARK , () -> _motion.getCurrentGear() == GearTypes.PARK && _motion.getPreviousGear() != GearTypes.PARK);
			put(EventTypes.BUTTON_SHORT_PRESS , () -> _button.getStatus() == ButtonStatus.SHORT_PRESS);
			put(EventTypes.BUTTON_LONG_PRESS , () -> _button.getStatus() == ButtonStatus.LONG_PRESS);
			put(EventTypes.SPEED_ZERO , () -> _motion.getSpeed() == 0.0);
			put(EventTypes.SPEED_GREATER_THAN_ZERO , () -> _motion.getSpeed() > 0.0);
			put(EventTypes.EHB_FULLY_DISENGAGED , () -> _brake.getPressure() == 0.0);
			put(EventTypes.EHB_FULLY_ENGAGED , () -> _brake.getPressure() == 100.0);
			put(EventTypes.BRAKE_ENGAGED_IN_PARK , () -> _brake.getPressure() > 0.0 && _motion.getCurrentGear() == GearTypes.PARK);
			put(EventTypes.BRAKE_DISENGAGED_IN_PARK , () -> _brake.getPressure() == 0.0 && _motion.getCurrentGear() == GearTypes.PARK);
		}
	};

	Events(Brake brake, Motion motion, Button button)
	{
		_brake = brake;
		_motion = motion;
		_button = button;
	}

	public boolean didEventOccur(EventTypes event)
	{
		if(_events.get(event) == null) throw new IllegalArgumentException("Invalid Event.");
		return _events.get(event).check();
	}

	@FunctionalInterface
	public interface IEvent{
		boolean check();
	}
}