package cosa.purple;

import cosa.models.Railroad;
import cosa.models.Property;

/**
 * The class is used to perform operations on a Railroad object
 */
public class RailroadController
{
    /**
     * Updates the amount of rent that a player must pay to the owner player depending
     * on the amount of railroads that the owner player owns
     *
     * @param railroad  The railroad that the player has landed on
     */
    public static void updateRailRent(Railroad railroad)
    {
        int nAmount = PlayerController.getNumOwnedRailroads(railroad.getOwner());
        railroad.setRent(Railroad.PRICES[nAmount - 1]);
    }

}
