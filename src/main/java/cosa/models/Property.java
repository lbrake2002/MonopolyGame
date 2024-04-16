package cosa.models;

import cosa.purple.PropertyController;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * This class is a type of game space that represents the different properties that players can buy in a Monopoly game.
 */
public class Property extends GameSpace
{
    @Min(value=1, message = "Property price must be a value greater than 0")
    private int price;
    private Player owner;
    @Min(value=1,message = "Property rent must be a value greater than 0")
    private int rentAmount;
    @Min(value=1, message = "Property image index must be a value greater than 0")
    @Max(value = 39, message = "Property image index must be less than 40")
    private int imageIndex;


    public Property() {
    }

    /**
     *
     * @param price - the price to purchase this Property
     * @param nRent - the amount of rent a Player will pay to the owner when this Property is landed on
     * @param owner - the Player that owns of this Property
     */
    public void setAll(int price, int nRent, Player owner) {
        this.price = price;
        this.rentAmount = nRent;
        this.owner = owner;
    }

    // The following are Getters/Setters for the Property class

    @Override
    public void doEvent(Player player)
    {
        PropertyController.checkProperty(this, player);
    }


    public int getPrice() {
        return this.price;
    }

    public Player getOwner() {
        return this.owner;
    }

    public int getRentAmount() {
        return this.rentAmount;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setPrice(int nVal) { this.price = nVal; }
    public void setRent(int nVal) { this.rentAmount = nVal; }
    public void setImageIndex(int nVal) { this.imageIndex = nVal; }
    public int getImageIndex() { return this.imageIndex; }


    @Override
    public String toString()
    {
        return String.format("Name: %s\tPrice: %d\tRent: %d\tOwner: %s", this.getName(), price, rentAmount, owner);
    }
}


