package cosa.models;

import cosa.purple.App;
import cosa.purple.PlayerController;
import cosa.purple.UserView;
import jakarta.validation.constraints.*;

/**
 * This class is the model for the Game.
 * Instantiates Game attributes and contains getters and setters for these.
 * Author: Kesley and John
 */
public class Game
{
    /**
     * This final int represents the jail space position on the monopoly board.
     */
    public static final int JAIL = 10;
    /**
     * This final int represents the Go-To-Jail space position on the monopoly board.
     */
    public static final int GO_TO_JAIL = 30;
    private GameBoard gameboard; // Represents the GameBoard object that holds all the GameSpaces

    @Size(min = 4, max = 4, message = "Number of players must be 4")
    private Player[] players; // array of Player objects participating in this Game

    @Min(value = 0, message = "Player 1 should be in zeroth spot of array")
    @Max(value = 3, message = "Player 4 should be in the third spot of the array")
    private int playerIndex; // the index of the current player in the players array
    private Player curPlayer; //The Player object that is the current player of the game - whose turn it currently is


    public Game()
    {
    }

    /**
     * This method sets all the values for the Game object.
     * Must be called when a Game object is created to assign default values to its properties
     */
    public void setAllValues()
    {
        this.gameboard = new GameBoard();
        this.players = new Player[4];

        //populate the players array with new players - holder players for testing purposes
        for (int i = 0; i < players.length; i++) {
            try
            {
                players[i] = PlayerController.createPlayer(App.getPlayerNames().get(i), PlayerColor.values()[i]);
            }
            catch (NullPointerException e)
            {
                //Login screen is not run during tests, so we use this instead.
                players[i] = PlayerController.createPlayer("Player" + i, PlayerColor.values()[i]);
            }
            PlayerController.setupPiece(players[i], i);
        }

        this.playerIndex = 0;
        curPlayer = players[playerIndex];
    }

    public GameBoard getGameboard() {
        return gameboard;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    /**
     * Getter method to get the index of the current player.
     * @return - the current player object
     */
    public Player getCurPlayer() {
        return curPlayer;
    }

    public void setCurPlayer(Player curPlayer) {
        this.curPlayer = curPlayer;
    }
//    public void setCurPlayer(int index) {
//        this.curPlayer = curPlayer;
//    }

    /**
     * Getter method to get the current player object.
     * @return - the index of the current player in the Player array
     */
    public int getPlayerIndex()
    {
        return playerIndex;
    }
}
