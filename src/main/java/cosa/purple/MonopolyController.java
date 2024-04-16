package cosa.purple;

import cosa.models.GameBoard;
import cosa.models.GameSpace;
import cosa.models.Game;
import cosa.models.Player;
import cosa.models.Property;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import static cosa.models.GameBoard.*;
import static cosa.models.GameBoard.dSpaceWidth;

public class MonopolyController implements Initializable {
    @FXML
    private Label lblCurrentPlayer; // Label for the current player
    @FXML
    private Label lblDoubles; // Label for if doubles are rolled
    @FXML
    private Label lblMonoMoney; // label for the current Player's in game money
    @FXML
    private Label lblPosition; // Label for the current Player's position on the board
    @FXML
    private Label lblPassedGo; // Label for if the Player has passed go this roll
    @FXML
    private Label lblJail;    // Label for a jailed player to alert them if they have successfully get out of jail or not.
    @FXML
    private Button btnRollDice; // Label for if the Player has passed go this roll
    @FXML
    private Button btnEndTurn; // Button to end the current player's turn.
    @FXML
    private ImageView ivDice1; // ImageView for the first die in the GUI
    @FXML
    private ImageView ivDice2; // ImageView for the second die in the GUI
    @FXML
    private Pane boardPane; // a Pane that show the game board
    @FXML
    private GridPane propertyGrid; // a grid pane that displays the current player's owned properties
    @FXML
    private Label lblPropertyPlayer; // label that is a title for the grid of owned properties
    private DiceController DC; // a DiceController object to use methods from that class
    private GameController GC; // a GameController object to use methods from that class


    /**
     * This method will be called when the "Roll Dice" button is clicked.
     * It calls the rolling dice sequence, moves the Player, and updates the Labels/buttons on the screen
     */
    @FXML
    protected void onRollDiceButtonClick() {
        GC.doTurn(DC.rollDice());
        new Thread(() -> GC.getMonopGame().getGameboard().getGameSpaces().get(GC.curPlayer.getPosition()).doEvent(GC.curPlayer)).start();
        lblCurrentPlayer.setText("You rolled an " + DC.getSum());

        // will be true if the Player has landed on the Go-To Jail to end up in Jail
        if(GC.curPlayer.getInJail() && GC.curPlayer.getNumDoubles() < 3)
        {
            updateJail();
        }
        else
        {
            setDoublesValues(DC.isDoubles(), GC.curPlayer.getNumDoubles());
        }

        //the following deals with if the current player is currently in jail
        if(GC.curPlayer.getInJail())
        {
            if(DC.isDoubles())  //if the player rolls doubles
            {
                lblJail.setText("You rolled doubles! You may now leave jail and roll on your next turn."); //set the jail label to cue the player they are out of jail
            }
            lblJail.setText("You did not roll doubles, remain in jail for another turn.");              //if they do not roll doubles cue the player they are in jail
        }

        updatePlayerGUI();
    }


    /**
     * Updates the GUI when a player rolls the dice to update text and players position
     */
    public void updatePlayerGUI()
    {
        try
        {
            Platform.runLater(() -> {
                try
                {
                    int nPos = GC.curPlayer.getPosition();
                    lblPosition.setText("Your position on the board is: " + GC.getMonopGame().getGameboard().getGameSpaces().get(nPos).getName());
                    lblCurrentPlayer.setText(GC.curPlayer.getName() + "'s Turn");
                    URL obURL = App.class.getResource("images/die_" + DC.getDie().getDieVal1() + ".png");
                    ivDice1.setImage(new Image(obURL.openStream()));
                    obURL = App.class.getResource("images/die_" + DC.getDie().getDieVal2() + ".png");
                    ivDice2.setImage(new Image(obURL.openStream()));
                    lblPassedGo.setText(GC.getPassedGoMessage());
                    lblMonoMoney.setText("$" + GC.curPlayer.getMonoMoney());
                    loadOwnedProperties();
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }

            });
        }
        catch(IllegalStateException e)
        {
            System.out.printf("This is part of a test deprecate GUI functionality\n");
        }

    }


    public void loadOwnedProperties()
    {
        propertyGrid.getChildren().clear();
        lblPropertyPlayer.setText(GC.curPlayer.getName() + "'s Properties");
        int nSize = GC.curPlayer.getProperties().size();
        for (int i = 0; i < nSize; i++)
        {
            int xIndex = i % propertyGrid.getColumnCount();
            int yIndex = i / propertyGrid.getColumnCount();

            Property obProp = GC.curPlayer.getProperties().get(i);
            URL obURL = App.class.getResource("images/GameSpaces/GameSpace" + obProp.getImageIndex() + ".png");
            try
            {
                ImageView obImage = new ImageView(new Image(obURL.openStream()));

                obImage.setFitHeight(100);
                obImage.setFitWidth(100);

                propertyGrid.add(obImage, xIndex, yIndex);
            }
            catch (IOException e)
            {
                System.out.println("Error loading " + "GameSpace" + obProp.getImageIndex() + ".png");
            }
        }
    }

    /**
     * Will implement the functionality later.
     */
    @FXML
    protected void onEndTurnButtonClick()
    {
        lblJail.setText("");
        GC.endTurn();
        lblCurrentPlayer.setText(GC.curPlayer.getName() + "'s Turn");

        lblPosition.setText("Your position on the board is: " + GC.curPlayer.getPosition()); //cue the player of their position on the board
        updatePlayerGUI();
        btnEndTurn.setDisable(true);
        btnRollDice.setDisable(false);
    }

    /**
     * This method will take in a boolean and int and update the lblDoubles depending on true or false and int value
     *
     * @param isDoubles  - a boolean saying if doubles were rolled
     * @param numDoubles - an int holding the amount of doubles rolled this turn
     */
    public void setDoublesValues(boolean isDoubles, int numDoubles)
    {
        if (isDoubles)
        {
            if (numDoubles >= 3)
            {
                // Player is sent to Jail for rolling 3 doubles in a row
                lblDoubles.setText("You've been caught speeding. Go to Jail.");
                btnRollDice.setDisable(true);
                btnEndTurn.setDisable(false);
            }
            else
            {
                // Player rolled 1 or 2 doubles this turn and rolls again
                lblDoubles.setText("You rolled doubles " + numDoubles + " time(s), roll again");
                btnEndTurn.setDisable(true);
            }
        }
        else
        {
            // No doubles were rolled and Player can end turn
            lblDoubles.setText("");
            btnRollDice.setDisable(true);
            btnEndTurn.setDisable(false);
        }
    }

    /**
     * A method to set up a property and player to show the client what the game function may look like.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        btnEndTurn.setDisable(true);
        DC = new DiceController();
        GC = new GameController(DC);
        PropertyController.setMC(this);

        for (Player obPlayer : GC.getMonopGame().getPlayers())
        {
            boardPane.getChildren().add(obPlayer.getGamePiece());
        }
        updatePlayerGUI();

        GameBoardController.setGameSpaceCoordinates(GC.getMonopGame().getGameboard(), this);
    }

    /**
     * This method updates the lblDoubles to say the Player is in Jail and adjusts enabled buttons
     */
    private void updateJail()
    {
        lblDoubles.setText("You landed on Go-To Jail. You are in jail.");
        btnRollDice.setDisable(true);
        btnEndTurn.setDisable(false);
    }


    /**
     * Places the owner marker on a property to indicate to other players that they
     * own that property
     * @param property  The property that the owner purchased
     */
    public void placeOwnerMarker(Property property)
    {
        try
        {
            Platform.runLater(() -> {
                Rectangle obRec = new Rectangle();
                obRec.setWidth(10);
                obRec.setHeight(10);
                obRec.setStroke(Color.BLACK);
                obRec.setFill(property.getOwner().getColor().COLOR);

                setRecCoords(obRec, property);

                boardPane.getChildren().add(obRec);
            });
        }
        catch(IllegalStateException e)
        {
            System.out.println("This is part of a test deprecate GUI functionality");
        }


    }


    /**
     * Sets the rectangle coordinates on the board when a player purchases a property
     * @param obRec     The rectangle to set the coordinated to
     * @param property  The property that the rectangle will be placed on
     */
    public void setRecCoords(Rectangle obRec, Property property)
    {
        //Top side of the board
        //Using owner's position because if they are buying, then they are already on the property
        if (property.getOwner().getPosition() < 10)
        {
            obRec.setX(property.getCoordinateX() - (GameBoard.dSpaceWidth / 2) + 1);
            obRec.setY(property.getCoordinateY() - (GameBoard.dSpaceHeight / 2) + 1);
        }
        //Right side of the board
        else if (property.getOwner().getPosition() < 20)
        {
            obRec.setX(property.getCoordinateX() + (GameBoard.dSpaceWidth / 2) - 1 - obRec.getWidth());
            obRec.setY(property.getCoordinateY() - (GameBoard.dSpaceHeight / 2) + 1);
        }
        //Bottom side of the board
        else if(property.getOwner().getPosition() < 30)
        {
            obRec.setX(property.getCoordinateX() + (GameBoard.dSpaceWidth / 2) - 1 - obRec.getWidth());
            obRec.setY(property.getCoordinateY() + (GameBoard.dSpaceHeight / 2) - 1 - obRec.getHeight());
        }
        //Left side of the board
        else
        {
            obRec.setX(property.getCoordinateX() - (GameBoard.dSpaceWidth / 2) + 1);
            obRec.setY(property.getCoordinateY() + (GameBoard.dSpaceHeight / 2) - 1 - obRec.getHeight());
        }
    }


    /**
     * Used to test the positions of each game space
     * @param lstSpaces A list containing all spaces on the Game Board
     */
    public void drawSpaceCoords(ArrayList<GameSpace> lstSpaces)
    {
        for (GameSpace obSpace : lstSpaces)
        {
            Circle obCirc = new Circle();
            obCirc.setRadius(5);
            obCirc.setCenterX(obSpace.getCoordinateX());
            obCirc.setCenterY(obSpace.getCoordinateY());
            boardPane.getChildren().add(obCirc);
        }
    }


    public DiceController getDC() { return this.DC; }

    public static double getPaneXSize()
    {
        return 800;
    }
    public static double getPaneYSize()
    {
        return 800;
    }


    /**
     * Asks the player if they want to purchase the vacant property they landed on.
     *
     * @param buyer The player who landed on the space
     * @return  true if they want to purchase the property, false if not
     */
    public static boolean askPurchase(Property space, Player buyer)
    {
        if (buyer.getMonoMoney() >= space.getPrice())
        {
            System.out.printf("Do you wish to purchase %s for %d? Y/N\n", space.getName(), space.getPrice());
            Scanner obn = new Scanner(System.in);

            return obn.nextLine().matches("[Yy]");
        }
        return false;
    }
}