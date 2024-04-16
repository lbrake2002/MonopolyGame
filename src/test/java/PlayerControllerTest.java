import cosa.models.*;
import cosa.purple.MonopolyController;
import cosa.purple.PlayerController;
import cosa.purple.PropertyController;
import static cosa.purple.PlayerController.*;

import jdk.jshell.execution.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerControllerTest {
    private Property obProp;
    private Player obPlayer;

    private MonopolyController mc;

    @BeforeEach
    public void setup() {
        obProp = PropertyController.createProperty(100, 50, null);
        obPlayer = createPlayer("Bob", PlayerColor.RED);
        mc = new MonopolyController();
        PropertyController.setMC(mc);
    }

    @Test
    public void testPlayHasEnoughMonopolyMoney() {
        assertTrue(hasEnoughFunds(obPlayer, obProp.getPrice()));
    }

    @Test
    public void testPlayerDoesNotHaveEnoughMonopolyMoney() {
        deductFunds(obPlayer, Player.STARTING_MONEY);
        assertFalse(hasEnoughFunds(obPlayer, obProp.getPrice()));
    }

    @Test
    public void testPlayerJustHasEnoughMoney() {
        deductFunds(obPlayer, 1400);
        assertTrue(hasEnoughFunds(obPlayer, obProp.getPrice()));
    }

    @Test
    public void testSubtractMonopolyMoney() {
        //int nExpectedPrice = obPlayer.getMonoMoney() - obProp.getPrice();
        int nExpectedPrice = 1400;
        deductFunds(obPlayer, obProp.getPrice());
        assertEquals(nExpectedPrice, obPlayer.getMonoMoney());
    }

    @Test
    public void testPlayerAddsProperty() {
        PropertyController.purchase(obProp, obPlayer);
        assertEquals(obPlayer.getProperties().size(), 1);
        assertEquals(obPlayer.getProperties().get(0), obProp);
    }


    @Test
    public void testPlayerOwnsNoRailroads()
    {
        assertEquals(getNumOwnedRailroads(obPlayer), 0);
    }


    @Test
    public void testPlayerOwnsOneRailroad()
    {
        Railroad railroad = createRailroad();
        obPlayer.addProperty(railroad);
        assertEquals(getNumOwnedRailroads(obPlayer), 1);
    }

    @Test
    public void testPlayerOwnsTwoRailroads()
    {
        for(int i = 0; i < 2; i++)
        {
            Railroad railroad = createRailroad();
            obPlayer.addProperty(railroad);
        }
        assertEquals(getNumOwnedRailroads(obPlayer), 2);
    }

    @Test
    public void testPlayerOwnsThreeRailroads()
    {
        for(int i = 0; i < 3; i++)
        {
            Railroad railroad = createRailroad();
            obPlayer.addProperty(railroad);
        }
        assertEquals(getNumOwnedRailroads(obPlayer), 3);
    }

    @Test
    public void testPlayerOwnsAllRailroads()
    {
        for(int i = 0; i < 4; i++)
        {
            Railroad railroad = createRailroad();
            obPlayer.addProperty(railroad);
        }
        assertEquals(getNumOwnedRailroads(obPlayer), 4);
    }

    private Railroad createRailroad()
    {
        Railroad railroad = new Railroad();
        railroad.setAll(200, 25, obPlayer);
        return railroad;
    }

    @Test
    public void testPlayerOwnsNoUtilities()
    {
        assertEquals(getNumOwnedUtilities(obPlayer), 0);
    }

    @Test
    public void testPlayerOwnsOneUtility()
    {
        Utility utility = createUtility();
        obPlayer.addProperty(utility);
        assertEquals(getNumOwnedUtilities(obPlayer), 1);
    }

    @Test
    public void testPlayerOwnsBothUtilities()
    {
        for(int i = 0; i < 2; i++)
        {
            Utility utility = createUtility();
            obPlayer.addProperty(utility);
        }
        assertEquals(getNumOwnedUtilities(obPlayer), 2);
    }

    private Utility createUtility()
    {
        Utility utility = new Utility();
        utility.setAll(150, 25, obPlayer);
        return utility;
    }


    @Test
    public void testPlayerOwnsAllOfARealEstateColor()
    {
        for (int i = 0; i < RealEstateColor.LIGHT_BLUE.NUM_PROPS; i++)
        {
            RealEstate realEstate = new RealEstate();
            realEstate.setAll(100, 25, obPlayer);
            realEstate.setColor(RealEstateColor.LIGHT_BLUE);
            obPlayer.addProperty(realEstate);
        }

        assertTrue(PlayerController.isOwnerOfAllColor(RealEstateColor.LIGHT_BLUE, obPlayer));
    }


    @Test
    public void testPlayerDoesNotOwnAllOfRealEstateColor()
    {
        RealEstate realEstate = new RealEstate();
        realEstate.setAll(100, 25, obPlayer);
        realEstate.setColor(RealEstateColor.LIGHT_BLUE);
        obPlayer.addProperty(realEstate);
        assertFalse(PlayerController.isOwnerOfAllColor(RealEstateColor.LIGHT_BLUE, obPlayer));
    }

}