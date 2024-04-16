package cosa.models;

import cosa.models.Property;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * This class is the model for the Player.
 * Instantiates Player attributes and contains getters and setters for these.
 * Author: Kesley, John, Dave, Brett
 */
public class Player {
    /**
     * This represents the amount of in-game Monopoly money every Player will start the game with
     */
    public static final int STARTING_MONEY = 1500;

    /**
     * This represents the starting position of each Player in the game. Start of the board
     */
    public static final int STARTING_POS = 0;

    @Min(value = 0, message = "Players position must be a positive number")
    @Max(value = 39, message = "Players position can be at most 39")
    private int position; // represents the current position of this player

    @Min(value = 0, message = "Players MonoMoney must be a positive number")
    private int monoMoney; // represents this Player's amount of money in the game

    @NotEmpty(message = "Player name cannot be empty")
    @Size(min = 2, max = 20, message = "Player name must be between 2 and 20 characters inclusive")
    private String name; // This is the name of this player

    @NotNull(message = "Players property list cannot be null")
    @Size(max = 28, message = "Players can own between 0 and 28 properties inclusive")
    private ArrayList<Property> properties; // the Properties this Player owns

    private boolean inJail; // true if the Player is in Jail, false otherwise

    @Min(value = 0, message = "Player cannot roll a negative amount of doubles")
    @Max(value = 3, message = "Player can roll a max of 3 doubles before being sent to jail")
    private int numDoubles; // this is the number of doubles this player rolled this turn

    private PlayerColor color;
    private Circle gamePiece;
    private double offsetX, offsetY;

    public Player() {
    }

    /**
     * This method sets all the values for the Player object.
     * Must be called when a Player object is created to assign values to its properties
     *
     * @param name       - the name of this Player
     * @param monoMoney  - the amount of in-game Monopoly money this Player starts the game with
     * @param properties - the Arraylist of Properties this Player owns (should be empty at the start of the game)
     * @param position   - the position on the board this Player starts the game at (0 is the start (GO! tile))
     */
    public void setAll(String name, int monoMoney, ArrayList<Property> properties, int position) {
        this.name = name;
        this.monoMoney = monoMoney;
        this.properties = properties;
        this.position = position;
        this.numDoubles = 0;
        this.inJail = false;
    }

    /**
     * Another value setter for the Player object, but only requires a name
     * The other values are created using default/final values
     *
     * @param name - the name of this Player
     */
    public void setAll(String name, PlayerColor color) {
        this.name = name;
        this.monoMoney = STARTING_MONEY;
        this.properties = new ArrayList<>();
        this.position = 0;
        this.numDoubles = 0;
        this.inJail = false;
        this.color = color;
    }

    /**
     * Adds a property to this player's property list.
     *
     * @param obProp - The property to be added
     */
    public void addProperty(Property obProp) {
        properties.add(obProp);
    }

    // The following are Getters/Setters for the Player class
    public int getPosition() {
        return this.position;
    }

    public void setPosition(int nVal) {
        this.position = nVal;
    }

    public void setName(String sName)
    {
        this.name = sName;
    }

    public String getName()
    {
        return this.name;
    }

    public int getMonoMoney() {
        return this.monoMoney;
    }

    public void setMonoMoney(int nVal) {
        this.monoMoney = nVal;
    }

    public ArrayList<Property> getProperties() {
        return this.properties;
    }

    public void setProperties(ArrayList<Property> lstProps) {
        properties = lstProps;
    }

    public int getNumDoubles() {
        return numDoubles;
    }

    public void setNumDoubles(int numDoubles) {
        this.numDoubles = numDoubles;
    }

    public void setInJail(boolean bInJail) {
        this.inJail = bInJail;
    }


    public boolean getInJail() {
        return inJail;
    }

    public PlayerColor getColor() { return color; }
    public void setColor(PlayerColor color) { this.color = color; }

    public Circle getGamePiece() { return gamePiece; }
    public void setGamePiece(Circle circle) { gamePiece = circle; }

    public double getOffsetX()
    {
        return offsetX;
    }

    public void setOffsetX(double offsetX)
    {
        this.offsetX = offsetX;
    }

    public double getOffsetY()
    {
        return offsetY;
    }

    public void setOffsetY(double offsetY)
    {
        this.offsetY = offsetY;
    }
}
