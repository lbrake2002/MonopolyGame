import static org.junit.jupiter.api.Assertions.*;

import cosa.models.Player;
import cosa.models.Property;
import cosa.purple.PlayerController;
import cosa.purple.PropertyController;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.junit.jupiter.api.*;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.Set;

public class PropertyTest
{
    private static ValidatorFactory vf;
    private static Validator validator;
    private static Property property;

    /**
     * Note: This is a modified version from Ernesto team0.gold Example
     * Helper: creates a string of letter X's of any size for testing string length bounds
     * @param count - How many times the letter X is repeated in the string
     * @return string of the letter X repeated the specified number of times
     */
    private String repeatX(int count){
        return new String(new char[count]).replace('\0','X');
    }

    private void assertInvalidProperty(String expectedProperty, String expectedErrMsg, Object expectedValue){
        //run validator on player object and store the resulting violations in a collection
        Set<ConstraintViolation<Property>> constraintViolations = validator.validate( property );//use the private global player

        //count how many violations - SHOULD ONLY BE 1
        assertEquals( 1, constraintViolations.size() );

        //get first violation from constraintViolations collection
        ConstraintViolation<Property> violation = constraintViolations.iterator().next();

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
    public void setUpValidProperty()
    {
        property = PropertyController.createProperty(200, 20, null);
        property.setImageIndex(1);
    }



    @Test
    public void testPriceLessThanOneIsInvalid()
    {
        int invalid = 0;
        property.setPrice(invalid);
        assertInvalidProperty("price", "Property price must be a value greater than 0", invalid);
    }

    @Test
    public void testPriceLowerBound()
    {
        property.setPrice(1);
        assertEquals( 0, validator.validate( property ).size() );
    }

    @Test
    public void testRentLessThanOneIsInvalid()
    {
        int invalid = 0;
        property.setRent(invalid);
        assertInvalidProperty("rentAmount", "Property rent must be a value greater than 0", invalid);
    }

    @Test
    public void testRentLowerBound()
    {
        property.setRent(1);
        assertEquals( 0, validator.validate( property ).size() );
    }

    @Test
    public void testOwnerNullIsValid()
    {
        property.setOwner(null);
        assertEquals( 0, validator.validate( property ).size() );
    }

    @Test
    public void testOwnerNotNullIsValid()
    {
        property.setOwner(new Player());
        assertEquals( 0, validator.validate( property ).size() );
    }


    @Test
    public void testIndexLessThanOneIsInvalid()
    {
        int invalid = 0;
        property.setImageIndex(invalid);
        assertInvalidProperty("imageIndex", "Property image index must be a value greater than 0", invalid);
    }

    @Test
    public void testIndexGreaterThenThirtyNineIsInvalid()
    {
        int invalid = 40;
        property.setImageIndex(invalid);
        assertInvalidProperty("imageIndex", "Property image index must be less than 40", invalid);
    }

    @Test
    public void testIndexLowerBound()
    {
        property.setImageIndex(1);
        assertEquals( 0, validator.validate( property ).size() );
    }


    @Test
    public void testIndexUpperBound()
    {
        property.setImageIndex(39);
        assertEquals( 0, validator.validate( property ).size() );
    }


    @Test
    public void testIndexNormalCase()
    {
        property.setImageIndex(20);
        assertEquals( 0, validator.validate( property ).size() );
    }
}
