package cosa.models;

import jakarta.validation.constraints.*;
import javafx.scene.image.*;

import java.io.Serializable;

/**
 * Java Bean standard class (Serializable) that contains private attributes and no parameter constructor.
 * Author: Kesley and John
 */
public class Die implements Serializable {

    private final int MIN = 1; // the minimum value for a single die
    private final int MAX = 6; // the maximum value for a single die

    @Min(value = MIN, message = "Die must be minimum value of 1")
    @Max(value = MAX, message = "Die must be maximum value of 6")
    private int dieVal1; // individual die roll value for the first die

    @Min(value = MIN, message = "Die must be minimum value of 1")
    @Max(value = MAX, message = "Die must be maximum value of 6")
    private int dieVal2; // individual die roll value for the second die

    @Min(value = MIN * 2, message = "Die roll must be minimum value of 2")
    @Max(value = MAX * 2, message = "Die roll must be maximum value of 12")
    private int diceRoll; // the sum of both die values

//    @NotNull(message = "Images can not be null")
//    @Size(min = MAX, max = MAX, message = "There must be 6 images")
//    private Image[] diceImages = new Image[6]; // an array of Images holding the images of each side of a die


//    private Image ivDice1; // the Image of the first die side that was rolled
//    private Image ivDice2; // the Image of the second die side that was rolled

    public Die() {
    }

    /**
     * Workaround to copy values from object to object.
     *
     * @param dieVal1 - the value of the first die
     * @param dieVal2 - the value of the second die
     */
    public void setAllValues(int dieVal1, int dieVal2) {
        this.dieVal1 = dieVal1;
        this.dieVal2 = dieVal2;
        this.diceRoll = dieVal1 + dieVal2;
    }

    // The following are getters/setters for the properties of the Die class
    public int getDieVal1() {
        return dieVal1;
    }

    public void setDieVal1(int dieVal1) {
        this.dieVal1 = dieVal1;
    }

    public int getDieVal2() {
        return dieVal2;
    }

    public void setDieVal2(int dieVal2) {
        this.dieVal2 = dieVal2;
    }

    public int getDiceRoll() {
        return diceRoll;
    }

    public void setDiceRoll(int diceRoll) {
        this.diceRoll = diceRoll;
    }

    public int getMIN() {
        return MIN;
    }

    public int getMAX() {
        return MAX;
    }
//
//    public Image getIvDice1() {
//        return ivDice1;
//    }
//
//    public void setIvDice1(int nDie1) {
//        this.ivDice1 = diceImages[nDie1];
//    }
//
//    public Image getIvDice2() {
//        return ivDice2;
//    }
//
//    public void setIvDice2(int nDie2) {
//        this.ivDice2 = diceImages[nDie2];
//    }
//
//    public Image[] getDiceImages() {
//        return diceImages;
//    }
}
