package cosa.purple;

import cosa.models.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * This class is used to perform operations on Player objects.
 */
public class PlayerController
{
    MonopolyController MC;


    public PlayerController(MonopolyController MC)
    {
        this.MC = MC;
    }


    /**
     * A method used to create a new Player object.
     *
     * @param name       - The players name
     * @param monoMoney  - The players monopoly money
     * @param properties - Empty arraylist to store players owned properties
     * @param position   - Players position on the board.
     * @return - A filled Player object
     */
    public static Player createPlayer(String name, int monoMoney, ArrayList<Property> properties, int position)
    {
        Player player = new Player();
        player.setAll(name, monoMoney, properties, position);
        //Will add constraint validation here later.
        return player;
    }
    /**
     <<<<<<< HEAD
     * A method used to create a new Player object
     *
     * @param name - the players name
     * @return - A filled Player object
    =======
     * This method deducts monopoly money from the player.
     *
     *
    >>>>>>> story31_a_monopoly_player_ends_their_current_turn
     */
    public static Player createPlayer(String name, PlayerColor color) {
        Player player = new Player();
        player.setAll(name, color);
        //Will add constraint validation here later.
        return player;
    }
    /**
     * This method deducts in-game Monopoly money from the player's balance.
     *
     * @param nVal - The amount of monopoly money that is to be deducted.
     */
    public static void deductFunds(Player player, int nVal) {
        player.setMonoMoney(player.getMonoMoney() - nVal);
    }

    /**
     * This method adds in-game Monopoly money to the player's balance.
     *
     * @param nVal - The amount of monopoly money that is to be added.
     */
    public static void addFunds(Player player, int nVal) {
        player.setMonoMoney(player.getMonoMoney() + nVal);
    }

    /**
     * This method checks whether the Monopoly player has enough funds when compared to a value.
     *
     * @param nVal - The amount of monopoly money to be compared to the players funds.
     * @return - True if the player has enough funds, False if they do not have enough funds.
     */
    public static boolean hasEnoughFunds(Player player, int nVal)
    {
        return nVal <= player.getMonoMoney();
    }


    /**
     * Determines how many railroads a player owns
     *
     * @param player    The player whose railroad count to get
     * @return          The amount of railroads that a player owns
     */
    public static int getNumOwnedRailroads(Player player)
    {
        int nCount = 0;
        for (Property property : player.getProperties())
        {
            if (property instanceof Railroad)
            {
                nCount++;
            }
        }

        return nCount;
    }


    /**
     * Determines how many utility properties that a player owns.
     *
     * @param player    The player whose utility count to get
     * @return          The amount of utilities that a player owns
     */
    public static int getNumOwnedUtilities(Player player)
    {
        int nCount = 0;
        for (Property property : player.getProperties())
        {
            if (property instanceof Utility)
            {
                nCount++;
            }
        }

        return nCount;
    }


    /**
     * Determines if a player owns all real estate properties of a color
     *
     * @param color     The real estate color that another player has landed on
     * @param owner     The player who owns of the real estate property
     * @return          True if the owner owns all real estate properties of the same color,
     *                  false if they do not
     */
    public static boolean isOwnerOfAllColor(RealEstateColor color, Player owner)
    {
        int nCount = 0;
        for (Property property : owner.getProperties())
        {
            if (property instanceof RealEstate)
            {
                if (((RealEstate) property).getColor() == color)
                {
                    nCount++;
                }
            }
        }

        return nCount == color.NUM_PROPS;
    }


    public static void setupPiece(Player player, int nPos)
    {
        Circle obCirc = new Circle();
        obCirc.setRadius(7);
        obCirc.setFill(player.getColor().COLOR);
        obCirc.setStroke(Color.BLACK);
        player.setGamePiece(obCirc);

        if (nPos == 0 || nPos == 1)
        {
            player.setOffsetY(0 - obCirc.getRadius() - 1);
        }
        else
        {
            player.setOffsetY(obCirc.getRadius() + 1);
        }

        if (nPos == 0 || nPos == 2)
        {
            player.setOffsetX(0 - obCirc.getRadius() - 1);
        }
        else
        {
            player.setOffsetX(obCirc.getRadius() + 1);
        }


        //Set initial position
        player.getGamePiece().setCenterX(GameBoard.dSpaceWidth / 2 + player.getOffsetX());
        player.getGamePiece().setCenterY(GameBoard.dSpaceHeight / 2 + player.getOffsetY());
    }
}
