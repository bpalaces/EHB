package ehb;

import interfaces.ButtonColorTypes;
import java.util.HashMap;


public class Actions
{
	private Brake _brake;
	private Alarm _alarm;
	private Motion _motion;
	private PressureProfile _pressureProfile;
    @SuppressWarnings("serial")
	private HashMap<ActionTypes, IAction> _actions = new HashMap<ActionTypes, IAction>() {
		{
			put(ActionTypes.NO_OP , () -> {});
			put(ActionTypes.SET_COLOR_ORANGE , () -> _alarm.setColor(ButtonColorTypes.ORANGE));
			put(ActionTypes.SET_COLOR_BLUE , () -> _alarm.setColor(ButtonColorTypes.BLUE));
			put(ActionTypes.SET_COLOR_RED , () ->  _alarm.setColor(ButtonColorTypes.RED));
			put(ActionTypes.PLAY_ENGAGED_SOUND , () -> _alarm.play("engaging"));
			put(ActionTypes.PLAY_DISENGAGED_SOUND , () -> _alarm.play("disengaging"));
			put(ActionTypes.UPDATE_APPLIED_FORCE , () -> _brake.setPressure(new Pressure(_pressureProfile.getNewPressure())));
            put(ActionTypes.PLAY_CONTINUOUS_ALERT, () -> _alarm.play("warning"));
			put(ActionTypes.DISENGAGE_EHB , () -> _brake.setPressure(new Pressure(0.0)));
			put(ActionTypes.FULLY_ENGAGE_EHB , () -> _brake.setPressure(new Pressure (100.0)));
		}
	};

	Actions(Brake brake, Alarm alarm, Motion motion)
	{
		_brake = brake;
		_alarm = alarm;
		_motion = motion;
		_pressureProfile = new PressureProfile(motion);
	}

	public void execute(ActionTypes instruction)
	{
	    System.out.println("performing action: " + instruction.toString());
		if(_actions.get(instruction) == null) throw new IllegalArgumentException("Invalid Action.");
		_actions.get(instruction).perform();
	}

	@FunctionalInterface
	public interface IAction{
		void perform();
	}
}
