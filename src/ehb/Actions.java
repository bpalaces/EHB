package ehb;

import java.util.HashMap;


public class Actions
{
	private static HashMap<ActionTypes, IAction> _actions;

	// TODO: Update lambdas. 
	static 
	{
		_actions = new HashMap<>() {
			{
				put(ActionTypes.NO_OP , () -> System.out.println("No op"));
				put(ActionTypes.SET_COLOR_ORANGE , () -> System.out.println("Set color orange."));
				put(ActionTypes.SET_COLOR_BLUE , () -> System.out.println("Set color blue"));
				put(ActionTypes.SET_COLOR_RED , () -> System.out.println("set color red"));
				put(ActionTypes.PLAY_ENGAGED_SOUND , () -> System.out.println("play engaged sound"));
				put(ActionTypes.PLAY_DISENGAGED_SOUND , () -> System.out.println("play disengaged sound"));
				put(ActionTypes.APPLY_TEN_PERCENT_FORCE_INCREASE , () -> System.out.println("apply 10% pressure increase"));
				put(ActionTypes.DISENGAGE_EHB , () -> System.out.println("disengage ehb."));
				put(ActionTypes.FULLY_ENGAGE_EHB , () -> System.out.println("engage ehb."));
			}
		};

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
