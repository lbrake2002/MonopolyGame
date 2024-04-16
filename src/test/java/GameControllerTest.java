import cosa.models.Die;
import cosa.models.PlayerColor;
import cosa.purple.DiceController;
import cosa.purple.GameController;
import cosa.models.Game;
import cosa.models.Player;
import cosa.purple.PlayerController;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class GameControllerTest
{
    private Player obPlayer;
    private GameController GC;
    private DiceController DC;
    private Game game;

    private Die die;

    @BeforeEach
    public void setup()
    {
        DC = new DiceController();
        GC = new GameController(DC);
        game = GC.getMonopGame();
        obPlayer = game.getCurPlayer();
        die = DC.getDie();

    }

    // Story13_a Player rolls dice/doubles and player passes go
    @Test
    public void testThatEndTurnSetsCurrentPlayerToZeroWhenPlayerCircuitFinishes()
    {
        GC.endTurn();
        assertEquals(game.getPlayerIndex(), 1);

        GC.endTurn();
        assertEquals(game.getPlayerIndex(), 2);

        GC.endTurn();
        assertEquals(game.getPlayerIndex(), 3);

        GC.endTurn();
        assertEquals(game.getPlayerIndex(), 0);
    }
    @Test
    public void testThatEndTurnIncrementsTheCurrentPlayer()
    {
        GC.endTurn();
        assertEquals(game.getPlayerIndex(), 1);

        GC.endTurn();
        assertEquals(game.getPlayerIndex(), 2);

        GC.endTurn();
        assertEquals(game.getPlayerIndex(), 3);
    }



    @Test
    public void testMovePlayerPlacesThePlayerOnTheCorrectSpaceBasedOnTheirRoll()
    {
        int startingPos = obPlayer.getPosition();
        int diceRoll = DC.rollDice();

        GC.movePlayer(startingPos, diceRoll);
        int newPos = startingPos + diceRoll;
        assertEquals(obPlayer.getPosition(), newPos);
    }

    @Test
    public void testMovePlayerPlacesThePlayerOnTheCorrectSpaceWhenTheyPassGoNormalCase()
    {
        int startingPos = 35;
        int diceRoll = 12;
        GC.movePlayer(startingPos, diceRoll);

        assertEquals(game.getCurPlayer().getPosition(), 7);
    }

    @Test
    public void testMovePlayerPlacesThePlayerOnTheCorrectSpaceWhenTheyPassGoBoundaryCase()
    {
        int startingPos = 29;
        int diceRoll = 11;
        GC.movePlayer(startingPos, diceRoll);

        assertEquals(obPlayer.getPosition(), 0);
    }

    // Story 15_d rolling three doubles in a row
    @Test
    public void testDoesPlayerGoToJailWhenTheyRollThreeDoubles() {
        DC.getDie().setDieVal1(3);
        DC.getDie().setDieVal2(3);

        GC.doTurn(DC.getSum());
        GC.doTurn(DC.getSum());
        GC.doTurn(DC.getSum());

        assertEquals(game.getCurPlayer().getPosition(), Game.JAIL); // position == 10
    }

    @Test
    public void testDoesPlayerGoToJailWhenTheyRollThreeDoublesInSeparateTurns() {
        DC.getDie().setDieVal1(3);
        DC.getDie().setDieVal2(3);

        GC.doTurn(DC.getSum());
        GC.doTurn(DC.getSum());

        DC.getDie().setDieVal1(3);
        DC.getDie().setDieVal2(2);
        GC.doTurn(DC.getSum());

        DC.getDie().setDieVal1(3);
        DC.getDie().setDieVal2(3);
        GC.doTurn(DC.getSum());

        assertEquals(game.getCurPlayer().getPosition(), 23);
    }

    @Test
    public void testDoesGoToJailMethodSendPlayerToJail() {
        GC.goToJail();
        assertEquals(game.getCurPlayer().getPosition(), Game.JAIL); // position == 10
        assertEquals(game.getCurPlayer().getInJail(), true); // position == 10
    }

    // Story15_d a player lands on go to jail
    @Test
    public void TestDoesPlayerGoToJailWhenTheyLandOnGoToJailSpace() {
        game.getCurPlayer().setPosition(27);
        DC.getDie().setDieVal1(1);
        DC.getDie().setDieVal2(2);
        GC.doTurn(DC.getSum());
        assertEquals(game.getCurPlayer().getPosition(), Game.JAIL);
        assertTrue(game.getCurPlayer().getInJail());
    }

    @Test
    public void TestPlayerDoesNotGoToJailWhenTheyLandOnAnySpaceBesidesGoToJail() {
        game.getCurPlayer().setPosition(27);
        DC.getDie().setDieVal1(2);
        DC.getDie().setDieVal2(2);
        GC.doTurn(DC.getSum());
        assertFalse(game.getCurPlayer().getPosition() == Game.JAIL);
        assertFalse(game.getCurPlayer().getInJail());
    }

    @Test
    public void TestPlayerIsVisitingJail() {
        game.getCurPlayer().setPosition(6);
        DC.getDie().setDieVal1(2);
        DC.getDie().setDieVal2(2);
        GC.doTurn(DC.getSum());
        assertTrue(game.getCurPlayer().getPosition() == Game.JAIL);
        assertFalse(game.getCurPlayer().getInJail());
    }

    @Test
    public void TestPlayerRollsThreeDoublesAndLandsOnGoToJailSpace()
    {
        DC.getDie().setDieVal1(5);
        DC.getDie().setDieVal2(5);

        GC.doTurn(DC.getSum());
        GC.doTurn(DC.getSum());
        GC.doTurn(DC.getSum());

        // Player is caught speeding (rolling 3 sets of doubles) and does not actually land on go to jail
        // Player goes to jail directly from the space they were on when they rolled their third set of doubles.
        assertTrue(game.getCurPlayer().getPosition() == Game.JAIL);
        assertTrue(game.getCurPlayer().getInJail());
    }




    @Test
    public void testThatTheNumberOfDoublesCountIsCorrectForTheCurrentPlayerAfterPreviousPlayerEndsTurn()
    {
        obPlayer.setNumDoubles(2);

        GC.endTurn();


        assertEquals(GC.getCurPlayer().getNumDoubles(), 0);
    }
    @Test
    public void testThatTheOfPositionOfTheCurrentPlayerIsCorrectAfterPreviousPlayerEndsTurn()
    {
        obPlayer.setPosition(10);

        GC.endTurn();


        assertEquals(GC.getCurPlayer().getPosition(), 0);
    }
    @Test
    public void testThatTheCurrentPlayerHasTheCorrectMonopolyMoneyAmountFollowingPreviousPlayerEndingTurn()
    {
        obPlayer.setMonoMoney(200);
        GC.endTurn();

        assertEquals(GC.getCurPlayer().getMonoMoney(), 1500);
    }

    @Test
    public void testThatWhenJailedPlayerEndsTurnTheNextPlayerIsNotInJail()
    {
        obPlayer.setInJail(true);
        GC.endTurn();

        assertEquals(GC.getCurPlayer().getInJail(), false);
    }



    @Test
    public void testRollingDoublesGetsAPlayerOutOfJail()
    {
        die.setDieVal1(3);              //set up the player to not roll doubles
        die.setDieVal2(3);

        obPlayer.setInJail(true);           //ensure the player is currently in jail

        GC.doTurn(die.getDieVal1() + die.getDieVal2());       //do the turn  note: doTurn deals with rolling and moving the player

        //the player should no longer be in jail (inJail is false)
        assertEquals(obPlayer.getInJail(), false);
    }

    @Test
    public void testNotRollingDoublesKeepsAPlayerInJail()
    {
        die.setDieVal1(3);              //set up the player to not roll doubles
        die.setDieVal2(2);

        obPlayer.setInJail(true);                   //ensure the player is currently in jail

        GC.doTurn(die.getDieVal1() + die.getDieVal2());      //do the turn note: doTurn deals with rolling and moving the player


        //the player remains in jail (inJail is true)
        assertEquals(obPlayer.getInJail(), true);
    }

    @Test
    public void testThatCurrentPlayerDoesNotMoveOffTheJailSpaceWhenTheyRollDoubles()
    {
        die.setDieVal1(3);              //set up the player to not roll doubles
        die.setDieVal2(3);

        obPlayer.setInJail(true);               //set the player to in jail
        obPlayer.setPosition(game.JAIL);        //set the players position to the jail spot (10) on the board
        GC.doTurn(die.getDieVal1() + die.getDieVal2());   //do the turn note: doTurn deals with rolling and moving the player

        //ensure the player does not move off 10 following the roll
        assertEquals(obPlayer.getPosition(), 10);
    }

    @Test
    public void testThatCurrentPlayerDoesNotMoveOffTheJailSpaceWhenTheyRollNonDoubles()
    {
        die.setDieVal1(3);              //set up the player to not roll doubles
        die.setDieVal2(2);

        obPlayer.setInJail(true);               //set the player to in jail
        obPlayer.setPosition(game.JAIL);        //set the players position to the jail spot (10) on the board
        GC.doTurn(die.getDieVal1() + die.getDieVal2());   //do the turn note: doTurn deals with rolling and moving the player

        //ensure the player does not move off 10 following the roll
        assertEquals(obPlayer.getPosition(), 10);
    }
    @Test
    public void testThatNonJailedPlayerThatIsJustVisitingCarriesOutANormalTurnWhileRollingDoubles()
    {
        die.setDieVal1(3);                                  //set up the player to not roll doubles
        die.setDieVal2(2);

        obPlayer.setPosition(game.JAIL);

        GC.doTurn(die.getDieVal1() + die.getDieVal2());
        obPlayer.setInJail(false);                           //set the player to in jail

        //ensure the player does not move off 10 following the roll
        assertEquals(obPlayer.getPosition(), 15);
    }


}
