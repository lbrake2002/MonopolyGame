package cosa.models;

import cosa.purple.GameBoardController;
import cosa.purple.MonopolyController;

import java.util.ArrayList;

/**
 * This class is the model for the GameBoard.
 * Instantiates GameBoard attributes and contains getters and setters for these.
 * Author: Kesley and John
 */

public class GameBoard
{

    public static final int SIZE_X = 11;
    public static final int SIZE_Y = 11;

    private static MonopolyController MC;

    public static final double dPaneSizeX = MC.getPaneXSize();
    public static final double dPaneSizeY = MC.getPaneYSize();
    public static final double dSpaceWidth = dPaneSizeX / GameBoard.SIZE_X;
    public static final double dSpaceHeight = dPaneSizeY / GameBoard.SIZE_Y;

    private ArrayList<GameSpace> gameSpaces; //an arraylist of gamespace objects

    public GameBoard()
    {
        gameSpaces = new ArrayList<GameSpace>();

        //used to populate the board with blank gamespaces for testing purposes - will eventually hold gamespace objects
        GameBoardController.loadGameSpaces(this);
    }


    /**
     * Getter method for the length of the "Gameboard".
     * @return - the length of the gamespaces arraylist as an int.
     */
    public int getLength()
    {
        return gameSpaces.size();
    }


    public ArrayList<GameSpace> getGameSpaces() { return gameSpaces; }

    public void addGameSpace(GameSpace space) { gameSpaces.add(space); }

    public void setMC(MonopolyController MC)
    {
        GameBoard.MC = MC;
    }

}
