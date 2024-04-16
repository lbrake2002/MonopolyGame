import cosa.models.Player;
import cosa.models.Property;
import cosa.models.Railroad;
import cosa.models.Utility;
import cosa.purple.PropertyController;
import static cosa.purple.PlayerController.*;

import cosa.purple.UtilityController;
import jdk.jshell.execution.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UtilityControllerTest {

    Player obPlayer;

    @BeforeEach
    public void setup()
    {
        obPlayer = new Player();
        obPlayer.setAll("Noah", 1500, new ArrayList<>(), 0);
    }

    @Test
    public void testPlayerOwnsOneOrBothUtilities()
    {
        for (int i = 0; i < 2; i++)
        {
            Utility utility = createUtility();
            obPlayer.addProperty(utility);
            UtilityController.multiplyRent(10, utility);
            assertEquals(utility.getRentAmount(), 10 * Utility.MULTIPLIERS[i]);
        }
    }

    /**
     * Creates a new Utility and set all of its values
     *
     * @return  The newly created utility
     */
    private Utility createUtility()
    {
        Utility utility = new Utility();
        utility.setAll(150, 25, obPlayer);
        return utility;
    }
}
