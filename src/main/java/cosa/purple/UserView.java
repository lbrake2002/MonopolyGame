package cosa.purple;

import cosa.purple.UserController;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import cosa.models.*;

public class UserView implements Initializable {
    private User user; // User object that gets put into the database
    private Stage dialog; // The splash screen object

    @Min(value = 0, message = "There must be 4 players")
    @Max(value = 4, message = "There must be 4 players")
    private static int numPlayers = 0; // counter for seeing how many Players have logged in


    public UserView(User user) {
        this.user = user;
    }

    /***
     * Creates and shows a modal dialog and pauses caller execution until modal is closed
     * @param mainStage - Stage set as the parent to the modal dialog
     */
    public void showModalDialog(Stage mainStage) {

        this.dialog = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("user-view.fxml"));
        //ignore the FXML controller and set the controller to this instance
        fxmlLoader.setControllerFactory(cl -> this);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            this.dialog.setTitle("Welcome");
            this.dialog.setScene(scene);
            this.dialog.initOwner(mainStage);
            this.dialog.initModality(Modality.APPLICATION_MODAL);
            this.dialog.showAndWait(); // pause execution until modal dialog closes
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private TextField txtName; // TextField for entering a Users name
    @FXML
    private TextField txtAge; // TextField for entering a Users age
    @FXML
    private Label lblNameError; // Label to display if the name is invalid
    @FXML
    private Label lblAgeError; // Label to display if the age is invalid

    public static String[] playerNames;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        playerNames = new String[4];
        // set all the inputs from the passed in User object
    }

    /**
     * This method checks to see if the User object is valid.
     * Validation would be further implemented in the future.
     *
     * @return - if true returns the User object, otherwise return null
     */
    public User getUserIfValid() {
        ValidationHelper vh = new ValidationHelper();
        User tempUser = new User();
        tempUser.setId(user.getId());
        tempUser.setName(txtName.getText());
        tempUser.setAge(Integer.parseInt(txtAge.getText()));

        //check that the data the user typed in is valid
        HashMap<String, String> errors = vh.getErrors(tempUser);
        if (errors.size() > 0) {
            //if there are errors set the error messages
            lblNameError.setText(errors.get("name"));
            lblAgeError.setText(errors.get("age"));
            return null;
        }
        lblNameError.setText(errors.get(""));
        lblAgeError.setText(errors.get(""));
        return tempUser;
    }


    /**
     * This method gets called when the "Login" button is clicked.
     * It adds a User object to the database based on the name and age given in the TextFields
     */
    @FXML
    public void onLoginClicked() {

        //GET the values form the controls and make a new temporary User object
        User tempUser = this.getUserIfValid();

        if (tempUser != null) {
            // try to save the User to the database
            UserController cc = new UserController();
            tempUser = cc.saveUser(tempUser);
            this.user.cloneValues(tempUser);

            numPlayers++;
            App.addPlayerNames(txtName.getText()); // Add player names to the ArrayList used to create the in-game Players
            clearTextFields();
            // exit method if 4 Players have not logged in yet
            if (numPlayers < 4) {
                return;
            }

            if (this.dialog.isShowing()) {
                this.dialog.close(); // Close the login screen after 4 users signed in
            }

        }
    }

    private void clearTextFields() {
        txtName.setText("");
        txtAge.setText("");
    }
}




