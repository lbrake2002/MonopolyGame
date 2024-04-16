package cosa.models;

/**
 * This class holds enumerations for each color of groups in the Monopoly game.
 */

public enum RealEstateColor
{
    BROWN(2), LIGHT_BLUE(3), PINK(3), ORANGE(3), RED(3), YELLOW(3), GREEN(3), DARK_BLUE(2);

    public final int NUM_PROPS;

    private RealEstateColor(int nProps)
    {
        this.NUM_PROPS = nProps;
    }
}
