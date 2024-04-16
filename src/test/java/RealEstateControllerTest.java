import cosa.models.Player;
import cosa.models.RealEstate;
import cosa.models.RealEstateColor;
import cosa.purple.PlayerController;
import cosa.purple.PropertyController;
import cosa.purple.RealEstateController;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class RealEstateControllerTest
{
    private RealEstate obProp;
    private Player obPlayer;
    private PropertyController PC;

    @BeforeEach
    public void setup() {
        PC = new PropertyController();
        obProp = new RealEstate();
        obProp.setAll(100, 50, null);
        obPlayer = PlayerController.createPlayer("Bob", Player.STARTING_MONEY, new ArrayList<>(), Player.STARTING_POS);
    }

    @Test
    public void testRentIsDoubledWhenPlayerOwnsAllOfSameColor()
    {
        for (int i = 0; i < RealEstateColor.LIGHT_BLUE.NUM_PROPS; i++)
        {
            RealEstate realEstate = new RealEstate();
            realEstate.setAll(100, 25, obPlayer);
            realEstate.setColor(RealEstateColor.LIGHT_BLUE);
            obPlayer.addProperty(realEstate);
        }

        obProp = (RealEstate) obPlayer.getProperties().get(obPlayer.getProperties().size() - 1);

        RealEstateController.doubleRent(obProp);
        assertEquals(obProp.getRentAmount(), 50);
    }

    @Test
    public void testRentIsNotDoubledWhenPlayerDoesNotOwnAllOfSameColor()
    {
        for (int i = 0; i < 2; i++)
        {
            RealEstate realEstate = new RealEstate();
            realEstate.setAll(100, 25, obPlayer);
            realEstate.setColor(RealEstateColor.LIGHT_BLUE);
            obPlayer.addProperty(realEstate);
        }

        obProp = (RealEstate) obPlayer.getProperties().get(obPlayer.getProperties().size() - 1);

        RealEstateController.doubleRent(obProp);
        assertEquals(obProp.getRentAmount(), 25);
    }
}
