package ehb;

import interfaces.ButtonColorTypes;

import java.util.HashMap;

public class Actions
{
	private Brake _brake;
	private Alarm _alarm;
	private HashMap<ActionTypes, IAction> _actions = new HashMap<>() {
		{
			put(ActionTypes.NO_OP , () -> {});
			put(ActionTypes.SET_COLOR_ORANGE , () -> _alarm.setColor(ButtonColorTypes.ORANGE));
			put(ActionTypes.SET_COLOR_BLUE , () -> _alarm.setColor(ButtonColorTypes.BLUE));
			put(ActionTypes.SET_COLOR_RED , () ->  _alarm.setColor(ButtonColorTypes.RED));
			put(ActionTypes.PLAY_ENGAGED_SOUND , () -> _alarm.play("engaging"));
			put(ActionTypes.PLAY_DISENGAGED_SOUND , () -> _alarm.play("engaging"));
			put(ActionTypes.APPLY_TEN_PERCENT_FORCE_INCREASE , () -> _brake.setPressure(_brake.getPressure() + 10.0));
			put(ActionTypes.DISENGAGE_EHB , () -> _brake.setPressure(0.0));
			put(ActionTypes.FULLY_ENGAGE_EHB , () -> _brake.setPressure(100.0));
		}
	};

	Actions(Brake brake, Alarm alarm)
	{
		_brake = brake;
		_alarm = alarm;
	}

	public void execute(ActionTypes instruction)
	{
		if(_actions.get(instruction) == null) throw new IllegalArgumentException("Invalid Action.");
		_actions.get(instruction).perform();
	}

	@FunctionalInterface
	public interface IAction{
		public void perform();
	}
}
