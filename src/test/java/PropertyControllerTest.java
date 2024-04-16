import static org.junit.jupiter.api.Assertions.*;

import cosa.models.Player;
import cosa.models.PlayerColor;
import cosa.models.Property;
import cosa.purple.MonopolyController;
import cosa.purple.PlayerController;
import cosa.purple.PropertyController;
import org.junit.jupiter.api.*;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PropertyControllerTest {
    private Property obProp;
    private Player obPlayer;

    private MonopolyController MC;

    @BeforeEach
    public void setup() {
        obProp = PropertyController.createProperty(100, 50, null);
        obPlayer = PlayerController.createPlayer("Bob", PlayerColor.GREEN);
        MC = new MonopolyController();
        PropertyController.setMC(MC);
    }


    @Test
    public void testPropertySpaceVacancy() {
        boolean isVacant = PropertyController.checkVacancy(obProp);
        assertTrue(isVacant);
    }


    @Test
    public void testPropertySpaceVacancyFalse() {
        PropertyController.purchase(obProp, obPlayer);
        assertFalse(PropertyController.checkVacancy(obProp));
    }

    @Test
    public void testOwnerAssigned() {
        PropertyController.purchase(obProp, obPlayer);
        assertEquals(obProp.getOwner(), obPlayer);
    }


    @Test
    public void testAskPlayerSaysYes() {
        try (DataInputStream obIn = new DataInputStream(new FileInputStream("D:\\cosacpmg\\team4.purple\\src\\test\\java\\playerSaysYes.txt"))) {
            System.setIn(obIn);
            assertTrue(MonopolyController.askPurchase(obProp, obPlayer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testAskPlayerSaysNo() {
        try (DataInputStream obIn = new DataInputStream(new FileInputStream("D:\\cosacpmg\\team4.purple\\src\\test\\java\\playerSaysNo.txt"))) {
            System.setIn(obIn);
            assertFalse(MonopolyController.askPurchase(obProp, obPlayer));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testCheckPropertyAllowsPurchase() {
        try (DataInputStream obIn = new DataInputStream(new FileInputStream("D:\\cosacpmg\\team4.purple\\src\\test\\java\\playerSaysYes.txt"))) {
            System.setIn(obIn);
            PropertyController.checkProperty(obProp, obPlayer);
            assertEquals(obProp.getOwner(), obPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testCheckPropertyDoesRent() {
        Player obOwner = PlayerController.createPlayer("Joe", PlayerColor.BLUE);
        PropertyController.purchase(obProp, obOwner);
        PropertyController.checkProperty(obProp, obPlayer);
        int nExpected = 1450;

        assertEquals(nExpected, obPlayer.getMonoMoney());
    }


    @Test
    public void testOwnerIsNotChargedRent() {
        PropertyController.purchase(obProp, obPlayer);
        int nExpected = obPlayer.getMonoMoney();
        PropertyController.checkProperty(obProp, obPlayer);
        assertEquals(nExpected, obPlayer.getMonoMoney());
    }


    @Test
    public void testRegularRent() {
        Player obRenter = PlayerController.createPlayer("Joe", Player.STARTING_MONEY, new ArrayList<>(), Player.STARTING_POS);
        PropertyController.purchase(obProp, obPlayer);
        PropertyController.doEvent(obProp, obRenter);

        int nExpected = 1450;

        assertEquals(nExpected, obRenter.getMonoMoney());
    }


}
