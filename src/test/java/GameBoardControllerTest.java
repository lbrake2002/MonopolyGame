import cosa.models.GameBoard;
import cosa.purple.DiceController;
import cosa.purple.GameBoardController;
import cosa.purple.GameController;
import cosa.models.Game;
import cosa.models.Player;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBoardControllerTest
{
    /**
     * This test will print out all game spaces including their name, price, rent, owner, and for real estate color
     */
    @Test
    public void testLoadSpaces()
    {
        GameBoard gb = new GameBoard();
        //GameBoardController.loadGameSpaces();

        gb.getGameSpaces().forEach(System.out::println);
        assertEquals(40, gb.getLength());
    }
}
