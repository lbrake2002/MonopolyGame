package cosa.purple;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import cosa.models.User;
import cosa.models.ValidationHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    private ConnectionSource dbConn; // Object used to connect to the database
    private Dao<User, Long> userDB; // the DAO Object
    private ValidationHelper vh = new ValidationHelper(); // object used to help validate object properties based on constraints

    public UserController() {
        try {
            // Connect to the database
            this.dbConn = new JdbcPooledConnectionSource(GlobalConstants.CONNECTION_STRING);
            this.initUserDB(dbConn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***
     * Separating this method from the constructor makes it easier to test the controller
     * Initialize the Data Access Object and ensure the table exists in the database
     * @return int
     * @throws SQLException
     */
    public int initUserDB(ConnectionSource dbConn) throws SQLException {
        // create Data access Object (DAO)  a.k.a Repository
        this.userDB = DaoManager.createDao(dbConn, User.class);
        userDB.setAutoCommit(dbConn.getReadWriteConnection("users"), true);
        //ensure table exist
        return TableUtils.createTableIfNotExists(dbConn, User.class);
    }

//region actions

    /*********** Make these functions public for easier testing ************************
     /***
     * Add user as long as it is valid (age >= 18 && age <= 120)(name.length >= 2 && name.length <=20)
     * @param obUser - User object to be inserted to the database
     * @return User | null - if successful return original user object - if failed return null
     */
    public User addUser(User obUser) {
        User userToReturn = null; // null indicates the operation failed
        try {
            // ensure car vin  is not in db and double check car is valid
            if (vh.isValid(obUser)) {
                int result = userDB.create(obUser);
                userToReturn = obUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // do something if you need to
        }
        return userToReturn;
    }


    /***
     * Save a User as long as it is valid
     * @param obUser - User object to be updated or added in the database
     * @return User | null - if successful return original User object - if failed return null
     */
    public User saveUser(User obUser) {
        if (obUser.getName().length() > 0) {
            return this.addUser(obUser);
        }

        return null;
    }

    /***
     * Get all users in the database in no particular order
     * @return ArrayList<User> : if empty means not users in the db or operation failed
     */
    public List<User> getAll() {
        List<User> usersFromDB = new ArrayList<User>(); // empty indicates the operation failed
        try {
            usersFromDB = userDB.queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //repo.getConnectionSource().closeQuietly();
        }
        return usersFromDB;
    }


    /***
     * Find one User by the user id
     * @param id
     * @return
     */
    public User getByID(Long id) {
        User userToReturn = null; // null indicates the operation failed
        try {
            userToReturn = userDB.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //repo.getConnectionSource().closeQuietly();
        }
        return userToReturn;
    }

//endregion

//region FXML

    // @FXML Annotated Fields and Handlers that access controls and event handlers declared in the FXML file

    @FXML
    private TableView<User> tblUsers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set row factory to handle double click
        tblUsers.setRowFactory(tv ->
        {
            TableRow<User> row = new TableRow<User>();
            return row;
        });

        //get users from Db put them in an observable list and set that to the table view items
        tblUsers.setItems(FXCollections.observableArrayList(this.getAll()));
    }


}
