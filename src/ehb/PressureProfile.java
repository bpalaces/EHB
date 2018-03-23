package ehb;


import java.util.Map;
import java.util.TreeMap;

public class PressureProfile
{
    private Motion _motion;
    private double _currentPressure = 0;
    private static TreeMap<Long, Double> _goodPressureProfile;
    PressureProfile(Motion motion)
    {
        _motion = motion;
    }


    //The units for the profiler is miles per hour to kPascal
    static
    {
        _goodPressureProfile = new TreeMap<Long, Double>();
        _goodPressureProfile.put(Long.valueOf(20), 6.0);
        _goodPressureProfile.put(Long.valueOf(25), 5.75);
        _goodPressureProfile.put(Long.valueOf(30), 5.5);
        _goodPressureProfile.put(Long.valueOf(35), 5.25);
        _goodPressureProfile.put(Long.valueOf(40), 5.0);
        _goodPressureProfile.put(Long.valueOf(45), 4.75);
        _goodPressureProfile.put(Long.valueOf(50), 4.5);
        _goodPressureProfile.put(Long.valueOf(55), 4.25);
        _goodPressureProfile.put(Long.valueOf(60), 4.0);
        _goodPressureProfile.put(Long.valueOf(65), 3.75);
        _goodPressureProfile.put(Long.valueOf(70), 3.5);
        _goodPressureProfile.put(Long.valueOf(75), 3.25);
        _goodPressureProfile.put(Long.valueOf(80), 3.0);
        _goodPressureProfile.put(Long.valueOf(85), 2.75);
        _goodPressureProfile.put(Long.valueOf(90), 2.5);
        _goodPressureProfile.put(Long.valueOf(95), 2.25);
        _goodPressureProfile.put(Long.valueOf(100), 2.0);
        _goodPressureProfile.put(Long.valueOf(105), 1.75);
        _goodPressureProfile.put(Long.valueOf(110), 1.5);
        _goodPressureProfile.put(Long.valueOf(115), 1.25);
        _goodPressureProfile.put(Long.valueOf(120), 1.0);
        _goodPressureProfile.put(Long.valueOf(125), 0.75);
        _goodPressureProfile.put(Long.valueOf(130), 0.5);
        _goodPressureProfile.put(Long.valueOf(135), 0.25);
        _goodPressureProfile.put(Long.valueOf(140), 0.00);
    }

    double getNewPressure()
    {
        //This uses the max and low values of the tree map to get the closest value in the
        //pressure profile
        double _speed = _motion.getSpeed();
        Long key = Long.valueOf((int) _speed);
        Map.Entry<Long, Double> floor = _goodPressureProfile.floorEntry(key);
        Map.Entry<Long, Double> ceiling = _goodPressureProfile.ceilingEntry(key);


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

        if (targetPressure > _currentPressure)
        {
            _currentPressure += rate;
            return _currentPressure;
        }
        else
        {
            return _currentPressure;
        }

    }
}
