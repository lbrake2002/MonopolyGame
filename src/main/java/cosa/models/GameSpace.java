package cosa.models;

import static cosa.models.SpaceType.*;

/**
 * This class is the model for the GameSpace.
 * Instantiates GameSpace attributes and contains getters and setters for these.
 * Author: Dave and Brett
 */

public class GameSpace {

    private SpaceType type;
    private String name;

    private double coordinateX;

    private double coordinateY;
    public GameSpace()
    {
        
    }

    /**
     * This represents the event that happens when a player lands on this space
     * @param player    The player that landed on this space
     */
    public void doEvent(Player player)
    {
        //Anything other than a Property will not really do anything here in this release.
    }

    /**
     * This checks whether the game space is a property space.
     * @return Returns true if game space is a property space.
     */
    public boolean isProperty()
    {
        return type == PROPERTY;
    }

    public String getName() { return name; }

    public void setName(String sName) {this.name = sName;}

    public double getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }


    @Override
    public String toString()
    {
        return String.format("Name: %s", this.getName());
    }


}
