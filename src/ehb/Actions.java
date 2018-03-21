package ehb;

import java.util.function.*;
import java.util.*;


private HashMap<ActionType, IAction> actions;

// TODO: Update lambdas. 
static 
{
	actions = new HashMap<>();
	actions.put(ActionType.NO_OP , () -> System.out.println("No op"));
	actions.put(ActionType.SET_COLOR_ORANGE , () -> System.out.println("Set color orange."));
	actions.put(ActionType.SET_COLOR_BLUE , () -> System.out.println("Set color blue"));
	actions.put(ActionType.SET_COLOR_RED , () -> System.out.println("set color red"));
	actions.put(ActionType.PLAY_ENGAGED_SOUND , () -> System.out.println("play engaged sound"));
	actions.put(ActionType.PLAY_DISENGAGED_SOUND , () -> System.out.println("play disengaged sound"));
	actions.put(ActionType.APPLY_TEN_PERCENT_FORCE_INCREASE , () -> System.out.println("apply 10% pressure increase"));
	actions.put(ActionType.DISENGAGED_EHB , () -> System.out.println("disengage ehb."));
	actions.put(ActionType.FULLY_ENGAGE_EHB , () -> System.out.println("engage ehb."));
}


public static void execute(ActionType instruction)
{
	actions.get(instruction).perform();
}

@FunctionalInterface
public interface IAction{
	public void perform();
}
