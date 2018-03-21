package ehb;


public enum EventTypes
{
    SHIFT_OUT_OF_PARK,
    SHIFT_INTO_PARK,
    BUTTON_SHORT_PRESS,
    BUTTON_LONG_PRESS,
    SPEED_ZERO,
    SPEED_GREATER_THAN_ZERO,
    EHB_FULLY_DISENGAGED,
    PLAY_DISENGAGED_SOUND,
    BRAKE_ENGAGED_IN_PARK,
    BRAKE_DISENGAGED_IN_PARK;

    @Override
    public String toString() {
        switch(this)
        {
            case PARK:
                return "Park";
            case REVERSE:
                return "Reverse";
            case NEUTRAL:
                return "Neutral";
            case DRIVE:
                return "Drive";
            default:
                return "NULL";
        }
    }
}
