import static org.junit.jupiter.api.Assertions.*;

import cosa.models.Property;
import cosa.purple.PlayerController;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.*;
import cosa.models.Player;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.Set;

public class PlayerTest {

    private static ValidatorFactory vf;
    private static Validator validator;

    private Player player;


    /**
     * Note: This is a modified version from Ernesto team0.gold Example
     * Helper: creates a string of letter X's of any size for testing string length bounds
     * @param count - How many times the letter X is repeated in the string
     * @return string of the letter X repeated the specified number of times
     */
    private String repeatX(int count){
        return new String(new char[count]).replace('\0','X');
    }

    /**
     * Modified version from Ernesto's team0.gold example
     * @param expectedProperty
     * @param expectedErrMsg
     * @param expectedValue
     */
    private void assertInvalidPlayer(String expectedProperty, String expectedErrMsg, Object expectedValue){
        //run validator on player object and store the resulting violations in a collection
        Set<ConstraintViolation<Player>> constraintViolations = validator.validate( player );//use the private global player

        //count how many violations - SHOULD ONLY BE 1
        assertEquals( 1, constraintViolations.size() );

        //get first violation from constraintViolations collection
        ConstraintViolation<Player> violation = constraintViolations.iterator().next();

        //ensure that expected property has the violation
        assertEquals( expectedProperty, violation.getPropertyPath().toString() );

        //ensure error message matches what is expected
        assertEquals( expectedErrMsg, violation.getMessage() );

        //ensure the invalid value is what was set
        assertEquals( expectedValue, violation.getInvalidValue() );
    }

    /***
     * Note: This was taken from Ernesto team0.gold Example
     * Run once at class creation to set up validator
     * or any other static objects
     */
    @BeforeAll
    public static void setUpValidator() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    /***
     * Note: This was taken from Ernesto team0.gold Example
     * Run once at class destruction to tear down validator
     * or any other static objects
     */
    @AfterAll
    public static void tearDownValidator()
    {
        //gracefully teardown the validator factory
        vf.close();
    }

    @BeforeEach
    public void setUpValidPlayer()
    {
        player = PlayerController.createPlayer("Bob",Player.STARTING_MONEY,new ArrayList<>(),Player.STARTING_POS);
    }


    @Test
    public void testNameIsLessThan2CharactersLong() {
        String invalid = "X";
        player.setName(invalid);
        assertInvalidPlayer("name", "Player name must be between 2 and 20 characters inclusive", invalid);
    }

    @Test
    public void testNameIsMoreThan20CharactersLong() {
        String invalid = repeatX(21);
        player.setName(invalid);
        assertInvalidPlayer("name", "Player name must be between 2 and 20 characters inclusive", invalid);
    }

    @Test
    public void testNameUpperBoundIsValid() {
        player.setName(repeatX(20));
        assertEquals( 0, validator.validate( player ).size() );
    }

    @Test
    public void testNameLowerBoundIsValid() {
        player.setName(repeatX(2));
        assertEquals( 0, validator.validate( player ).size() );
    }

    @Test
    public void testNameNormalCaseIsValid() {
        player.setName(repeatX(10));
        assertEquals( 0, validator.validate( player ).size() );
    }

    @Test
    public void testPlayersPositionLowerBoundIsValid()
    {
        player.setPosition(0);
        assertEquals( 0, validator.validate( player ).size() );
    }

    @Test
    public void testPlayersPositionUpperBoundIsValid()
    {
        player.setPosition(39);
        assertEquals( 0, validator.validate( player ).size() );
    }

    @Test
    public void testPlayersPositionNormalCaseIsValid()
    {
        player.setPosition(20);
        assertEquals( 0, validator.validate( player ).size() );
    }

    @Test
    public void testPlayersPositionLessThanZeroIsNotValid()
    {
        int invalid = -1;
        player.setPosition(invalid);
        assertInvalidPlayer("position", "Players position must be a positive number", invalid);
    }

    @Test
    public void testPlayersPositionMoreThanThirtyNineIsNotValid()
    {
        int invalid = 40;
        player.setPosition(invalid);
        assertInvalidPlayer("position", "Players position can be at most 39", invalid);
    }

    @Test
    public void testPlayersMoneyLowerBoundIsValid()
    {
        player.setMonoMoney(0);
        assertEquals( 0, validator.validate( player ).size() );
    }
    @Test
    public void testPlayersMoneyNormalCaseIsValid()
    {
        player.setMonoMoney(2000);
        assertEquals( 0, validator.validate( player ).size() );
    }


    @Test
    public void testPlayersPMoneyLessThanZeroIsNotValid()
    {
        int invalid = -1;
        player.setMonoMoney(invalid);
        assertInvalidPlayer("monoMoney", "Players MonoMoney must be a positive number", invalid);
    }

    @Test
    public void testPlayerPropertiesIsNullIsInvalid()
    {
        player.setProperties(null);
        assertInvalidPlayer("properties", "Players property list cannot be null", null);
    }

    @Test
    public void testPlayerPropertiesSizeLowerBoundIsValid()
    {
        assertEquals(0, validator.validate(player).size());
    }

    @Test
    public void testPlayerPropertiesSizeUpperBoundIsValid()
    {
        for (int i = 0; i < 28; i++)
        {
            player.addProperty(new Property());
        }

        assertEquals(0, validator.validate(player).size());
    }

    @Test
    public void testPlayerPropertiesSizeNormalCaseIsValid()
    {
        for (int i = 0; i < 10; i++)
        {
            player.addProperty(new Property());
        }

        assertEquals(0, validator.validate(player).size());
    }

    @Test
    public void testPlayerPropertiesSizeAboveTwentyEightIsInvalid()
    {
        ArrayList<Property> lstProps = new ArrayList<>();
        for (int i = 0; i < 29; i++)
        {
            lstProps.add(new Property());
        }
        player.setProperties(lstProps);

        assertInvalidPlayer("properties", "Players can own between 0 and 28 properties inclusive", lstProps);
    }

    @Test
    public void testNumberOfDoublesAPlayerHasRolledNormalCase()
    {
        player.setNumDoubles(2);
        assertEquals(0, validator.validate(player).size());
    }

    @Test
    public void testNumberOfDoublesAPlayerHasRolledBoundaryCaseWithinBounds()
    {
        player.setNumDoubles(3);
        assertEquals(0, validator.validate(player).size());
    }

    @Test
    public void testNumberOfDoublesAPlayerHasRolledBoundaryCaseOutsideUpperBounds()
    {
        player.setNumDoubles(4);
        assertInvalidPlayer("numDoubles","Player can roll a max of 3 doubles before being sent to jail",4);
    }

    @Test
    public void testNumberOfDoublesAPlayerHasRolledBoundaryCaseOutsideLowerBounds()
    {
        player.setNumDoubles(-1);
        assertInvalidPlayer("numDoubles","Player cannot roll a negative amount of doubles",-1);
    }

    @Test
    public void testNumberOfDoublesAPlayerHasRolledExceptionCase()
    {
        player.setNumDoubles(7);
        assertInvalidPlayer("numDoubles","Player can roll a max of 3 doubles before being sent to jail",7);
    }



}
