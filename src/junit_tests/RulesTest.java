package junit_tests;

import static org.junit.Assert.fail;

import junit.framework.TestCase;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import ehb.*;

public class RulesTest extends TestCase
{
  private static InitEngine _init;
  Rules rules;

  @Override
  public void setUp() throws Exception {
    // Do this so that the engine is only initialized once
    if (_init == null) {
        _init.init();
    }
    rules = new Rules();
  }

  @Test
  public void testWhatEvents()
  {
    RunTest test = new RunTest();

    List<EventTypes> events = Arrays.asList(EventTypes.BUTTON_PRESS, EventTypes.SHIFT_OUT_OF_PARK);
    List<StateTypes> states = Arrays.asList(StateTypes.PARKED_DISENGAGED, StateTypes.STOPPED_ENGAGED);
    assertEquals(IntStream.range(0, events.size()).boxed().collect(Collectors.toMap(events::get, states::get)), rules.whatEvents(StateTypes.PARKED_ENGAGED));

    events = Arrays.asList(EventTypes.BUTTON_PRESS, EventTypes.SHIFT_OUT_OF_PARK);
    states = Arrays.asList(StateTypes.PARKED_ENGAGED, StateTypes.STOPPED_DISENGAGED);
    assertEquals(IntStream.range(0, events.size()).boxed().collect(Collectors.toMap(events::get, states::get)), rules.whatEvents(StateTypes.PARKED_DISENGAGED));

      events = Arrays.asList(EventTypes.BUTTON_PRESS, EventTypes.SPEED_ZERO, EventTypes.NO_OP);
      states = Arrays.asList(StateTypes.MOVING_DISENGAGED, StateTypes.STOPPED_ENGAGED, StateTypes.MOVING_ENGAGED);
      assertEquals(IntStream.range(0, events.size()).boxed().collect(Collectors.toMap(events::get, states::get)), rules.whatEvents(StateTypes.MOVING_ENGAGED));

      events = Arrays.asList(EventTypes.SHIFT_INTO_PARK, EventTypes.SPEED_GREATER_THAN_ZERO, EventTypes.BUTTON_PRESS);
      states = Arrays.asList(StateTypes.PARKED_DISENGAGED, StateTypes.MOVING_DISENGAGED, StateTypes.STOPPED_ENGAGED);
      assertEquals(IntStream.range(0, events.size()).boxed().collect(Collectors.toMap(events::get, states::get)), rules.whatEvents(StateTypes.STOPPED_DISENGAGED));

      events = Arrays.asList(EventTypes.BUTTON_PRESS, EventTypes.SPEED_ZERO);
      states = Arrays.asList(StateTypes.MOVING_ENGAGED, StateTypes.STOPPED_DISENGAGED);
      assertEquals(IntStream.range(0, events.size()).boxed().collect(Collectors.toMap(events::get, states::get)), rules.whatEvents(StateTypes.MOVING_DISENGAGED));

      events = Arrays.asList(EventTypes.SHIFT_INTO_PARK, EventTypes.SPEED_GREATER_THAN_ZERO, EventTypes.BUTTON_PRESS, EventTypes.NO_OP);
      states = Arrays.asList(StateTypes.PARKED_ENGAGED, StateTypes.MOVING_ENGAGED, StateTypes.STOPPED_DISENGAGED, StateTypes.STOPPED_ENGAGED);
      assertEquals(IntStream.range(0, events.size()).boxed().collect(Collectors.toMap(events::get, states::get)), rules.whatEvents(StateTypes.STOPPED_ENGAGED));
  }

  @Test
  public void testWhatActions()
  {
      List<ActionTypes> actions = Arrays.asList(ActionTypes.SET_COLOR_BLUE, ActionTypes.DISENGAGE_EHB, ActionTypes.PLAY_DISENGAGED_SOUND);
      assertEquals(actions,rules.whatActions(EventTypes.BUTTON_PRESS,StateTypes.PARKED_ENGAGED));

      actions = Arrays.asList(ActionTypes.NO_OP);
      assertEquals(actions,rules.whatActions(EventTypes.SHIFT_OUT_OF_PARK, StateTypes.PARKED_ENGAGED));

      actions = Arrays.asList(ActionTypes.SET_COLOR_RED, ActionTypes.FULLY_ENGAGE_EHB, ActionTypes.PLAY_ENGAGED_SOUND);
      assertEquals(actions,rules.whatActions(EventTypes.BUTTON_PRESS, StateTypes.PARKED_DISENGAGED));

      actions = Arrays.asList(ActionTypes.NO_OP);
      assertEquals(actions,rules.whatActions(EventTypes.SHIFT_OUT_OF_PARK, StateTypes.PARKED_DISENGAGED));

      actions = Arrays.asList(ActionTypes.NO_OP);
      assertEquals(actions,rules.whatActions(EventTypes.SHIFT_INTO_PARK,StateTypes.STOPPED_DISENGAGED));

      actions = Arrays.asList(ActionTypes.NO_OP);
      assertEquals(actions,rules.whatActions(EventTypes.SPEED_GREATER_THAN_ZERO, StateTypes.STOPPED_DISENGAGED));

      actions = Arrays.asList(ActionTypes.FULLY_ENGAGE_EHB, ActionTypes.PLAY_ENGAGED_SOUND, ActionTypes.SET_COLOR_RED);
      assertEquals(actions,rules.whatActions(EventTypes.BUTTON_PRESS, StateTypes.STOPPED_DISENGAGED));

      actions = Arrays.asList( ActionTypes.SET_COLOR_ORANGE, ActionTypes.PLAY_ENGAGED_SOUND);
      assertEquals(actions,rules.whatActions(EventTypes.BUTTON_PRESS, StateTypes.MOVING_DISENGAGED));

      actions = Arrays.asList(ActionTypes.NO_OP);
      assertEquals(actions,rules.whatActions(EventTypes.SPEED_ZERO, StateTypes.MOVING_DISENGAGED));

      actions = Arrays.asList(ActionTypes.NO_OP);
      assertEquals(actions,rules.whatActions(EventTypes.SPEED_ZERO, StateTypes.MOVING_ENGAGED));

      actions = Arrays.asList(ActionTypes.DISENGAGE_EHB, ActionTypes.PLAY_DISENGAGED_SOUND, ActionTypes.SET_COLOR_BLUE);
      assertEquals(actions,rules.whatActions(EventTypes.BUTTON_PRESS, StateTypes.MOVING_ENGAGED));

      actions = Arrays.asList(ActionTypes.UPDATE_APPLIED_FORCE);
      assertEquals(actions,rules.whatActions(EventTypes.NO_OP, StateTypes.MOVING_ENGAGED));

      actions = Arrays.asList(ActionTypes.NO_OP);
      assertEquals(actions,rules.whatActions(EventTypes.SPEED_GREATER_THAN_ZERO, StateTypes.STOPPED_ENGAGED));

      actions = Arrays.asList(ActionTypes.SET_COLOR_RED, ActionTypes.FULLY_ENGAGE_EHB);
      assertEquals(actions,rules.whatActions(EventTypes.SHIFT_INTO_PARK, StateTypes.STOPPED_ENGAGED));

      actions = Arrays.asList(ActionTypes.DISENGAGE_EHB, ActionTypes.PLAY_DISENGAGED_SOUND, ActionTypes.SET_COLOR_BLUE);
      assertEquals(actions,rules.whatActions(EventTypes.BUTTON_PRESS, StateTypes.STOPPED_ENGAGED));

      actions = Arrays.asList(ActionTypes.PLAY_CONTINUOUS_ALERT);
      assertEquals(actions,rules.whatActions(EventTypes.NO_OP, StateTypes.STOPPED_ENGAGED));
  }

}
