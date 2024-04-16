package cosa.purple;

import cosa.models.*;

import java.util.Comparator;
import java.util.Scanner;

/**
 * This class is used to perform operations on Property objects.
 */
public class PropertyController
{
    private static MonopolyController MC;


    public static void setMC(MonopolyController controller)
    {
        MC = controller;
    }
    public static MonopolyController getMC()
    {
        return MC;
    }

    /**
     * This represents when a player lands on this space.
     * It just calls the check property method for this class.
     *
     * @param player - The player that landed on this space
     */
    public static void doEvent(Property space, Player player)
    {
        checkProperty(space, player);
    }

    /**
     * This checks to see if the property space is currently owned.
     * If not owned, the buyer has a chance to purchase it.
     * If owned, the buyer has to pay rent to the owner.
     *
     * @param space - The space that was landed on
     * @param player - The player who landed on the space
     */
    public static boolean checkProperty(Property space, Player player)
    {
        if (checkVacancy(space))
        {
            if(MonopolyController.askPurchase(space, player))
            {
                purchase(space, player);
                return true;
            }
        }
        else
        {
            //Player must pay rent
            if (space.getOwner() != player)
            {
                System.out.printf("You owe %s $%d in rent.", space.getOwner().getName(), space.getRentAmount());
                payRent(space, player);
                MC.updatePlayerGUI();
            }
        }
        return false;
    }


    /**
     * Checks to see if this property is already owned.
     *
     * @return - true if owned, false if not owned
     */
    public static boolean checkVacancy(Property space) {
        return space.getOwner() == null;
    }


    /**
     * Sets the owner for this property to the buyer and deducts their funds accordingly.
     * Used to return a boolean. Decided it didn't need to. Set to void
     *
     * @param buyer - The monopoly player using their turn to call the purchase method
     */
    public static void purchase(Property space, Player buyer)
    {
        PlayerController.deductFunds(buyer, space.getPrice());
        space.setOwner(buyer);
        buyer.addProperty(space);
        //Sort properties by order
        buyer.getProperties().sort(Comparator.comparingInt(Property::getImageIndex));
        MC.placeOwnerMarker(space);
        MC.updatePlayerGUI();
    }

    /**
     * A method used to create a new Property object
     *
     * @param price - the price to purchase a Property
     * @param nRent - the amount a Player must pay to the owner when they land on this Property
     * @param owner - the Player that owns this Property
     * @return - a filled Property object
     */
    public static Property createProperty(int price, int nRent, Player owner) {
        Property property = new Property();
        property.setAll(price, nRent, owner);
        //At some point, we will also add validation here.
        return property;
    }


    /**
     * Will subtract monopoly money out of the player who lands on another players property and
     * will add monopoly money to the owner's monopoly money. Also determines what kind of property
     * that the player has landed on.
     *
     * @param property  the property that is landed on
     */
    public static void payRent(Property property, Player player)
    {
        System.out.println(property.getClass());
        if (property instanceof Railroad)
        {
            // Update the railroad rent based on how many Railroads the owner owns
            RailroadController.updateRailRent((Railroad) property);
        }
        else if (property instanceof Utility)
        {
            int nDiceTotal = MC.getDC().getSum();
            UtilityController.multiplyRent(nDiceTotal, (Utility) property);
        }
        else if (property instanceof RealEstate)
        {
            RealEstateController.doubleRent((RealEstate) property);
        }

        //Transfer funds between the players
        PlayerController.addFunds(property.getOwner(), property.getRentAmount());
        PlayerController.deductFunds(player, property.getRentAmount());
    }
}
