import cosa.models.Die;
import cosa.models.Game;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import cosa.models.Game;
//import cosa.models.Player;
//import jakarta.validation.*;
//
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
public class GameTest {
    private static ValidatorFactory vf;
    private static Validator validator;
    private Game game;



    private void assertInvalidGame(String expectedProperty, String expectedErrMsg, Object expectedValue){
        //run validator on car object and store the resulting violations in a collection
        Set<ConstraintViolation<Game>> constraintViolations = validator.validate( game );//use the private global car created in setUpValidCar

        //count how many violations - SHOULD ONLY BE 1
        assertEquals( 1, constraintViolations.size() );

        //get first violation from constraintViolations collection
        ConstraintViolation<Game> violation = constraintViolations.iterator().next();

        //ensure that expected property has the violation
        assertEquals( expectedProperty, violation.getPropertyPath().toString() );

        //ensure error message matches what is expected
        assertEquals( expectedErrMsg, violation.getMessage() );

        //ensure the invalid value is what was set
        assertEquals( expectedValue, violation.getInvalidValue() );
    }

    @BeforeAll
    public static void setUpValidator() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @AfterAll
    public static void tearDownValidator()
    {
        //gracefully teardown the validator factory
        vf.close();
    }
    @BeforeEach
    public void setUpValidGame()
    {
       game = new Game();
       game.setAllValues();
    }

    @Test
    public void testPlayerIndexInNormalRange()
    {
        game.setPlayerIndex(2);
        assertEquals(0, validator.validate( game ).size());
    }
    @Test
    public void testPlayerIndexIsWithinNumPlayerRangeLowerBound()
    {
        game.setPlayerIndex(0);
        assertEquals(0, validator.validate( game ).size());
    }
    @Test
    public void testPlayerIndexIsWithinNumPlayerRangeUpperBound()
    {
        game.setPlayerIndex(3);
        assertEquals(0, validator.validate( game ).size());
    }
    @Test
    public void testPlayerIndexIsWithinNumPlayerRangeLowerBoundFail()
    {
        game.setPlayerIndex(-2);
        assertInvalidGame("playerIndex", "Player 1 should be in zeroth spot of array", -2);
    }

    @Test
    public void testPlayerIndexIsWithinNumPlayerRangeupperBoundFail()
    {
        game.setPlayerIndex(10);
        assertInvalidGame("playerIndex", "Player 4 should be in the third spot of the array", 10);
    }

    // more constraint testing
    @Test
    public void testPlayerCreationIndexNormalCase()
    {
        // no setter/getter to test with, it's only used internally and should not be accessed otherwise
//        game.setCreationPlayerIndex(2);
//        assertEquals(0, validator.validate( game ).size());
    }

    @Test
    public void testPlayerCreationIndexBoundaryCaseWithinBounds()
    {
        // no setter/getter to test with, it's only used internally and should not be accessed otherwise
//        game.setCreationPlayerIndex(3);
//        assertEquals(0, validator.validate( game ).size());
    }

    @Test
    public void testPlayerCreationIndexBoundaryCaseAboveBounds()
    {
        // no setter/getter to test with, it's only used internally and should not be accessed otherwise
//        game.setCreationPlayerIndex(4);
//        assertInvalidGame("playerCreationIndex", "Only 4 players are allowed to play the game at once", 4);
    }

    @Test
    public void testPlayerCreationIndexBoundaryCaseBelowBounds()
    {
        // no setter/getter to test with, it's only used internally and should not be accessed otherwise
//        game.setCreationPlayerIndex(-1);
//        assertInvalidGame("playerCreationIndex", "There cannot be less than 1 player in the game", -1);
    }

    @Test
    public void testPlayerCreationIndexExceptionCase()
    {
        // no setter/getter to test with, it's only used internally and should not be accessed otherwise
//        game.setCreationPlayerIndex(7);
//        assertInvalidGame("playerCreationIndex", "Only 4 players are allowed to play the game at once", 7);
    }


}



//    private Player obPlayer;
//    private Game obGame;
//    private ValidatorFactory vf;
//    private Validator validator;
//
//    @BeforeAll
//    public void setupValidation()
//    {
//        vf = Validation.buildDefaultValidatorFactory();
//        validator = vf.getValidator();
//    }
//
//    @BeforeEach
//    public void setup()
//    {
//        obGame = new Game();
//        obPlayer = obGame.getCurrentPlayer();
//    }
//
//    @Test
//    public void testRandomlyRolledDiceSumOfBothDice()
//    {
//        int nSum = obGame.rollDice();
//        assertTrue(nSum <= 12 && nSum >= 2);
//    }
//
//DC
//    @Test
//    public void testIfRollDiceIsDoubles()
//    {
//        obGame.rollDice();
//        if(obGame.isDoubles())
//        {
//            assertTrue(obGame.isDoubles());
//        }
//        else
//        {
//            assertFalse(obGame.isDoubles());
//        }
//    }
//
//    @Test
//    public void testThatEndTurnIncrementsTheCurrentPlayer()
//    {
//        obGame.endTurn();
//        assertEquals(obGame.getPlayerIndex(), 1);
//
//        obGame.endTurn();
//        assertEquals(obGame.getPlayerIndex(), 2);
//
//        obGame.endTurn();
//        assertEquals(obGame.getPlayerIndex(), 3);
//    }
//
//    @Test
//    public void testThatEndTurnSetsCurrentPlayerToZeroWhenPlayerCircuitFinishes()
//    {
//        obGame.endTurn();
//        assertEquals(obGame.getPlayerIndex(), 1);
//
//        obGame.endTurn();
//        assertEquals(obGame.getPlayerIndex(), 2);
//
//        obGame.endTurn();
//        assertEquals(obGame.getPlayerIndex(), 3);
//
//        obGame.endTurn();
//        assertEquals(obGame.getPlayerIndex(), 0);
//    }
//
//    @Test
//    public void testMovePlayerPlacesThePlayerOnTheCorrectSpaceBasedOnTheirRoll()
//    {
//        int startingPos = obPlayer.getPosition();
//        int diceRoll = obGame.rollDice();
//
//        obGame.movePlayer(startingPos, diceRoll);
//        int newPos = startingPos + diceRoll;
//        assertEquals(obPlayer.getPosition(), newPos);
//    }
//
//    @Test
//    public void testMovePlayerPlacesThePlayerOnTheCorrectSpaceWhenTheyPassGoNormalCase()
//    {
//        int startingPos = 35;
//        int diceRoll = 12;
//        obGame.movePlayer(startingPos, diceRoll);
//
//        assertEquals(obPlayer.getPosition(), 7);
//    }
//
//    @Test
//    public void testMovePlayerPlacesThePlayerOnTheCorrectSpaceWhenTheyPassGoBoundaryCase()
//    {
//        int startingPos = 29;
//        int diceRoll = 11;
//        obGame.movePlayer(startingPos, diceRoll);
//
//        assertEquals(obPlayer.getPosition(), 0);
//    }
//
//
//
//    @Test
//    public void testThatPlayerPassingGoIsDetected()
//    {
//        int startingPos = 29;
//        int diceRoll = 12;
//
//        obGame.movePlayer(startingPos, diceRoll);
//        assertTrue(obGame.passedGo(startingPos, diceRoll));
//    }
//
//}
