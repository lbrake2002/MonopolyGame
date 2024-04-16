package cosa.purple;


import cosa.models.Game;
import cosa.models.GameSpace;
import cosa.models.Player;
import cosa.models.PlayerColor;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable
{

    /**
     * Player object used to access Player properties needed for this class.
     */
    public static Player curPlayer;
    private DiceController DC; //DiceController object used to access DiceController methods needed for this class.

    private Game monopGame;    //Game object used to access Game properties needed for this class.
    private String passedGoMessage = ""; //String that will be set in its corresponding GUI label if the player passes go

    /**
     * Constructor for the GameController object
     * @param DC - a DiceController object that is used to control the Dice rolls
     */
    public GameController(DiceController DC)
    {
        this.monopGame = new Game();
        monopGame.setAllValues();

        monopGame.setCurPlayer(monopGame.getPlayers()[0]);
        this.curPlayer = monopGame.getCurPlayer();
        this.DC = DC;
    }

    /**
     * This method will be called from the MonopolyController when the "Roll Dice" button is clicked.
     * The method calls rollDice(), which will return an int that reflects the roll of two 6 sided dice.
     * Finds the current position of the player whose turn it currently is.
     * use the movePlayer method to move the current player to the new position.
     *
     */
    public void doTurn(int diceRoll)
    {
        //find current position
        int playerPos =  curPlayer.getPosition();
        //future testing will be done, doesn't change any values yet
        if(checkDoubles())
        {
            return;
        }
//        playerPos = curPlayer.getPosition();



        if(curPlayer.getInJail())
        {

            //if the outcome of this roll is true, the player is set to not be in jail
            if(DC.isDoubles())
            {
                curPlayer.setInJail(!DC.isDoubles());
            }
        }
        else
        {
            //move current player
            movePlayer(playerPos, diceRoll);
            playerPos = curPlayer.getPosition();
        }

        GameSpace obSpace = monopGame.getGameboard().getGameSpaces().get(playerPos);
        curPlayer.getGamePiece().setCenterX(obSpace.getCoordinateX() + curPlayer.getOffsetX());
        curPlayer.getGamePiece().setCenterY(obSpace.getCoordinateY() + curPlayer.getOffsetY());
    }


    /**
     * This method will deal with who rolls next based on if doubles are rolled or not.
     */
    private boolean checkDoubles()
    {
        if(DC.isDoubles())
        {
            //future check if 3 doubles rolled
            curPlayer.setNumDoubles(curPlayer.getNumDoubles()+1);
            if (curPlayer.getNumDoubles() >= 3)
            {
                // curPlayer will go to jail
                goToJail();
                return true;
            }
        }
        else
        {
            // reset the number of doubles rolled this turn for the curPlayer
            curPlayer.setNumDoubles(0);

        }
        return false;
    }


    /**
     * This method sets the current players position based on their starting position
     * and the value of the dice rolled.
     * @param playerPosition - the current players position before the roll
     * @param diceRoll - the sum of the dice rolled
     */
    public void movePlayer(int playerPosition, int diceRoll)
    {
        if(passedGo(playerPosition, diceRoll))
        {
            int newPos = (playerPosition + diceRoll) - monopGame.getGameboard().getLength();
            curPlayer.setPosition(newPos);
            PlayerController.addFunds(curPlayer, 200);
            setPassedGoMessage("You have passed GO!, you receive 200 Monopoly Money!");
        }
        else
        {
            curPlayer.setPosition(playerPosition + diceRoll);
            setPassedGoMessage("");
            if(curPlayer.getPosition() == Game.GO_TO_JAIL)
            {
                goToJail();
            }
        }
//        System.out.println("Current players new position is: " +  curPlayer.getPosition());
    }

    /**
     * This method checks to see if the dice rolled will move the current player past the "GO!" space.
     * The "GO!" space is the first space in the GameBoard.
     * @param playerPosition - the current players position before the roll
     * @param diceRoll - the sum of the dice rolled
     * @return - true if the dice rolled will move the current player past "GO!", false otherwise.
     */
    public boolean passedGo(int playerPosition, int diceRoll)
    {
        return playerPosition + diceRoll >= monopGame.getGameboard().getLength();
    }

    /**
     * This method sets the current Player's position to the Jail space and sets their inJail status to true.
     */
    public void goToJail()
    {
        curPlayer.setPosition(Game.JAIL);
        curPlayer.setInJail(true);
    }


    /**
     * This method will be called from the MonopolyController when the "End Turn" button is clicked.
     * This method:
     *  1) moves the current player index to the next Player
     *  2) sets the current players doubles rolled value to 0
     *  3) sets the new current player
     *  4) prints out who the new current player is
     *  This will be used for future implementation.
     */
    public void endTurn()
    {
        //go to next player's turn
        //disable end turn button for next player
        curPlayer.setNumDoubles(0);
        if(monopGame.getPlayerIndex() + 1 < monopGame.getPlayers().length)
        {
            monopGame.setPlayerIndex(monopGame.getPlayerIndex() + 1);
        }
        else
        {
            //reset current player to the first player - at the end of a player circuit
            monopGame.setPlayerIndex(0);

        }

        curPlayer = monopGame.getPlayers()[monopGame.getPlayerIndex()];
        monopGame.setCurPlayer(curPlayer);

    }


    public static Player getCurPlayer()
    {
        return curPlayer;
    }

    // The following are Getters/Setters for the GameController class
    public String getPassedGoMessage() {
        return passedGoMessage;
    }

    public void setPassedGoMessage(String passedGoMessage) {
        this.passedGoMessage = passedGoMessage;
    }

    public Game getMonopGame() {
        return monopGame;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.monopGame = new Game();
        this.curPlayer = monopGame.getCurPlayer();
    }
}
