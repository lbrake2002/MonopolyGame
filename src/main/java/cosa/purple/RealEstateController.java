package cosa.purple;

import cosa.models.RealEstate;

public class RealEstateController
{
    /**
     * Doubles the rent if the owner owns all real estate properties of the same color
     * @param realEstate    The property to double the rent amount to
     */
    public static void doubleRent(RealEstate realEstate)
    {
        if (PlayerController.isOwnerOfAllColor(realEstate.getColor(), realEstate.getOwner()))
        {
            realEstate.setRent(realEstate.getRentAmount() * 2);
        }
    }
}
