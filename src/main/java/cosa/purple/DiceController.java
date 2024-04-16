package cosa.purple;

import cosa.models.Die;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class is the controller used for the dice rolling sequence.
 * This is the controller for dice-view.fxml
 * This controller uses the Die (model) and GameController.
 * Author: Kesley and John
 */
public class DiceController implements Initializable
{
    private Die die; //Die object used to call methods from Die class

    private GameController GC; //Game controller object used to call methods from GameController

    /**
     * Constructor for the DiceController class
     */
    public DiceController() {
        this.die = new Die();
        this.die.setAllValues(1, 1);
    }

    /**
     * Roll 2 dice randomly with outcomes of 1 to 6.
     * @return - the sum of 2 randomly generated "dice" rolls.
     */
    public int rollDice() {
        //Randomly generate independent rolls for two 6-sided die
        die.setDieVal1((int) (Math.random() * die.getMAX()) + die.getMIN());
        die.setDieVal2((int) (Math.random() * die.getMAX()) + die.getMIN());

        // USE FOR TESTING DOUBLES
//        die.setDieVal1(3);
//        die.setDieVal2(3);

        return die.getDieVal1() + die.getDieVal2();
    }

    /**
     * This method will check if both rolled dice are the same value.
     * @return - true if both dice are the same value, false otherwise
     */
    public boolean isDoubles() {
        return die.getDieVal1() == die.getDieVal2();
    }

    // The following are Getters/Setters for the DiceController
    public int getSum()
    {
        return die.getDieVal1() + die.getDieVal2();
    }

    public GameController getGC() {
        return GC;
    }

    public Die getDie() {
        return die;
    }


    /**
     * Instantiates a Die and GameController
     * @param url - used to instantiate
     * @param resourceBundle - used to instantiate
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.die = new Die();
    }


}
