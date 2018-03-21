package ehb;

/**
 * This enum represents is used to represent the set of valid gear states
 * that the car can be in. No other gear states are possible for this simulation.
 */
public enum GearTypes
{
    NO_OP,
    SET_COLOR_ORANGE,
    SET_COLOR_BLUE,
    SET_COLOR_RED,
    PLAY_ENGAGED_SOUND,
    PLAY_DISENGAGED_SOUND,
    APPLY_TEN_PERCENT_FORCE_INCREASE,
    DISENGAGED_EHB,
    FULLY_ENGAGE_EHB;
}
