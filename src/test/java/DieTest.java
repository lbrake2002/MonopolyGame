import cosa.models.Die;
import jakarta.validation.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class DieTest
{
    private static ValidatorFactory vf;
    private static Validator validator;
    private Die die;

    private void assertInvalidDie(String expectedProperty, String expectedErrMsg, Object expectedValue){
        //run validator on car object and store the resulting violations in a collection
        Set<ConstraintViolation<Die>> constraintViolations = validator.validate( die );//use the private global car created in setUpValidCar

        //count how many violations - SHOULD ONLY BE 1
        assertEquals( 1, constraintViolations.size() );

        //get first violation from constraintViolations collection
        ConstraintViolation<Die> violation = constraintViolations.iterator().next();

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
    public void setUpValidDie()
    {
        die = new Die();
        die.setDieVal1(5);
        die.setDieVal2(4);
        die.setDiceRoll(9);
    }

    @Test
    public void testDieValueRangeNormalCaseDie()
    {
        die.setDieVal1(3);
        die.setDieVal2(2);
        assertEquals(0, validator.validate( die ).size());
    }
    @Test
    public void testDieValueRangeBoundaryCasePassDieVal()
    {
        die.setDieVal1(3);
        die.setDieVal2(2);
        assertEquals(0, validator.validate( die ).size());
    }
    @Test
    public void testDieValueRangeBoundaryCaseFailDieVal1()
    {
        die.setDieVal1(0);
        assertInvalidDie("dieVal1", "Die must be minimum value of 1", 0);
    }
    @Test
    public void testDieValueRangeExceptionCaseDieVal1()
    {
        die.setDieVal1(-10);
        assertInvalidDie("dieVal1", "Die must be minimum value of 1", -10);
    }
    @Test
    public void testDieValueRangeBoundaryCaseFailDieVal2()
    {
        die.setDieVal2(7);
        assertInvalidDie("dieVal2", "Die must be maximum value of 6", 7);
    }
    @Test
    public void testDieValueRangeExceptionCaseDieVal2()
    {
        die.setDieVal2(66);
        assertInvalidDie("dieVal2", "Die must be maximum value of 6", 66);
    }
    @Test
    public void testDieSumValueRangeNormalCaseDie()
    {
        die.setDiceRoll(8);

        assertEquals(0, validator.validate( die ).size());
    }
    @Test
    public void testDieSumValueRangeBoundaryCasePass()
    {
        die.setDiceRoll(12);
        assertEquals(0, validator.validate( die ).size());
    }
    @Test
    public void testDieSumValueRangeBoundaryCaseFailOverRange()
    {
        die.setDiceRoll(13);
        assertInvalidDie("diceRoll", "Die roll must be maximum value of 12", 13);
    }

    @Test
    public void testDieSumValueRangeBoundaryCaseFailUnderRange()
    {
        die.setDiceRoll(1);
        assertInvalidDie("diceRoll", "Die roll must be minimum value of 2", 1);
    }
    @Test
    public void testDieSumValueRangeExceptionCase()
    {
        die.setDiceRoll(20);
        assertInvalidDie("diceRoll", "Die roll must be maximum value of 12", 20);
    }

    //Testing the Image[] holding the die images
    @Test
    public void testDieImagesArrayIsALengthOfSix()
    {

    }
}
