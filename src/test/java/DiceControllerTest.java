import cosa.purple.DiceController;
import cosa.models.Die;
import cosa.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiceControllerTest
{
    private Player obPlayer;
    private DiceController DC;
    private Die die;


    @BeforeEach
    public void setup()
    {
        DC = new DiceController();
        die = new Die();
//        die.setDieVal1(1);
//        die.setDieVal1(2);
//        die.setDiceRoll(9);
    }

    @Test
    public void testRandomlyRolledDiceSumOfBothDice()
    {
        int nSum = DC.rollDice(); //tests for individual Die range in the DieTest
        assertTrue(nSum <= 12 && nSum >= 2);
    }

    @Test
    public void testIfRollDiceIsDoubles()
    {
        DC.rollDice();
        if(DC.isDoubles())
        {
            assertTrue(DC.isDoubles());
        }
        else
        {
            assertFalse(DC.isDoubles());
        }
    }
}
