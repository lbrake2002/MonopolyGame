package cosa.purple;

import cosa.models.Utility;

/**
 * The class is used to perform operations on a Utility object
 */
public class UtilityController {

    /**
     * Will multiply the rent amount by the roll of the players dice by the
     * amount of utilities that the owner player has
     *
     * @param dieSum    The result of the players dice roll
     * @param utility   The utility that the playing player must pay the owner
     */
    public static void multiplyRent(int dieSum, Utility utility)
    {
        int nPlayerOwnedUtilities =  PlayerController.getNumOwnedUtilities(utility.getOwner());
        utility.setRent(dieSum * Utility.MULTIPLIERS[nPlayerOwnedUtilities - 1]);
    }
}
