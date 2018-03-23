package ehb;

import interfaces.BrakeInterface;
import interfaces.ButtonColorTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Actions
{
	double currentPressure = 0;

	static TreeMap<Long, Double> goodPressureProfile;

	//The units for the profiler is miles per hour to kPascal
	static
	{
		goodPressureProfile = new TreeMap<Long, Double>();
		goodPressureProfile.put(Long.valueOf(20), 6.0);
		goodPressureProfile.put(Long.valueOf(25), 5.75);
		goodPressureProfile.put(Long.valueOf(30), 5.5);
		goodPressureProfile.put(Long.valueOf(35), 5.25);
		goodPressureProfile.put(Long.valueOf(40), 5.0);
		goodPressureProfile.put(Long.valueOf(45), 4.75);
		goodPressureProfile.put(Long.valueOf(50), 4.5);
		goodPressureProfile.put(Long.valueOf(55), 4.25);
		goodPressureProfile.put(Long.valueOf(60), 4.0);
		goodPressureProfile.put(Long.valueOf(65), 3.75);
		goodPressureProfile.put(Long.valueOf(70), 3.5);
		goodPressureProfile.put(Long.valueOf(75), 3.25);
		goodPressureProfile.put(Long.valueOf(80), 3.0);
		goodPressureProfile.put(Long.valueOf(85), 2.75);
		goodPressureProfile.put(Long.valueOf(90), 2.5);
		goodPressureProfile.put(Long.valueOf(95), 2.25);
		goodPressureProfile.put(Long.valueOf(100), 2.0);
		goodPressureProfile.put(Long.valueOf(105), 1.75);
		goodPressureProfile.put(Long.valueOf(110), 1.5);
		goodPressureProfile.put(Long.valueOf(115), 1.25);
		goodPressureProfile.put(Long.valueOf(120), 1.0);
		goodPressureProfile.put(Long.valueOf(125), 0.75);
		goodPressureProfile.put(Long.valueOf(130), 0.5);
		goodPressureProfile.put(Long.valueOf(135), 0.25);
		goodPressureProfile.put(Long.valueOf(140), 0.00);
	}

	//the car should crash on high speeds with the bad pressure profiler
	static TreeMap<Long, Double> badPressureProfile;

	static
	{
		badPressureProfile = new TreeMap<Long, Double>();
		badPressureProfile.put(Long.valueOf(20), 2.0);
		badPressureProfile.put(Long.valueOf(30), 2.5);
		badPressureProfile.put(Long.valueOf(40), 3.0);
		badPressureProfile.put(Long.valueOf(50), 3.5);
		badPressureProfile.put(Long.valueOf(60), 4.0);
		badPressureProfile.put(Long.valueOf(70), 4.5);
		badPressureProfile.put(Long.valueOf(80), 5.0);
		badPressureProfile.put(Long.valueOf(90), 5.5);
		badPressureProfile.put(Long.valueOf(100), 6.0);
	}

	Motion motionInstance = new Motion();
	double getNewPressure()
	{
		//This uses the max and low values of the tree map to get the closest value in the
		//pressure profile
		double _speed = motionInstance.getSpeed();
		Long key = Long.valueOf((int) _speed);
		Map.Entry<Long, Double> floor = goodPressureProfile.floorEntry(key);
		Map.Entry<Long, Double> ceiling = goodPressureProfile.ceilingEntry(key);


		double closestResult;
		if (floor != null && ceiling != null)
		{
			closestResult = (floor.getValue() + ceiling.getValue()) / 2.0;
		}
		else if (floor != null)
		{
			closestResult = floor.getValue();
		}
		else
		{
			closestResult = ceiling.getValue();
		}

		return lerpPressure((closestResult / 6.0) * 100);
	}

	double lerpPressure(double targetPressure)
	{
		double rate = 0.5;

		if (targetPressure > currentPressure)
		{
			this.currentPressure = currentPressure + rate;
			return this.currentPressure;
		}
		else
		{
			return this.currentPressure;
		}

	}

	private Brake _brake = new Brake();
	private Alarm _alarm = new Alarm();
    @SuppressWarnings("serial")
	private HashMap<ActionTypes, IAction> _actions = new HashMap<ActionTypes, IAction>() {
		{
			put(ActionTypes.NO_OP , () -> {});
			put(ActionTypes.SET_COLOR_ORANGE , () -> _alarm.setColor(ButtonColorTypes.ORANGE));
			put(ActionTypes.SET_COLOR_BLUE , () -> _alarm.setColor(ButtonColorTypes.BLUE));
			put(ActionTypes.SET_COLOR_RED , () ->  _alarm.setColor(ButtonColorTypes.RED));
			put(ActionTypes.PLAY_ENGAGED_SOUND , () -> _alarm.play("engaging"));
			put(ActionTypes.PLAY_DISENGAGED_SOUND , () -> _alarm.play("disengaging"));
			put(ActionTypes.UPDATE_APPLIED_FORCE , () -> _brake.setPressure(getNewPressure())); // Update with profile..
            put(ActionTypes.PLAY_CONTINUOUS_ALERT, () -> _alarm.play("warning"));
			put(ActionTypes.DISENGAGE_EHB , () -> _brake.setPressure(0.0));
			put(ActionTypes.FULLY_ENGAGE_EHB , () -> _brake.setPressure(100.0));
		}
	};

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
