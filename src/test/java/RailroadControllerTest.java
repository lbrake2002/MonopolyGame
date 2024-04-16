import cosa.models.Player;
import cosa.models.Property;
import cosa.models.Railroad;
import cosa.models.Utility;
import cosa.purple.PropertyController;
import static cosa.purple.PlayerController.*;


import cosa.purple.RailroadController;
import jdk.jshell.execution.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RailroadControllerTest {

    Player obPlayer = new Player();

    @BeforeEach
    public void setup()
    {
        obPlayer = createPlayer("Bob", Player.STARTING_MONEY, new ArrayList<>(), Player.STARTING_POS);
    }

    @Test
    public void testRailroadRentWithOneRailroad()
    {
        Railroad railroad = createRailroad();
        obPlayer.addProperty(railroad);
        RailroadController.updateRailRent(railroad);
        assertEquals(railroad.getRentAmount(), 25);
    }

    @Test
    public void testRailroadRentWithOneToAllRailroads()
    {
        for(int i = 0; i < 4; i++)
        {
            Railroad railroad = createRailroad();
            obPlayer.addProperty(railroad);
            RailroadController.updateRailRent(railroad);
            assertEquals(railroad.getRentAmount(), Railroad.PRICES[i]);
        }
    }

    /**
     * Creates a new Railroad and sets all properties
     *
     * @return  The newly created railroad
     */
    private Railroad createRailroad()
    {
        Railroad railroad = new Railroad();
        railroad.setAll(200, 25, obPlayer);
        return railroad;
    }


}
