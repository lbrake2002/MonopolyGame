package cosa.purple;

import cosa.models.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

import static cosa.models.GameBoard.*;

/**
 * This class will perform operations on GameBoard objects
 */
public class GameBoardController
{

    /**
     * Loads all game spaces into the static game board.
     */
    public static void loadGameSpaces(GameBoard gb)
    {
        try (InputStream obStream = App.class.getResourceAsStream("csvFiles/Monopoly_Spaces.csv");
                Scanner obIn = new Scanner(obStream))
        {
            //Skip header line
            obIn.nextLine();

            while(obIn.hasNextLine())
            {
                //Create a new game space for each line in the file
                String[] sArgs = obIn.nextLine().split(",");
                GameSpace obSpace;
                switch (sArgs[1])
                {
                    case "Real Estate" -> obSpace = new RealEstate();
                    case "Railroad" -> obSpace = new Railroad();
                    case "Utility" -> obSpace = new Utility();
                    default -> obSpace = new GameSpace();
                }
                obSpace.setName(sArgs[0]);
                gb.addGameSpace(obSpace);
            }
            loadProperties(gb.getGameSpaces());
        }
        catch (IOException e)
        {
            System.out.println("Error loading game spaces file: " + e.getMessage());
        }
    }


    /**
     * Helper method that calls methods to load all property data into the game board.
     */
    public static void loadProperties(ArrayList<GameSpace> lstProps)
    {
        ArrayList<RealEstate> lstRealEstate = new ArrayList<>();
        ArrayList<Railroad> lstRailroad = new ArrayList<>();
        ArrayList<Utility> lstUtility = new ArrayList<>();

        for (GameSpace obGSpace : lstProps)
        {
            if (obGSpace instanceof RealEstate)
            {
                lstRealEstate.add((RealEstate) obGSpace);
            }
            else if (obGSpace instanceof Railroad)
            {
                lstRailroad.add((Railroad) obGSpace);
            }
            else if (obGSpace instanceof Utility)
            {
                lstUtility.add((Utility) obGSpace);
            }
        }

        loadRealEstate(lstRealEstate);
        loadRailroad(lstRailroad);
        loadUtility(lstUtility);
    }


    /**
     * Loads all RealEstate data into the game board from the csv file.
     * @param lstRealEstate An ArrayList of RealEstate properties
     */
    public static void loadRealEstate(ArrayList<RealEstate> lstRealEstate)
    {
        try (InputStream obStream = App.class.getResourceAsStream("csvFiles/Monopoly_Real_Estate.csv");
             Scanner obIn = new Scanner(obStream))
        {
            //Skip headers
            obIn.nextLine();

            for (RealEstate obReal : lstRealEstate)
            {
                String[] sArgs = obIn.nextLine().split(",");
                obReal.setAll(Integer.parseInt(sArgs[1]), Integer.parseInt(sArgs[2]), null);
                obReal.setColor(RealEstateColor.valueOf(sArgs[12]));
                obReal.setImageIndex(Integer.parseInt(sArgs[13]));
            }
        }
        catch (IOException e)
        {
            System.out.println("Error loading real estate file: " + e.getMessage());
        }
    }


    /**
     * Loads all Railroad data into the game board from the csv file.
     * @param lstRailroad   An ArrayList of Railroads
     */
    public static void loadRailroad(ArrayList<Railroad> lstRailroad)
    {

        try (InputStream obStream = App.class.getResourceAsStream("csvFiles/Monopoly_Railroads.csv");
             Scanner obIn = new Scanner(obStream))
        {
            //Skip headers
            obIn.nextLine();

            for (Railroad obRail : lstRailroad)
            {
                String[] sArgs = obIn.nextLine().split(",");
                obRail.setAll(Integer.parseInt(sArgs[1]), 25, null);
                obRail.setImageIndex(Integer.parseInt(sArgs[4]));
            }
        }
        catch (IOException e)
        {
            System.out.println("Error loading real estate file: " + e.getMessage());
        }
    }


    /**
     * Loads all Utility data into the game board from the csv file.
     * @param lstUtility    An ArrayList of Utilities
     */
    public static void loadUtility(ArrayList<Utility> lstUtility)
    {
        try (InputStream obStream = App.class.getResourceAsStream("csvFiles/Monopoly_Utilities.csv");
             Scanner obIn = new Scanner(obStream))
        {
            //Skip headers
            obIn.nextLine();

            for (Utility obUtil : lstUtility)
            {
                String[] sArgs = obIn.nextLine().split(",");
                obUtil.setAll(Integer.parseInt(sArgs[1]), 0, null);
                obUtil.setImageIndex(Integer.parseInt(sArgs[4]));
            }
        }
        catch (IOException e)
        {
            System.out.println("Error loading real estate file: " + e.getMessage());
        }
    }

    /**
     * Sets the coordinates for the game spaces on the pane
     * @param GB    The game board that the game spaces are placed in
     * @param MC    The Monopoly controller used to get the Pane size
     */
    public static void setGameSpaceCoordinates(GameBoard GB, MonopolyController MC)
    {
        for(int i = 0; i < GB.getLength(); i++)
        {
            double dCoordinateX;
            double dCoordinateY;

            //Top side of the board
            if (i < 10)
            {
                dCoordinateX = dSpaceWidth * i + (dSpaceWidth / 2.0);
                dCoordinateY = dSpaceHeight / 2.0;

            }
            //Right side of the board
            else if (i < 20)
            {
                dCoordinateX = dPaneSizeX - (dSpaceWidth / 2.0);
                dCoordinateY = dSpaceHeight * (i - 10) + (dSpaceHeight / 2.0);
            }
            //Bottom side of the board
            else if(i < 30)
            {
                dCoordinateX = dPaneSizeX - (dSpaceWidth * (i - 20)) - (dSpaceWidth / 2.0);
                dCoordinateY = dPaneSizeY - (dSpaceHeight / 2.0);
            }
            //Left side of the board
            else
            {
                dCoordinateX = dSpaceWidth / 2;
                dCoordinateY = dPaneSizeY - (dSpaceHeight * (i - 30)) - (dSpaceWidth / 2.0);
            }

            GB.getGameSpaces().get(i).setCoordinateX(dCoordinateX);
            GB.getGameSpaces().get(i).setCoordinateY(dCoordinateY);
        }
    }
}
