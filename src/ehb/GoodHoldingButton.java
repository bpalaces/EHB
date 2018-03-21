package ehb;

/**
 * Created by Kevin Omidvaran on 3/2/18.
 */

import interfaces.*;

import java.util.Map;
import java.util.TreeMap;




public class GoodHoldingButton
{
  private double _speed;

  private GearTypes _gear;
  private boolean wasEngaged;
  private int alertPlayed;
  private boolean _engaged;
  private boolean _shortHold;
  private int _counter;
  private boolean _isActive;
  private boolean _previousIsDown;
  Double _initSpeed = -1000.0;
  TreeMap<Long, Double> goodPressureProfile = new TreeMap<Long,Double>();


  public GoodHoldingButton()
  {


    wasEngaged = false;
    alertPlayed = 0;
    _engaged = false;
    _shortHold = true;
    _counter = 0;
    _isActive = false;
    _previousIsDown = false;
    _initSpeed = -1000.0;

    //Max pressure is considered to be at 6 kPa.
    //first is speed. second is pressure

    //The units for the profiler is miles per hour to kPascal

    goodPressureProfile.put(Long.valueOf(20), 4.75);
    goodPressureProfile.put(Long.valueOf(25), 4.75);
    goodPressureProfile.put(Long.valueOf(30), 4.75);
    goodPressureProfile.put(Long.valueOf(35), 4.5);
    goodPressureProfile.put(Long.valueOf(40), 4.5);
    goodPressureProfile.put(Long.valueOf(45), 4.5);
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




  //We use the pressure profile obtained from http://www.scielo.br/scielo.php?script=sci_arttext&pid=S0100-73862001000100007
  // with supplement http://www.optimumg.com/docs/Brake_tech_tip.pdf

  public void update()
  {
    if(_initSpeed == -1000.0)
    {
      _initSpeed = SpeedInterface.getSpeed();
      System.out.print(_isActive);
      System.out.println("is init speed");
    }
    if(!ButtonInterface.isDown() && SpeedInterface.getSpeed() == _initSpeed)
    {
      _isActive = false;
//      System.out.println("should be inactive");
      _previousIsDown = ButtonInterface.isDown();
      _counter =0;
    }
    if(ButtonInterface.isDown() && SpeedInterface.getSpeed() == _initSpeed)
    {
      _isActive = false;
      _previousIsDown = true;
      _counter = 0;
    }

    _speed = 0;
//    System.out.println(_isActive);
    if ((ButtonInterface.isDown() != _previousIsDown && ButtonInterface.isDown()) )
    {
//      System.out.println("found is down");
      System.out.println(_previousIsDown);
//                _engaged = !_engaged;

      _isActive = !_isActive;
      _counter = 0;

    }

    if (ButtonInterface.isDown() ==  _previousIsDown && ButtonInterface.isDown())
    {
      _counter++;
    }
    if (_counter == 120 )
    {
//      System.out.println("found is down");
      _shortHold = false;


    }
    if(_isActive && _shortHold)
    {


//      System.out.println(_counter);
      ButtonInterface.setColor(ButtonColorTypes.RED);

      if (alertPlayed == 0)
      {
        ButtonInterface.play(ButtonSoundTypes.ENGAGED);
        wasEngaged = true;
        alertPlayed++;
      }
      _speed = (SpeedInterface.getSpeed() / 0.44704); // Get the speed from the speed interface.
      _gear = GearInterface.getGear();  // Get the current gear from the Gear interface.

      if (_gear.toString().equals("Park"))
      {
        BrakeInterface.setPressure(100.00);
      }
      else if ((!_gear.toString().equals("Reverse")) && _speed < 0)
      {
        BrakeInterface.setPressure(100.00);
      }
      else
      {
        //This uses the max and low values of the tree map to get the closest value in the
        //pressure profile
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
//                System.out.println("closestResult = " + closestResult);

        BrakeInterface.setPressure((closestResult / 6.0) * 100);
      }
    }
    else if(_isActive && !_shortHold)
    {
      BrakeInterface.setPressure(100);
      ButtonInterface.setColor(ButtonColorTypes.GREEN);

    }
    else
    {
      ButtonInterface.setColor(ButtonColorTypes.BLUE);
      if (wasEngaged)
      {
        ButtonInterface.play(ButtonSoundTypes.DISENGAGED);
        wasEngaged = false;
        alertPlayed--;
        _shortHold = true;
      }
//      System.out.println("not engaged");
      BrakeInterface.setPressure(0.0);
      _counter = 0;

    }
    _previousIsDown = ButtonInterface.isDown();
  }
}
