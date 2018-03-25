package ehb;

import interfaces.ButtonColorTypes;
import java.util.HashMap;

/**
 * Class that deals with performing actions in a given state once a
 * given event occurs.
 */
public class Actions
{
	private Brake _brake;
	private Alarm _alarm;
	private Motion _motion;
	private PressureProfile _pressureProfile;
	/* Mapping of values in the ActionTypes enum to wrapped lambda functions performing
	* said actions. */
    @SuppressWarnings("serial")
	private HashMap<ActionTypes, IAction> _actions = new HashMap<ActionTypes, IAction>() {
		{
			put(ActionTypes.NO_OP , () -> {});
			put(ActionTypes.SET_COLOR_ORANGE , () -> _alarm.setColor(ButtonColorTypes.ORANGE));
			put(ActionTypes.SET_COLOR_BLUE , () -> _alarm.setColor(ButtonColorTypes.BLUE));
			put(ActionTypes.SET_COLOR_RED , () ->  _alarm.setColor(ButtonColorTypes.RED));
			put(ActionTypes.PLAY_ENGAGED_SOUND , () -> _alarm.play(ValidSoundTypes.ENGAGED));
			put(ActionTypes.PLAY_DISENGAGED_SOUND , () -> _alarm.play(ValidSoundTypes.DISENGAGED));
			put(ActionTypes.UPDATE_APPLIED_FORCE , () -> _brake.setPressure(new Pressure(_pressureProfile.getNewPressure())));
            put(ActionTypes.PLAY_CONTINUOUS_ALERT, () -> _alarm.play(ValidSoundTypes.WARNING));
			put(ActionTypes.DISENGAGE_EHB , () -> _brake.setPressure(new Pressure(0.0)));
			put(ActionTypes.FULLY_ENGAGE_EHB , () -> _brake.setPressure(new Pressure (100.0)));
		}
	};

	public Actions(Brake brake, Alarm alarm, Motion motion)
	{
		_brake = brake;
		_alarm = alarm;
		_motion = motion;
		_pressureProfile = new PressureProfile(motion);
	}

	/**
	 * Function that takes an action executes the corresponding wrapped lambda
	 * function.
	 *
	 * @param instruction The action to perform.
	 */
	public void execute(ActionTypes instruction)
	{
		if(_actions.get(instruction) == null) throw new IllegalArgumentException("Invalid Action.");
		_actions.get(instruction).perform();
	}

	/**
	 * Functional interface to wrap lambda functions so they can be held
	 * inside of a map.
	 */
	@FunctionalInterface
	public interface IAction{
		void perform();
	}
}
