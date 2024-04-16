package cosa.purple;

import cosa.models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class App extends Application {

    private User user1; // A dummy user object
    private UserController userCtrl; // the UserController object used to add the user to the database
    private static ArrayList<String> playerNames; // A list of player names from the login screen

    @Override
    public void start(Stage stage) throws IOException {
        playerNames = new ArrayList<>();
        this.userCtrl = new UserController();
        initDB();
        UserView v = new UserView(user1);
        v.showModalDialog(stage);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("monopoly-game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 900);
        stage.setTitle("Monopoly Game");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method initializes the database
     */
    protected void initDB() {
        // Dummy user
        user1 = new User();
        user1.setAllValues("", 26);
        this.userCtrl.addUser(user1);
    }

    public static ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    public static void addPlayerNames(String playerName) {
        App.playerNames.add(playerName);
    }

    public static void main(String[] args) {
        launch();
    }
}